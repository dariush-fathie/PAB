package ir.paad.audiobook.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

    private val config = "config"
    private val defaultStringValue = ""
    private val defaultBooleanValue = false
    private val defaultIntValue = -1
    private var sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor


    init {
        sharedPreferences = context.getSharedPreferences(config, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

    fun getStringValue(name: String): String {
        return sharedPreferences.getString(name, defaultStringValue)
    }

    fun putStringValue(name: String, value: String) {
        editor.putString(name, value).apply()
        editor.commit()
    }

    fun getIntValue(name: String): Int {
        return sharedPreferences.getInt(name, defaultIntValue)
    }

    fun putIntValue(name: String, value: Int) {
        editor.putInt(name, value).apply()
    }


    fun putBooleanValue(name: String, value: Boolean) {
        editor.putBoolean(name, value).apply()
    }

    fun getBooleanValue(name: String): Boolean {
        return sharedPreferences.getBoolean(name, defaultBooleanValue)
    }

}