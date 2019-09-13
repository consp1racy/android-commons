@file:Suppress("unused")

package net.xpece.android.app

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.StorageStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.companion.CompanionDeviceManager
import android.content.ClipboardManager
import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.fingerprint.FingerprintManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.aware.WifiAwareManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.print.PrintManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textclassifier.TextClassificationManager
import android.view.textservice.TextServicesManager

/**
 * Never null.
 */
inline val Context.activityManager: ActivityManager
    get() = getSystemServiceOrThrow(Context.ACTIVITY_SERVICE)

/**
 * Never null.
 */
inline val Context.alarmManager: AlarmManager
    get() = getSystemServiceOrThrow(Context.ALARM_SERVICE)

/**
 * Never null.
 */
inline val Context.audioManager: AudioManager
    get() = getSystemServiceOrThrow(Context.AUDIO_SERVICE)

inline val Context.clipboardManager: ClipboardManager?
    get() = getSystemServiceOrNull(Context.CLIPBOARD_SERVICE)

inline val Context.connectivityManager: ConnectivityManager?
    get() = getSystemServiceOrNull(Context.CONNECTIVITY_SERVICE)

/**
 * Never null.
 */
inline val Context.keyguardManager: KeyguardManager
    get() = getSystemServiceOrThrow(Context.KEYGUARD_SERVICE)

/**
 * Never null.
 */
inline val Context.layoutInflater: LayoutInflater
    get() = getSystemServiceOrThrow(Context.LAYOUT_INFLATER_SERVICE)

inline val Context.locationManager: LocationManager?
    get() = getSystemServiceOrNull(Context.LOCATION_SERVICE)

/**
 * Never null.
 */
inline val Context.notificationManager: NotificationManager
    get() = getSystemServiceOrThrow(Context.NOTIFICATION_SERVICE)

/**
 * Never null.
 *
 * Requires `support-compat` library version 24.2.0 or later
 * or `support-v4` library version 22.0.0 or later.
 */
inline val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

/**
 * Never null.
 */
inline val Context.powerManager: PowerManager
    get() = getSystemServiceOrThrow(Context.POWER_SERVICE)

inline val Context.searchManager: SearchManager?
    get() = getSystemServiceOrNull(Context.SEARCH_SERVICE)

/**
 * Never null.
 */
inline val Context.sensorManager: SensorManager
    get() = getSystemServiceOrThrow(Context.SENSOR_SERVICE)

/**
 * Never null.
 */
inline val Context.telephonyManager: TelephonyManager
    get() = getSystemServiceOrThrow(Context.TELEPHONY_SERVICE)

/**
 * Never null.
 */
inline val Context.vibrator: Vibrator
    get() = getSystemServiceOrThrow(Context.VIBRATOR_SERVICE)

inline val Context.wallpaperService: WallpaperManager?
    get() = getSystemServiceOrNull(Context.WALLPAPER_SERVICE)

inline val Context.wifiManager: WifiManager?
    get() = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        applicationContext.getSystemServiceOrNull(Context.WIFI_SERVICE)
    } else {
        getSystemServiceOrNull(Context.WIFI_SERVICE)
    }

/**
 * Never null.
 */
inline val Context.windowManager: WindowManager
    get() = getSystemServiceOrThrow(Context.WINDOW_SERVICE)

/**
 * Never null.
 */
inline val Context.inputMethodManager: InputMethodManager
    get() = getSystemServiceOrThrow(Context.INPUT_METHOD_SERVICE)

/**
 * Never null.
 */
