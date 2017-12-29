package com.masaibar.fingerprintsample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.masaibar.fingerprintsamples.FingerprintHandler
import java.lang.ref.WeakReference

class AuthActivity : AppCompatActivity() {

    private lateinit var fingerprintHandler: FingerprintHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        fingerprintHandler = FingerprintHandler(WeakReference(this))
    }

    override fun onResume() {
        super.onResume()
        fingerprintHandler.startAuth()
    }

    companion object {

        private val TAG = "AuthActivity"

        fun start(context: Context) {
            val intent = Intent(context, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)
        }
    }
}
