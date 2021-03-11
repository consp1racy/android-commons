@file:JvmName("SystemServicesX")
@file:Suppress("unused", "DEPRECATION")

package net.xpece.android.app

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

/**
 * Never null.
 */
inline val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

/**
 * Never null.
 */
inline val Context.displayManagerCompat: DisplayManagerCompat
    get() = DisplayManagerCompat.getInstance(this)

/**
 * Never null.
 */
@Deprecated("Use BiometricPrompt instead.")
inline val Context.fingerprintManagerCompat: FingerprintManagerCompat
    get() = FingerprintManagerCompat.from(this)
