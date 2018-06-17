package ir.paad.audiobook.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v4.os.EnvironmentCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.util.*


object DeviceId {
    private var deviceBuildInfo = Build.BOARD + Build.BRAND + Build.DISPLAY + Build.HOST + Build.ID +
            Build.MANUFACTURER + Build.MODEL + Build.PRODUCT + Build.TAGS + Build.USER + Build.VERSION.SDK_INT

    init {
        deviceBuildInfo = StringUtil.removeSpaces(deviceBuildInfo)
    }

    @SuppressLint("HardwareIds")
    fun getAndroidId(ctx: Context): String {
        var androidId = Settings.Secure.getString(ctx.contentResolver,
                Settings.Secure.ANDROID_ID)
        if (androidId == null) {
            androidId = "0"
            // request another userId like AdvertisingID
        }
        androidId = deviceBuildInfo + androidId
        return androidId
    }



    /*fun getIdThread() {

        var adInfo: Info? = null
        try {
            adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mContext)

        } catch (exception: IOException) {
            // Unrecoverable error connecting to Google Play services (e.g.,
            // the old version of the service doesn't support getting AdvertisingId).

        } catch (exception: GooglePlayServicesAvailabilityException) {
            // Encountered a recoverable error connecting to Google Play services.

        } catch (exception: GooglePlayServicesNotAvailableException) {
            // Google Play services is not available entirely.
        }

        val userId = adInfo!!.getUserId()
        val isLAT = adInfo!!.isLimitAdTrackingEnabled()
    }*/


    object Installation {

        // todo we most save file in multiple directories

        private var sID: String? = null
        private const val INSTALLATION = "INSTALLATION"

        fun isAnyInstallationFile(context: Context) : Boolean{
            val docs = File(Environment.getExternalStoragePublicDirectory("Documents").path)
            if (!docs.exists()) {
                docs.mkdir()
            }
            val installation = File(Environment.getExternalStoragePublicDirectory("Documents"), ".$INSTALLATION")
            try {
                if (installation.exists()) {
                    return true
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
            return false
        }

        @Synchronized
        fun id(context: Context): String {
            if (sID == null) {
                val installation = File(Environment.getExternalStoragePublicDirectory("Documents"), ".$INSTALLATION")
                try {
                    if (!installation.exists())
                        writeInstallationFile(installation)
                    sID = readInstallationFile(installation)
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }
            return sID as String
        }

        @Throws(IOException::class)
        private fun readInstallationFile(installation: File): String {
            val f = RandomAccessFile(installation, "r")
            val bytes = ByteArray(f.length().toInt())
            f.readFully(bytes)
            f.close()
            return String(bytes)
        }

        @Throws(IOException::class)
        private fun writeInstallationFile(installation: File) {
            val out = FileOutputStream(installation)
            val id = UUID.randomUUID().toString()
            out.write(id.toByteArray())
            out.close()
        }
    }


}
