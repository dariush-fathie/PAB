package ir.paad.audiobook.utils

import android.util.Base64

class Base64util {

    fun encodeToString(a: ByteArray): String {
        return Base64.encodeToString(a, Base64.DEFAULT)
    }

    fun decodeFromString(a: String): ByteArray {
        val decoded = Base64.decode(a, Base64.DEFAULT)
        return decoded
    }

}
