package com.example.rently.ui.screens.add_apartment

import android.content.Context
import android.gesture.Prediction
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.method.TextKeyListener.clear
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rently.Resource
import com.example.rently.model.Apartment
import com.example.rently.model.GooglePrediction
import com.example.rently.repository.GooglePlacesRepository
import com.example.rently.repository.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class AddApartmentViewModel @Inject constructor(
    private val googleRepository: GooglePlacesRepository,
    private val imagesRepository: ImagesRepository
) : ViewModel() {

    val isLoading = mutableStateOf(false)
    val showPredictions = mutableStateOf(false)
    var predictions = mutableStateListOf<GooglePrediction>()

    private fun getPredictions(address: String) {
        showPredictions.value = true
        viewModelScope.launch {
            isLoading.value = true
            val response = googleRepository.getPredictions(input = address)
            when (response) {
                is Resource.Success -> {
                    predictions = response?.data?.predictions!!.toMutableStateList()
                }
            }

            isLoading.value = false
        }
    }

    fun showPredictions() {
        showPredictions.value = true
    }

    fun hidePredictions() {
        showPredictions.value = false
    }

    fun onSearchAddressChange(address: String) {
        getPredictions(address)
        showPredictions()
    }

    // Todo
    fun uploadMultipleImages() {

    }

    fun uploadImage(imageBase64: String) {
        viewModelScope.launch {
            val response = imagesRepository.uploadImage(image = imageBase64)
            when (response) {
                is Resource.Success -> {
                    Log.d("Rently", "${response.data?.data?.url}")
                    Log.d("Rently", "Uploaded successfully!")
                }
                else -> {
                    Log.d("Rently", "Error while uploading image")
                }
            }
        }
    }

    private fun convertImageToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedImage =
            try {
                Base64.encodeToString(byteArray, Base64.DEFAULT);
            } catch (exception: Exception) {
                ""
            }

        return encodedImage
    }

    fun getImageBitmap(context: Context, imageUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, imageUri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, imageUri!!)
            ImageDecoder.decodeBitmap(source)
        }
    }
}