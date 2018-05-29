package ir.paad.audiobook.utils

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CipherUtil {

    private lateinit var algorithm: String
    private lateinit var mode: String
    private lateinit var padding: String
    private var key: SecretKeySpec? = null
    private var iv: IvParameterSpec? = null
    private var cipherMode: Int = Cipher.DECRYPT_MODE


    class Builder() {

        private val cipherUtil = CipherUtil()

        fun algorithm(algo: String) = apply {
            cipherUtil.algorithm = algo
        }

        fun mode(mode: String) = apply {
            cipherUtil.mode = mode
        }

        fun padding(padding: String) = apply {
            cipherUtil.padding = padding
        }

        fun key(key: String) = apply {
            cipherUtil.key = SecretKeySpec(key.toByteArray(), cipherUtil.algorithm)
        }

        fun iv(iv: String) = apply {
            cipherUtil.iv = IvParameterSpec(iv.toByteArray())
        }

        fun build(): Cipher? {
            if (cipherUtil.algorithm == "") {
                throw Exception("you most set algorithm type")
            }
            if (cipherUtil.mode == "") {
                throw Exception("you most set algorithm mode")
            }
            if (cipherUtil.padding == "") {
                throw Exception("you most set algorithm padding")
            }
            if (cipherUtil.key == null) {
                throw Exception("you most set key")
            }
            if (cipherUtil.iv == null) {
                throw Exception("you most set iv")
            }
            var flag = false
            var cipher: Cipher? = null
            try {
                cipher = Cipher.getInstance(cipherUtil.algorithm.trim()
                        + "/" + cipherUtil.mode.trim()
                        + "/" +
                        cipherUtil.padding.trim())
            } catch (e: NoSuchAlgorithmException) {
                flag = true
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                flag = true
                e.printStackTrace()
            }

            try {
                cipher?.init(cipherUtil.cipherMode, cipherUtil.key, cipherUtil.iv)
            } catch (e: InvalidKeyException) {
                flag = true
                e.printStackTrace()
            } catch (e: InvalidAlgorithmParameterException) {
                flag = true
                e.printStackTrace()
            }
            return if (flag) {
                null
            } else {
                cipher
            }
        }
    }


}