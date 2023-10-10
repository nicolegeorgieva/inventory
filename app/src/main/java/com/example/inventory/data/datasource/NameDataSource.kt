package com.example.inventory.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class NameDataSource @Inject constructor(
    private val datastore: DataStore<Preferences>
) {

}