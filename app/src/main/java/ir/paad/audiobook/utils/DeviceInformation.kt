package ir.paad.audiobook.utils

import android.content.Context
import android.os.Build
import ir.paad.audiobook.models.DeviceModel

class DeviceInformation(context: Context) {

    private val dm = DeviceModel()

    init {
        dm.board = Build.BOARD
        dm.brand = Build.BRAND
        dm.display = Build.DISPLAY
        dm.host = Build.HOST
        dm.manfacturer = Build.MANUFACTURER
        dm.model = Build.MODEL
        dm.product = Build.PRODUCT
        dm.tags = Build.TAGS
        dm.user = Build.USER
        dm.sdkVersion = Build.VERSION.SDK_INT
        dm.displayHeight = context.resources.displayMetrics.heightPixels
        dm.displayWidth = context.resources.displayMetrics.widthPixels
    }

    fun get(): DeviceModel = dm

}