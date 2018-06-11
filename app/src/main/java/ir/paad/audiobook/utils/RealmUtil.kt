package ir.paad.audiobook.utils

import android.content.Context
import io.realm.RealmConfiguration
import ir.paad.audiobook.R

class RealmUtil {

    fun getConfiguration(context: Context): RealmConfiguration {
        val sp = SharedPreferencesUtil(context)
        return RealmConfiguration.Builder()
                .name(context.getString(R.string.default_information) + context.getString(R.string.realm))
                .schemaVersion(context.resources.getInteger(R.integer.schema_version).toLong())
                .encryptionKey(Base64util().decodeFromString(sp.getStringValue(context.getString(R.string.defaultConfiguration))))
                .build()
    }

}