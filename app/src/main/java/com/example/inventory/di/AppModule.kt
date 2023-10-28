package com.example.inventory.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.example.inventory.data.database.AppDatabase
import com.example.inventory.data.database.dao.InventoryDao
import com.example.inventory.data.datastore.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDataStore(
        @ApplicationContext
        context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    fun provideDataBase(
        @ApplicationContext
        context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "app-database"
        ).build()
    }

    @Provides
    fun provideInventoryDao(
        database: AppDatabase
    ): InventoryDao {
        return database.inventoryDao()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        json: Json
    ): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(json = json, contentType = ContentType.Any)
            }
        }
    }

    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}