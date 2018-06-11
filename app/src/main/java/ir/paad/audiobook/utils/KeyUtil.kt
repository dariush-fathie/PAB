package ir.paad.audiobook.utils

import android.content.Context
import android.util.Log
import ir.paad.audiobook.R
import java.security.SecureRandom


class KeyUtil {

    fun generateAndSaveKey(context: Context) : ByteArray {
        val key = generateKey()
        saveKey(context, key)
        return key
    }

    private fun saveKey(context: Context, key: ByteArray) {
        val sp = SharedPreferencesUtil(context)
        val encodedKey = Base64util().encodeToString(key)
        if (sp.getStringValue(context.getString(R.string.defaultConfiguration)) != "") {
            throw Exception("you can not change key")
        }
        Log.e("keyEncoded", encodedKey)
        sp.putStringValue(context.getString(R.string.defaultConfiguration), encodedKey)
    }

    private fun generateKey(): ByteArray {
        val key = ByteArray(64)
        SecureRandom().nextBytes(key)
        return key
    }

}