package com.myprojects.ecommerceapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

object PreferenceKeys {
    val USERNAME = stringPreferencesKey("username")
}

suspend fun saveLoginInfo(context:Context,username: String) {
    context.dataStore.edit { preferences ->
        preferences[PreferenceKeys.USERNAME] = username
    }
}

fun getLoginInfo(context: Context): Flow<String?> {
    return context.dataStore.data.map { preferences ->
        preferences[PreferenceKeys.USERNAME]
    }
}