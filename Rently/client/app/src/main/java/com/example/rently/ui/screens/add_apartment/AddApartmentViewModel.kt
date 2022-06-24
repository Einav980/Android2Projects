package com.example.rently.ui.screens.add_apartment

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.google.GoogleLocation
import com.example.rently.model.google.GooglePrediction
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.GooglePlacesRepository
import com.example.rently.repository.ImagesRepository
import com.example.rently.util.convertImageToBase64
import com.example.rently.validation.presentation.AddApartmentFormEvent
import com.example.rently.validation.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddApartmentViewModel @Inject constructor(
    private val googleRepository: GooglePlacesRepository,
    private val imagesRepository: ImagesRepository,
    private val apartmentRepository: ApartmentRepository,
    private val validateAddress: ValidateAddress,
    private val validateDescription: ValidateDescription,
    private val validatePrice: ValidatePrice,
    private val validateSize: ValidateSize
) : ViewModel() {

    var state by mutableStateOf(AddApartmentFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    val apartmentImageUri = mutableStateOf<Uri?>(null)
    val apartmentImageBitmap = mutableStateOf<Bitmap?>(null)
    val placesLoading = mutableStateOf(false)
    var predictions = mutableStateListOf<GooglePrediction>()

    init {
        fetchApartmentTypes()
    }

    fun onEvent(event: AddApartmentFormEvent) {
        when (event) {
            is AddApartmentFormEvent.Submit -> {
                submitData()
            }

            is AddApartmentFormEvent.AddressChanged -> {
                viewModelScope.launch {
                    state = state.copy(address = event.address, showPredictions = true)
                    getPredictions(state.address)
                }
            }

            is AddApartmentFormEvent.AddressClicked -> {
                state = state.copy(address = event.address, showPredictions = false)
            }

            is AddApartmentFormEvent.DescriptionChanged -> {
                state = state.copy(description = event.description)
            }

            is AddApartmentFormEvent.PriceChanged -> {
                state = state.copy(price = event.price)
            }

            is AddApartmentFormEvent.SizeChanged -> {
                state = state.copy(size = event.size)
            }

            is AddApartmentFormEvent.HasBalconyChanged -> {
                state = state.copy(hasBalcony = event.checkState)
            }

            is AddApartmentFormEvent.HasParkingChanged -> {
                state = state.copy(hasParking = event.checkState)
            }

            is AddApartmentFormEvent.IsAnimalFriendlyChanged -> {
                state = state.copy(isAnimalFriendly = event.checkState)
            }

            is AddApartmentFormEvent.IsFurnishedChanged -> {
                state = state.copy(isFurnished = event.checkState)
            }

            is AddApartmentFormEvent.ShowPredictions -> {
                state = state.copy(showPredictions = true)
            }

            is AddApartmentFormEvent.HidePredictions -> {
                state = state.copy(showPredictions = false)
            }

            is AddApartmentFormEvent.CityChanged -> {
                state = state.copy(city = event.city)
            }

            is AddApartmentFormEvent.BedroomsAmountChanged -> {
                state = state.copy(numberOfBedrooms = event.numberOfBedrooms)
            }

            is AddApartmentFormEvent.BathroomsAmountChanged -> {
                state = state.copy(numberOfBathrooms = event.numberOfBathrooms)
            }

            is AddApartmentFormEvent.ApartmentTypeChanged -> {
                state = state.copy(selectedApartmentTypeIndex = event.index)
            }

            is AddApartmentFormEvent.ApartmentUploadingStateChanged -> {
                state = state.copy(apartmentIsUploading = event.state)
            }

        }
    }

    private fun fetchApartmentTypes() {
        viewModelScope.launch {
            state = state.copy(isApartmentTypesLoading = true)
            val response = apartmentRepository.listApartmentTypes()
            when (response) {
                is Resource.Success -> {
                    state = state.copy(apartmentTypes = response.data!!)
                }
                else -> {
                    Log.d("Rently", "Error listing apartment types")
                }
            }
            state = state.copy(isApartmentTypesLoading = false)
        }
    }

    private fun clearAllErrors() {
        state = state.copy(
            addressError = null,
            descriptionError = null,
            priceError = null,
            sizeError = null
        )
    }

    private fun submitData() {

        val addressResult = validateAddress.execute(state.address)
        val descriptionResult = validateDescription.execute(state.description)
        val priceResult = validatePrice.execute(state.price)
        val sizeResult = validateSize.execute(state.size)

        val hasError =
            listOf(addressResult, descriptionResult, priceResult, sizeResult).any { !it.successful }

        if (hasError) {
            state = state.copy(
                addressError = addressResult.errorMessage,
                descriptionError = descriptionResult.errorMessage,
                priceError = priceResult.errorMessage,
                sizeError = sizeResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            clearAllErrors()
            addApartment()
        }
    }

    private suspend fun getPredictions(address: String) {
        placesLoading.value = true
        val response = googleRepository.getPredictions(input = address)
        when (response) {
            is Resource.Success -> {
                predictions = response?.data?.predictions!!.toMutableStateList()
            }
        }
        placesLoading.value = false
    }

    private suspend fun addApartment() {
        validationEventChannel.send(ValidationEvent.ApartmentUploading)

        val apartmentEncodedString = convertImageToBase64(bitmap = apartmentImageBitmap.value!!)

        val imageUploadResponse = uploadImage(apartmentEncodedString)
        if (imageUploadResponse.isEmpty()) {
            validationEventChannel.send(ValidationEvent.ApartmentUploadError)
            return
        }

        state = state.copy(apartmentImageUrl = imageUploadResponse)

        val location = getAddressLocation(state.address)
        if (location == null) {
            validationEventChannel.send(ValidationEvent.ApartmentUploadError)
            return
        }

        state = state.copy(apartmentAddressLocation = location)

        val apartment = Apartment(
            address = state.address,
            city = state.city,
            type = "Apartment",
            size = state.size,
            hasBalcony = state.hasBalcony,
            hasParking = state.hasParking,
            isAnimalsFriendly = state.isAnimalFriendly,
            isFurnished = state.isFurnished,
            price = state.price,
            imageUrl = state.apartmentImageUrl,
            location = state.apartmentAddressLocation,
            numberOfBaths = state.numberOfBathrooms,
            numberOfBeds = state.numberOfBedrooms,
        )

        val apartmentUploadResponse = uploadApartment(apartment)
        if(!apartmentUploadResponse){
            validationEventChannel.send(ValidationEvent.ApartmentUploadError)
            return
        }

        validationEventChannel.send(ValidationEvent.ApartmentUploadSuccess)
    }

    private suspend fun uploadApartment(apartment: Apartment): Boolean {
        return withContext(Dispatchers.IO) {
            val response = apartmentRepository.addApartment(apartment)
            when (response) {
                is Resource.Success -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private suspend fun uploadImage(imageBase64: String): String {
        return withContext(Dispatchers.IO) {
            val response = imagesRepository.uploadImage(image = imageBase64)
            when (response) {
                is Resource.Success -> {
                    Log.d("Rently", "Upload image succesfully")
                    response.data?.data!!.url
                }
                else -> {
                    ""
                }
            }
        }
    }

    private suspend fun getAddressLocation(address: String): GoogleLocation? {
        return withContext(Dispatchers.IO) {
            val response = googleRepository.getAddressLocation(address = address)
            when (response) {
                is Resource.Success -> {
                    Log.d("Rently", "Got address location successfully!")
                    response.data!!.results[0].geometry.location
                }
                else -> {
                    null
                }
            }
        }
    }

    sealed class ValidationEvent {
        object ApartmentUploading : ValidationEvent()
        object ApartmentUploadSuccess : ValidationEvent()
        object ApartmentUploadError : ValidationEvent()
    }
}