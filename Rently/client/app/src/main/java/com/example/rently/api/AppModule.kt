package com.example.rently.api

import com.example.rently.repository.ApartmentRepository
import com.example.rently.repository.UserRepository
import com.example.rently.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: UserApi
    ) = UserRepository(api)

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
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ApartmentApi::class.java)
    }
}