inline val Context.accessibilityManager: AccessibilityManager
    get() = getSystemServiceOrThrow(Context.ACCESSIBILITY_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(5)
inline val Context.accountManager: AccountManager
    get() = getSystemServiceOrThrow(Context.ACCOUNT_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(8)
inline val Context.devicePolicyManager: DevicePolicyManager
    get() = getSystemServiceOrThrow(Context.DEVICE_POLICY_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(8)
inline val Context.dropBoxManager: DropBoxManager
    get() = getSystemServiceOrThrow(Context.DROPBOX_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(8)
inline val Context.uiModeManager: UiModeManager
    get() = getSystemServiceOrThrow(Context.UI_MODE_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(9)
inline val Context.downloadManager: DownloadManager
    get() = getSystemServiceOrThrow(Context.DOWNLOAD_SERVICE)

@get:RequiresApi(9)
inline val Context.storageManager: StorageManager?
    get() = getSystemServiceOrNull(Context.STORAGE_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(10)
inline val Context.nfcManager: NfcManager
    get() = getSystemServiceOrThrow(Context.NFC_SERVICE)

@get:RequiresApi(12)
inline val Context.usbManager: UsbManager?
    get() = getSystemServiceOrNull(Context.USB_SERVICE)

@get:RequiresApi(14)
inline val Context.textServicesManager: TextServicesManager?
    get() = getSystemServiceOrNull(Context.TEXT_SERVICES_MANAGER_SERVICE)

@get:RequiresApi(14)
inline val Context.wifiP2pManager: WifiP2pManager?
    get() = getSystemServiceOrNull(Context.WIFI_P2P_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(16)
inline val Context.inputManager: InputManager
    get() = getSystemServiceOrThrow(Context.INPUT_SERVICE)

@get:RequiresApi(16)
inline val Context.mediaRouter: MediaRouter?
    get() = getSystemServiceOrNull(Context.MEDIA_ROUTER_SERVICE)

@get:RequiresApi(16)
inline val Context.nsdManager: NsdManager?
    get() = getSystemServiceOrNull(Context.NSD_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(17)
inline val Context.displayManager: DisplayManager
    get() = getSystemServiceOrThrow(Context.DISPLAY_SERVICE)

/**
 * Never null.
 *
 * Requires `support-compat` library version 24.2.0 or later
 * or `support-v4` library version 22.0.0 or later.
 */
inline val Context.displayManagerCompat: DisplayManagerCompat
    get() = DisplayManagerCompat.getInstance(this)

/**
 * Never null.
 */
@get:RequiresApi(17)
inline val Context.userManager: UserManager
    get() = getSystemServiceOrThrow(Context.USER_SERVICE)

@get:RequiresApi(18)
inline val Context.bluetoothManager: BluetoothManager?
    get() = getSystemServiceOrNull(Context.BLUETOOTH_SERVICE)

@get:RequiresApi(19)
inline val Context.appOpsManager: AppOpsManager?
    get() = getSystemServiceOrNull(Context.APP_OPS_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(19)
inline val Context.captioningManager: CaptioningManager
    get() = getSystemServiceOrThrow(Context.CAPTIONING_SERVICE)

@get:RequiresApi(19)
inline val Context.consumerIrManager: ConsumerIrManager?
    get() = getSystemServiceOrNull(Context.CONSUMER_IR_SERVICE)

@get:RequiresApi(19)
inline val Context.printManager: PrintManager?
    get() = getSystemServiceOrNull(Context.PRINT_SERVICE)

@get:RequiresApi(21)
inline val Context.appWidgetManager: AppWidgetManager?
    get() = getSystemServiceOrNull(Context.APPWIDGET_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.batteryManager: BatteryManager
    get() = getSystemServiceOrThrow(Context.BATTERY_SERVICE)

@get:RequiresApi(21)
inline val Context.cameraManager: CameraManager?
    get() = getSystemServiceOrNull(Context.CAMERA_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.jobScheduler: JobScheduler
    get() = getSystemServiceOrThrow(Context.JOB_SCHEDULER_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.launcherApps: LauncherApps
    get() = getSystemServiceOrThrow(Context.LAUNCHER_APPS_SERVICE)

@get:RequiresApi(21)
inline val Context.mediaProjectionManager: MediaProjectionManager?
    get() = getSystemServiceOrNull(Context.MEDIA_PROJECTION_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.mediaSessionManager: MediaSessionManager
    get() = getSystemServiceOrThrow(Context.MEDIA_SESSION_SERVICE)

@get:RequiresApi(21)
inline val Context.restrictionsManager: RestrictionsManager?
    get() = getSystemServiceOrNull(Context.RESTRICTIONS_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.telecomManager: TelecomManager
    get() = getSystemServiceOrThrow(Context.TELECOM_SERVICE)

@get:RequiresApi(21)
inline val Context.tvInputManager: TvInputManager?
    get() = getSystemServiceOrNull(Context.TV_INPUT_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(22)
inline val Context.subscriptionManager: SubscriptionManager
    get() = getSystemServiceOrThrow(Context.TELEPHONY_SUBSCRIPTION_SERVICE)

@get:RequiresApi(22)
inline val Context.usageStatsManager: UsageStatsManager?
    get() = getSystemServiceOrNull(Context.USAGE_STATS_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(23)
inline val Context.carrierConfigManager: CarrierConfigManager
    get() = getSystemServiceOrThrow(Context.CARRIER_CONFIG_SERVICE)

@get:RequiresApi(23)
inline val Context.fingerprintManager: FingerprintManager?
    get() = getSystemServiceOrNull(Context.FINGERPRINT_SERVICE)

/**
 * Never null.
 *
 * Requires `support-compat` library version 23.0.0 or later.
 */
inline val Context.fingerprintManagerCompat: FingerprintManagerCompat
    get() = FingerprintManagerCompat.from(this)

@get:RequiresApi(23)
inline val Context.midiManager: MidiManager?
    get() = getSystemServiceOrNull(Context.MIDI_SERVICE)

@get:RequiresApi(23)
inline val Context.networkStatsManager: NetworkStatsManager?
    get() = getSystemServiceOrNull(Context.NETWORK_STATS_SERVICE)

@get:RequiresApi(24)
inline val Context.hardwarePropertiesManager: HardwarePropertiesManager?
    get() = getSystemServiceOrNull(Context.HARDWARE_PROPERTIES_SERVICE)

@get:RequiresApi(24)
inline val Context.systemHealthManager: SystemHealthManager?
    get() = getSystemServiceOrNull(Context.SYSTEM_HEALTH_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(25)
inline val Context.shortcutManager: ShortcutManager
    get() = getSystemServiceOrThrow(Context.SHORTCUT_SERVICE)

@get:RequiresApi(26)
inline val Context.companionDeviceManager: CompanionDeviceManager?
    get() = getSystemServiceOrNull(Context.COMPANION_DEVICE_SERVICE)

@get:RequiresApi(26)
inline val Context.storageStatsManager: StorageStatsManager?
    get() = getSystemServiceOrNull(Context.STORAGE_STATS_SERVICE)

/**
 * Never null.
 */
@get:RequiresApi(26)
inline val Context.textClassificationManager: TextClassificationManager
    get() = getSystemServiceOrThrow(Context.TEXT_CLASSIFICATION_SERVICE)

@get:RequiresApi(26)
inline val Context.wifiAwareManager: WifiAwareManager?
    get() = getSystemServiceOrNull(Context.WIFI_AWARE_SERVICE)

inline fun <reified T> Context.getSystemServiceOrNull(name: String): T? =
        getSystemService(name) as T?

/**
 * @throws ServiceNotFoundException When service is not found.
 */
inline fun <reified T> Context.getSystemServiceOrThrow(name: String): T =
        getSystemServiceOrNull<T>(name) ?:
                throw ServiceNotFoundException(
                        "${T::class.java.simpleName} not found.")

@RequiresApi(23)
inline fun <reified T> Context.getSystemServiceOrNull(): T? =
        getSystemService(T::class.java)

/**
 * @throws ServiceNotFoundException When service is not found.
 */
@RequiresApi(23)
inline fun <reified T> Context.getSystemServiceOrThrow(): T =
        getSystemServiceOrNull<T>() ?:
                throw ServiceNotFoundException(
                        "${T::class.java.simpleName} not found.")
