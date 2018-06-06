package ir.paad.audiobook.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

class BuildUtil {

    fun getVersionCode(context: Context): Int {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("packageManager", e.message + " ")
        }
        return -1
    }

}