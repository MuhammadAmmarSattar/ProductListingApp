package com.example.myappproject.data.repository.productListing.di

import com.example.myappproject.data.repository.productListing.remote.service.ProductListingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ProductListingModule {
    @Provides
    fun provideProductListingService(retrofit: Retrofit): ProductListingService =
        retrofit.create(ProductListingService::class.java)
}