package ir.paad.audiobook.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.onesignal.*

class OneSignalIdObserver : Service(), OSSubscriptionObserver, OSPermissionObserver {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        Log.e("id,token", OneSignal.getPermissionSubscriptionState().subscriptionStatus.pushToken + "  \n:  " + OneSignal.getPermissionSubscriptionState().subscriptionStatus.userId)

        OneSignal.addSubscriptionObserver(this)
        OneSignal.addPermissionObserver(this)

    }


    override fun onOSSubscriptionChanged(stateChanges: OSSubscriptionStateChanges?) {

        stateChanges ?: return
        stateChanges.from ?: return
        stateChanges.to ?: return

        /**
         * @param stateChanges
         * hold FCM token and OneSignal userId
         * stateChanges.from : contain old token and userId
         * stateChanges.to : contain new token and userId
         * to getCurrent token and userId use below code :
         * OneSignal.getPermissionSubscriptionState().subscriptionStatus.[pushToken|userId]
         */

        if (!stateChanges.from.subscribed && stateChanges.to.subscribed) {
            Log.e("token", stateChanges.to.pushToken) // new token
            Log.e("userId", stateChanges.to.userId) // new userId
            Log.e("fromUserID", stateChanges.from.userId) // old token
            Log.e("fromToken", stateChanges.from.pushToken) // old userId
        }

    }

    override fun onOSPermissionChanged(stateChanges: OSPermissionStateChanges?) {
        Log.e("from  subscribed ", stateChanges?.from?.enabled.toString())
        Log.e("to  subscribed ", stateChanges?.to?.enabled.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
