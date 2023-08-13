package com.example.note.sharedPreferences

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPreferencesManager (context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit {
            putInt(key, value)
        }
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }
}