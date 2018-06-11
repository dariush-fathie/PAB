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

        // token and userId that create in server for firstTime they are empty
        var token = ""
        var id = ""

        if (!sp.getBooleanValue(context.getString(R.string.userConfig))) {
            createUserDataBase(context)
            sp.putBooleanValue(context.getString(R.string.userConfig), true)
        }

        var isFirstTime = false
        var oldUUIDExist = false
        // default value of boolean variable is false
        if (!sp.getBooleanValue(context.resources.getString(R.string.firstTime))) {

            // if it is first time that user connect to server check if user installed app before
            // by searching installation file in storage

            oldUUIDExist = DeviceId.Installation.isAnyInstallationFile(context)
            isFirstTime = true
            sp.putBooleanValue(context.resources.getString(R.string.firstTime), true)

        }

        val db = Realm.getInstance(RealmUtil().getConfiguration(context))
        db.executeTransaction { realm ->
            val user = realm.where(User::class.java).findFirst()
            user ?: return@executeTransaction
            val secret = user.userSecret
            secret ?: return@executeTransaction
            token = secret.token
            id = secret.userId
        }
        db.close()

        val versionCode = BuildUtil().getVersionCode(context)
        val deviceInformation = Gson().toJson(DeviceInformation(context).get())

        ApiClient().client
                .create(ApiInterface::class.java)
                .getServerStatus(versionCode,
                        token,
                        id,
                        oldUUIDExist,
                        DeviceId.Installation.id(context),
                        DeviceId.getAndroidId(context), deviceInformation
                        , isFirstTime)
                .enqueue(callback)
    }

    private fun createUserDataBase(context: Context) {
        val conf = RealmConfiguration.Builder()
        conf.name(context.getString(R.string.default_information) + context.getString(R.string.realm))
        conf.schemaVersion(context.resources.getInteger(R.integer.schema_version).toLong())
        conf.encryptionKey(KeyUtil().generateAndSaveKey(context))
        conf.build()
    }


}
