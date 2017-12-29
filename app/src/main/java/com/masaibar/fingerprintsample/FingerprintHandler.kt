package com.masaibar.fingerprintsamples

import android.app.Activity
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import java.lang.ref.WeakReference

class FingerprintHandler(private val activityRef: WeakReference<Activity>) : FingerprintManagerCompat.AuthenticationCallback() {

    private lateinit var manager: FingerprintManagerCompat
    private lateinit var cancellationSignal: CancellationSignal

    fun startAuth() {
        manager = FingerprintManagerCompat.from(activityRef.get()?.applicationContext)
        cancellationSignal = CancellationSignal()

        manager.authenticate(null, 0, cancellationSignal, this, null)
    }

    override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
        super.onAuthenticationSucceeded(result)
        activityRef.get()?.finish()
    }

}
