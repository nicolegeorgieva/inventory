package com.example.inventory.di

import com.example.inventory.DispatcherProvider
import com.example.inventory.DispatcherProviderImpl
import com.example.inventory.data.repository.inventory.InventoryRepository
import com.example.inventory.data.repository.inventory.InventoryRepositoryImpl
import com.example.inventory.data.repository.name.NameRepository
import com.example.inventory.data.repository.name.NameRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindingsModule {

    @Binds
    abstract fun bindInventoryRepository(
        inventoryRepositoryImpl: InventoryRepositoryImpl
    ): InventoryRepository

    @Binds
    abstract fun bindNameRepository(
        nameRepositoryImpl: NameRepositoryImpl
    ): NameRepository

    @Binds
    abstract fun bindDispatcherProvider(
        dispatcherProvider: DispatcherProviderImpl
    ): DispatcherProvider
}