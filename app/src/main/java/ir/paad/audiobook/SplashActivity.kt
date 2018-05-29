package ir.paad.audiobook

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_splash)

        Handler().postDelayed({
            runOnUiThread({
                finish()
            })
        }, 1000)

    }

}