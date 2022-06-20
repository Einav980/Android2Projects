package com.example.rently.ui.screens.add_apartment

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.model.google.GooglePrediction
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.GooglePlacesRepository
import com.example.rently.repository.ImagesRepository
import com.example.rently.util.convertImageToBase64
import com.example.rently.validation.presentation.AddApartmentFormEvent
import com.example.rently.validation.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddApartmentViewModel @Inject constructor(
    private val googleRepository: GooglePlacesRepository,
    private val imagesRepository: ImagesRepository,
    private val apartmentRepository: ApartmentRepository,
    private val validateAddress: ValidateAddress = ValidateAddress(),
    private val validateDescription: ValidateDescription = ValidateDescription(),
    private val validatePrice: ValidatePrice = ValidatePrice(),
    private val validateSize: ValidateSize = ValidateSize()
) : ViewModel() {

    var state by mutableStateOf(AddApartmentFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    val errorWhileAddingApartment = mutableStateOf(false)
    val apartmentUploadedImageUrl = mutableStateOf("")
    val apartmentImageUri = mutableStateOf<Uri?>(null)
    val apartmentImageBitmap = mutableStateOf<Bitmap?>(null)
    val apartmentImageEncodedString = mutableStateOf("")
    val apartmentTypeSelectedIndex = mutableStateOf(0)
    val placesLoading = mutableStateOf(false)
    val apartmentTypesLoading = mutableStateOf(false)
    val apartmentIsUploading = mutableStateOf(false)
    var predictions = mutableStateListOf<GooglePrediction>()
    var apartmentTypes = mutableStateListOf<ApartmentType>()

    fun onEvent(event: AddApartmentFormEvent){
        when(event){
            is AddApartmentFormEvent.AddressChanged -> {
                state = state.copy(address = event.address, showPredictions = true)
                getPredictions(state.address)
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

            is AddApartmentFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun clearAllErrors(){
        state = state.copy(addressError = null, descriptionError = null, priceError = null, sizeError = null)
    }

    private fun submitData() {

        val addressResult = validateAddress.execute(state.address)
        val descriptionResult = validateDescription.execute(state.description)
        val priceResult = validatePrice.execute(state.price)
        val sizeResult = validateSize.execute(state.size)

        val hasError = listOf(addressResult, descriptionResult, priceResult, sizeResult).any { !it.successful }

        if(hasError){
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
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    private fun getPredictions(address: String) {
        viewModelScope.launch {
            placesLoading.value = true
            val response = googleRepository.getPredictions(input = address)
            when (response) {
                is Resource.Success -> {
                    predictions = response?.data?.predictions!!.toMutableStateList()
                }
            }

            placesLoading.value = false
        }
    }

    fun listApartmentTypes() {
        viewModelScope.launch {
            apartmentTypesLoading.value = true
            val response = apartmentRepository.listApartmentTypes()
            when (response) {
                is Resource.Success -> {
                    apartmentTypes = response?.data?.toMutableStateList()!!
                }
            }
            apartmentTypesLoading.value = false
        }
    }

    fun sendApartment() {
        apartmentIsUploading.value = true
        viewModelScope.launch {
            if (apartmentImageBitmap.value != null) {
                apartmentImageEncodedString.value =
                    convertImageToBase64(bitmap = apartmentImageBitmap.value!!)
                val imageUploadResponse =
                    imagesRepository.uploadImage(apartmentImageEncodedString.value)
                when (imageUploadResponse) {
                    is Resource.Success -> {
                        apartmentUploadedImageUrl.value = imageUploadResponse.data!!.data.url
                        val apartment = Apartment(
                            address = state.address,
                            city = state.city,
                            type = apartmentTypes[apartmentTypeSelectedIndex.value].type,
                            size = state.size,
                            hasBalcony = state.hasBalcony,
                            hasParking = state.hasParking,
                            isAnimalsFriendly = state.isAnimalFriendly,
                            isFurnished = state.isFurnished,
                            price = state.price,
                            imageUrl = apartmentUploadedImageUrl.value
                        )
                        val response = apartmentRepository.addApartment(apartment)
                        when (response) {
                            is Resource.Success -> {
                                Log.d("Rently", "Apartment has been added successfully!")
                            }
                        }
                    }
                }
            }
            apartmentIsUploading.value = false
        }
    }

    fun uploadImage(imageBase64: String): String {
        var uploadedImageUrl = ""
        viewModelScope.launch {
            val response = imagesRepository.uploadImage(image = imageBase64)
            when (response) {
                is Resource.Success -> {
                    uploadedImageUrl = response.data?.data!!.url
                }
                else -> {
                    uploadedImageUrl = "error"
                }
            }
        }
        return uploadedImageUrl
    }

    fun getAddressLocation(address: String) {
        viewModelScope.launch {
            val response = googleRepository.getAddressLocation(address = address)
            when (response) {
                is Resource.Success -> {
                    Log.d("Rently", "Address location: ${response.data?.results!![0].geometry}")
                }
                else -> {
                    Log.d("Rently", "Error has occured")
                }
            }
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}