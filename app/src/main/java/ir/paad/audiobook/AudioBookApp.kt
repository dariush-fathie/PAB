package ir.paad.audiobook

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class AudioBookApp : Application() {


    override fun onCreate() {
        super.onCreate()

        // default realm database
        Realm.init(this)
        val conf = RealmConfiguration.Builder().name("database.realm").schemaVersion(1).build()
        Realm.setDefaultConfiguration(conf)



    }

}
