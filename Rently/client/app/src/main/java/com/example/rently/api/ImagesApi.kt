package com.example.rently.api

import com.example.rently.model.ImageUploadResponse
import com.example.rently.util.Constants
import okhttp3.MultipartBody
import retrofit2.http.*

interface ImagesApi {
    @FormUrlEncoded
    @POST("1/upload")
    suspend fun uploadImage(
        @Query("key") key: String = Constants.IMGBB_API_KEY,
        @Field("image") image: String
    ): ImageUploadResponse

    companion object{
        const val BASE_URL = "https://api.imgbb.com/"
    }
}