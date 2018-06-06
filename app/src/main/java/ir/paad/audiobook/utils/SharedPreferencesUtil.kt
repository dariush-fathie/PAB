package ir.paad.audiobook.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {

    private val config = "config"
    private val defaultStringValue = "default"
    private val defaultBooleanValue = false
    private val defaultIntValue = -1
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    init {
        sharedPreferences = context.getSharedPreferences(config, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

    fun getStringValue(name: String) :String{
        if (this::sharedPreferences.isInitialized){
            return sharedPreferences.getString("name", defaultStringValue)
        }
        return defaultStringValue
    }

    fun putStringValue(name: String, value: String): Boolean {
        if (this::editor.isInitialized) {
            return editor.putString(name, value).commit()
        }
        return false
    }

    fun getIntValue(name: String): Int {
        if (this::sharedPreferences.isInitialized) {
            return sharedPreferences.getInt(name, defaultIntValue)
        }
        return defaultIntValue
    }

    fun putIntValue(name: String, value: Int): Boolean {
        if (this::editor.isInitialized) {
            return editor.putInt(name, value).commit()
        }
        return false
    }


    fun putBooleanValue(name: String, value: Boolean): Boolean {
        if (this::editor.isInitialized) {
            return editor.putBoolean(name, value).commit()
        }
        return false
    }

    fun getBooleanValue(name: String): Boolean {
        if (this::sharedPreferences.isInitialized) {
            return sharedPreferences.getBoolean(name, defaultBooleanValue)
        }
        return false
    }

}