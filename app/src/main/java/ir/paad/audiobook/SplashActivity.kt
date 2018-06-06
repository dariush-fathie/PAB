package ir.paad.audiobook

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import ir.paad.audiobook.client.ServerRequest
import ir.paad.audiobook.models.Config
import ir.paad.audiobook.models.UserSecret
import ir.paad.audiobook.utils.NetworkUtil
import ir.paad.audiobook.utils.SharedPreferencesUtil
import ir.paad.audiobook.utils.ToastUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    private val requestCode = 1019

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_splash)
        finish()
        //requestWriteAndReadPer()
    }

    private fun checkUser(userSecret: UserSecret) {
        val ft = resources.getString(R.string.firstTime)
        val sp = SharedPreferencesUtil(this@SplashActivity)

        if (sp.getBooleanValue(ft)) {
            sp.putBooleanValue(ft, false)
            // create user data base
            Log.e("firstTime", "true")
        } else {
            Log.e("firstTime", "false")
            // todo check if user info are complete
        }
    }

    private fun config() {
        val request = ServerRequest().config(this, object : Callback<Config> {
            override fun onResponse(call: Call<Config>?, response: Response<Config>?) {
                Log.e("connection", "successful")

                // if response was null so there is an error don't continue
                response ?: onFailure(call, Throwable("null response"))
                response ?: return

                val conf = response.body()

                // if status was null so there is an error don't continue
                conf ?: onFailure(call, Throwable("null status"))
                conf ?: return

                if (conf.serverStatus) {
                    if (conf.updateForce) {
                        Log.e("updateForce", "true")
                        showUpdateDialog(conf.currentVersion, conf.updateMessage)
                    } else {
                        Log.e("updateForce", "false")
                        Log.e("", "start authenticating")
                        checkUser(conf.userSecret!!)
                    }
                } else {
                    showServerMessage(conf.message)
                }
            }

            override fun onFailure(call: Call<Config>?, t: Throwable?) {
                Log.e("onFailure", t?.message + " message")
                ToastUtil.showShortToast(this@SplashActivity, "خطا در اتصال به سرور")
                // todo : show error page to notify user ...
            }
        })
    }

    private fun checkNetWorkConnection() {
        if (NetworkUtil().isNetworkAvailable(this)) {
            Log.e("network", "available")
            config()
        } else {
            Log.e("network", "not available")
            //todo connection not available try again
        }
    }

    private fun showUpdateDialog(newVersion: String, updateMessage: String) {

    }

    private fun showServerMessage(message: String) {

    }

    private fun showUpdateMessage() {

    }

    private fun requestWriteAndReadPer() {
        if (ActivityCompat.checkSelfPermission(this
                        , android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("")
            builder.setPositiveButton("باشه", { dialog, which ->
                showPermissionDialog()
            })
            builder.setNegativeButton("اجازه نمیدم", { dialog, which ->
                finish() // go and dead dick head you must to allow ...
            })
            builder.setCancelable(false)
            builder.show()

        } else {
            Toast.makeText(this, "start check server", Toast.LENGTH_SHORT).show()
            checkNetWorkConnection()
        }
    }

    private fun showPermissionDialog() {
        ActivityCompat.requestPermissions(this
                , arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE
                , android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                , requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (this.requestCode == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // app can read/write external storage
            } else {
                // permission not granted request again ...
                requestWriteAndReadPer()
            }
        }
    }


}