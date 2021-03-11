@file:JvmName("SystemServicesX")
@file:Suppress("unused", "DEPRECATION")

package net.xpece.android.app

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

/**
 * Get a [NotificationManagerCompat] instance for a provided context.
 *
 * Never null.
 */
inline val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

/**
 * Gets an instance of the display manager given the context.
 *
 * Never null.
 */
inline val Context.displayManagerCompat: DisplayManagerCompat
    get() = DisplayManagerCompat.getInstance(this)

/**
 * Get a [FingerprintManagerCompat] instance for a provided context.
 *
 * Never null.
 */
@Deprecated("Use BiometricPrompt instead.")
inline val Context.fingerprintManagerCompat: FingerprintManagerCompat
    get() = FingerprintManagerCompat.from(this)
