package ir.paad.audiobook.services

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ir.paad.audiobook.models.events.NetChangeEvent
import ir.paad.audiobook.utils.NetworkUtil
import org.greenrobot.eventbus.EventBus

class NetworkStateReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        EventBus.getDefault().post(NetChangeEvent(NetworkUtil().isNetworkAvailable(context)))
    }

}



