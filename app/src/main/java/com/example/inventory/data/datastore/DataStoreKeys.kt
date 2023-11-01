package com.example.inventory.data.datastore

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

val NAME = stringPreferencesKey("name")
val QUOTE = stringPreferencesKey("quote")
val DATE = longPreferencesKey("date")