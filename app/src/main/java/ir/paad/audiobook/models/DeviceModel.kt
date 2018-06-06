package ir.paad.audiobook.models

class DeviceModel {

    /*
    private var deviceBuildInfo = Build.BOARD + Build.BRAND + Build.DISPLAY + Build.HOST + Build.ID +
            Build.MANUFACTURER + Build.MODEL + Build.PRODUCT + Build.TAGS + Build.USER + Build.VERSION.SDK_INT
     */
    var board: String? = ""
    var brand: String? = ""
    var display: String? = ""
    var host: String? = ""
    var manfacturer: String? = ""
    var model: String? = ""
    var product: String? = ""
    var tags: String? = ""
    var user: String? = ""
    var sdkVersion: Int = 0
    var displayWidth = 0
    var displayHeight = 0

}