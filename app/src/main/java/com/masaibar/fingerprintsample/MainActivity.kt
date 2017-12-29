package com.masaibar.fingerprintsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fingerprintClient = FingerprintClient(this)

        button_is_hardware_detected.setOnClickListener {
            Toast.makeText(
                    this,
                    "Hardware detected = ${fingerprintClient.isHardwareDetected()}",
                    Toast.LENGTH_SHORT
            ).show()
        }

        button_has_enrolled_fp.setOnClickListener {
            Toast.makeText(
                    this,
                    "Has enrolled fingerprint = ${fingerprintClient.hasEnrolledFingerprints()}",
                    Toast.LENGTH_SHORT
            ).show()
        }

        button_start_auth.setOnClickListener {
//            fingerprintClient.authenticate()
            AuthActivity.start(this)
        }
    }
}
