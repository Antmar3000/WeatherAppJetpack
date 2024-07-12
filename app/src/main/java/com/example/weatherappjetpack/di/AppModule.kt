package com.example.weatherappjetpack.di

import android.app.Application
import androidx.compose.animation.core.InternalAnimationApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFusedLocationClient (app : Application) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}