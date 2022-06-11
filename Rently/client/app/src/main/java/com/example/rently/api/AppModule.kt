package com.example.rently.api

import android.content.Context
import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.GooglePlacesRepository
import com.example.rently.repository.UserRepository
import com.example.rently.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: UserApi,
        @ApplicationContext context: Context
    ) = UserRepository(api, context)

    @Singleton
    @Provides
    fun provideUserApi(): UserApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApartmentRepository(
        api: ApartmentApi
    ) = ApartmentRepository(api)

    @Singleton
    @Provides
    fun provideApartmentApi(): ApartmentApi{
        val clientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ApartmentApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGooglePlacesRepository(
        api: GooglePlacesApi
    ) = GooglePlacesRepository(api)

    @Singleton
    @Provides
    fun provideGooglePlacesApi(): GooglePlacesApi{
        val clientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GooglePlacesApi.BASE_URL)
            .build()
            .create(GooglePlacesApi::class.java)
    }
}