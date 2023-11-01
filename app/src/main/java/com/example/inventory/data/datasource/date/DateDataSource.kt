package com.example.inventory.data.datasource.date

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class DateDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

}