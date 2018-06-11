package ir.paad.audiobook.utils

import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

class HashUtil {

    fun hash(passwordToHash: String, salt: String): String? {
        val p = Base64.encode(salt.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
        val s = Base64.encode(salt.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
        var generatedPassword: String? = null
        try {
            val md = MessageDigest.getInstance("SHA-512")
            md.update(s)
            val bytes = md.digest(p)
            val sb = StringBuilder()
            for (i in bytes.indices) {
                sb.append(Integer.toString((bytes[i] and 0xff.toByte()) + 0x100, 16).substring(1))
            }
            generatedPassword = sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return generatedPassword
    }

}