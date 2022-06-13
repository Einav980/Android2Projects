package com.example.rently.repository

import com.example.rently.Resource
import com.example.rently.api.ApartmentApi
import com.example.rently.api.ImagesApi
import com.example.rently.model.Apartment
import com.example.rently.model.ApartmentType
import com.example.rently.model.ImageUploadResponse
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.serialization.BinaryFormat
import okhttp3.MultipartBody
import javax.inject.Inject

@ActivityScoped
class ImagesRepository @Inject constructor(
    private val api: ImagesApi,
) {
    suspend fun uploadImage(image: String): Resource<ImageUploadResponse> {
        val response = try {
            api.uploadImage(image = image)
        } catch (e: Exception) {
            return Resource.Error("Failed uploading image")
        }
        return Resource.Success(response)
    }
}