package ir.paad.audiobook.client

import android.content.Context
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmConfiguration
import ir.paad.audiobook.R
import ir.paad.audiobook.models.Config
import ir.paad.audiobook.models.User
import ir.paad.audiobook.utils.*
import retrofit2.Callback

class ServerRequest {

    fun config(context: Context, callback: Callback<Config>) {

        val sp = SharedPreferencesUtil(context)
        val ft = context.resources.getString(R.string.firstTime)

        // token and id that create in server for firstTime they are empty
        var token = ""
        var id = ""

        // default value of boolean value is false
        if (!sp.getBooleanValue(ft)) {
            createUserDataBase(context)
            sp.putBooleanValue(ft, true)
        } else {
            // to open encrypted database first create RealmConfiguration instance and pass it to Realm.getInstance([configuration])
            val db = Realm.getInstance(RealmConfiguration.Builder()
                    .name(context.getString(R.string.default_information) + context.getString(R.string.realm))
                    .schemaVersion(context.resources.getInteger(R.integer.schema_version).toLong())
                    .encryptionKey(Base64util().decodeFromString(sp.getStringValue(context.getString(R.string.defaultConfiguration))))
                    .build())

            db.executeTransaction { realm ->
                val user = realm.where(User::class.java).findFirst()
                user ?: throw Exception("null user")
                val secret = user.userSecret
                secret ?: throw Exception("null user")
                token = secret.token
                id = secret.id
            }

        }

        val versionCode = BuildUtil().getVersionCode(context)
        val di = Gson().toJson(DeviceInformation(context).get())

        ApiClient().client
                .create(ApiInterface::class.java)
                .getServerStatus(versionCode,
                        token,
                        id,
                        DeviceId.Installation.id(context),
                        DeviceId.getAndroidId(context), di).enqueue(callback)

    }


    private fun createUserDataBase(context: Context) {
        val conf = RealmConfiguration.Builder()
        conf.name(context.getString(R.string.default_information) + context.getString(R.string.realm))
        conf.schemaVersion(context.resources.getInteger(R.integer.schema_version).toLong())
        conf.encryptionKey(KeyUtil().generateAndSaveKey(context))
        conf.build()
    }


}
