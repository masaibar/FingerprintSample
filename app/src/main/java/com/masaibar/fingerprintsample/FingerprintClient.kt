package com.masaibar.fingerprintsample

import android.content.Context
import android.os.Handler
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.util.Log

/**
 * todo 指紋未登録の時にEvernoteってどういう挙動するんだ?
 * todo 有効になっている状態で後から指紋消したらどうなるの?
 */
class FingerprintClient(context: Context) {

    companion object {
        const val TAG = "FingerprintClient"
    }

    private val manager: FingerprintManagerCompat = FingerprintManagerCompat.from(context)

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

    /**
     * このメソッドを二階連続で読んだ時（キャンセルはされていない.....onAuthenticationError()
     * 登録された指紋で認証した時................................onAuthenticationSucceeded()
     * 登録されていない指紋で失敗した時...........................onAuthenticationFailed()
     */
    fun authenticate() {
        val callback = object : FingerprintManagerCompat.AuthenticationCallback() {
            override fun onAuthenticationError(errMsgId: Int, errString: CharSequence?) {
                super.onAuthenticationError(errMsgId, errString)
                Log.d(TAG, "onAuthenticationError, id = $errMsgId, str = $errString")
            }

            override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "onAuthenticationSucceeded, result = ${result}")
            }

            override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence?) {
                super.onAuthenticationHelp(helpMsgId, helpString)
                Log.d(TAG, "onAuthenticationHelp, id = $helpMsgId, str = $helpString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "onAuthenticationFailed")
            }
        }

        manager.authenticate(null, 0, null, callback, Handler())
    }
}