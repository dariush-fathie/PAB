package ir.paad.audiobook

import android.app.Application
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import com.onesignal.*
import io.realm.Realm
import io.realm.RealmConfiguration
import ir.paad.audiobook.services.NetworkStateReceiver
import ir.paad.audiobook.services.OneSignalIdObserver
import ir.paad.audiobook.utils.NetworkUtil


class AudioBookApp : Application(){


    override fun onCreate() {
        super.onCreate()
        // default realm database
        Realm.init(this)
        val conf = RealmConfiguration.Builder().name("database.realm").schemaVersion(1).build()
        Realm.setDefaultConfiguration(conf)

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init()

        startService(Intent(this , OneSignalIdObserver::class.java))
        registerForNetworkChangeEvents()
    }



    private fun registerForNetworkChangeEvents() {
        val networkStateChangeReceiver = NetworkStateReceiver()
        registerReceiver(networkStateChangeReceiver, IntentFilter(CONNECTIVITY_ACTION))
        registerReceiver(networkStateChangeReceiver, IntentFilter(NetworkUtil().WIFI_STATE_CHANGE_ACTION))
        registerReceiver(networkStateChangeReceiver, IntentFilter(NetworkUtil().manuallyTriggerOnReceive))
    }




}
