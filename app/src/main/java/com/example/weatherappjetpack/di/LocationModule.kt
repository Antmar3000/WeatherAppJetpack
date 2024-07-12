package com.example.weatherappjetpack.di

import com.example.weatherappjetpack.data.location.LocationTrackerImpl
import com.example.weatherappjetpack.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker (locationTracker : LocationTrackerImpl) : LocationTracker
}