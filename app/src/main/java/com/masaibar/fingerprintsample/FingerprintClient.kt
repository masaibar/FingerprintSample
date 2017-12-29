package com.masaibar.fingerprintsample

import android.content.Context
import android.os.Handler
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.support.v4.os.CancellationSignal
import android.util.Log

/**
 * todo 指紋未登録の時にEvernoteってどういう挙動するんだ?
 * todo 有効になっている状態で後から指紋消したらどうなるの?
 */
class FingerprintClient(context: Context) {

    companion object {
        const val TAG = "FingerprintClient"
    }

    interface AuthResultCallback {
        fun onError()
        fun onHelp()
        fun onSucceeded()
        fun onFailed()
    }

    private val manager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)
    private lateinit var cancellationSignal: CancellationSignal
    private var selfCancelled = false

    fun canUse(): Boolean {
        return isHardwareDetected() && hasEnrolledFingerprints()
    }

    /**
     * センサーが端末に搭載されているか
     */
    fun isHardwareDetected(): Boolean {
        return manager.isHardwareDetected
    }

    /**
     * 指紋が登録されているか
     */
    fun hasEnrolledFingerprints(): Boolean {
        return manager.hasEnrolledFingerprints()
    }

    fun auth(resultCallback: AuthResultCallback) {
        cancellationSignal = CancellationSignal()

        val callback = object : FingerprintManagerCompat.AuthenticationCallback() {
            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                super.onAuthenticationError(errMsgId, errString)
                if (!selfCancelled) {
                    resultCallback.onError()
                }
            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpMsgId, helpString)
                if (!selfCancelled) {
                    resultCallback.onHelp()
                }
            }

            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Handler().post( {
                    resultCallback.onSucceeded()
                })
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                resultCallback.onFailed()
            }
        }
        manager.authenticate(null, 0, cancellationSignal, callback, null)
    }

    fun cancel() {
        cancellationSignal.cancel()
        selfCancelled = true
    }
}