@file:JvmName("SystemServices")
@file:Suppress("unused", "DEPRECATION")

package net.xpece.android.app

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.role.RoleManager
import android.app.usage.NetworkStatsManager
import android.app.usage.StorageStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.companion.CompanionDeviceManager
import android.content.ClipboardManager
import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.CrossProfileApps
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.biometrics.BiometricManager
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
import android.net.IpSecManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.aware.WifiAwareManager
import android.net.wifi.p2p.WifiP2pManager
import android.net.wifi.rtt.WifiRttManager
import android.nfc.NfcManager
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.print.PrintManager
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.telephony.euicc.EuiccManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textclassifier.TextClassificationManager
import android.view.textservice.TextServicesManager
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.hardware.display.DisplayManagerCompat
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

/**
 * Get a [ActivityManager] for interacting with the global system state.
 * 
 * Never null.
 */
inline val Context.activityManager: ActivityManager
    get() = getSystemServiceOrThrow(Context.ACTIVITY_SERVICE)

/**
 * Get a [AlarmManager] for receiving intents at a time of your choosing.
 * 
 * Never null.
 */
inline val Context.alarmManager: AlarmManager
    get() = getSystemServiceOrThrow(Context.ALARM_SERVICE)

/**
 * Get a [AudioManager] for handling management of volume, ringer modes and audio routing.
 * 
 * Never null.
 */
inline val Context.audioManager: AudioManager
    get() = getSystemServiceOrThrow(Context.AUDIO_SERVICE)

/**
 * Get a [ClipboardManager] for accessing and modifying the contents of the global clipboard.
 */
inline val Context.clipboardManager: ClipboardManager?
    get() = getSystemServiceOrNull(Context.CLIPBOARD_SERVICE)

/**
 * Get a [ConnectivityManager] for handling management of network connections.
 */
inline val Context.connectivityManager: ConnectivityManager?
    get() = getSystemServiceOrNull(Context.CONNECTIVITY_SERVICE)

/**
 * Get a [KeyguardManager] for controlling keyguard.
 * 
 * Never null.
 */
inline val Context.keyguardManager: KeyguardManager
    get() = getSystemServiceOrThrow(Context.KEYGUARD_SERVICE)

/**
 * Get a [LayoutInflater] for inflating layout resources in this context.
 * 
 * Never null.
 */
inline val Context.layoutInflater: LayoutInflater
    get() = getSystemServiceOrThrow(Context.LAYOUT_INFLATER_SERVICE)

/**
 * Get a [LocationManager] for controlling location updates.
 */
inline val Context.locationManager: LocationManager?
    get() = getSystemServiceOrNull(Context.LOCATION_SERVICE)

/**
 * Get a [NotificationManager] for informing the user of background events.
 * 
 * Never null.
 */
inline val Context.notificationManager: NotificationManager
    get() = getSystemServiceOrThrow(Context.NOTIFICATION_SERVICE)

/**
 * Get a [PowerManager] for controlling power management, including "wake locks",
 * which let you keep the device on while you're running long tasks.
 * 
 * Never null.
 */
inline val Context.powerManager: PowerManager
    get() = getSystemServiceOrThrow(Context.POWER_SERVICE)

/**
 * Get a [SearchManager] for handling searches.
 */
inline val Context.searchManager: SearchManager?
    get() = getSystemServiceOrNull(Context.SEARCH_SERVICE)

/**
 * Get a [SensorManager] for accessing sensors.
 * 
 * Never null.
 */
inline val Context.sensorManager: SensorManager
    get() = getSystemServiceOrThrow(Context.SENSOR_SERVICE)

/**
 * Get a [TelephonyManager] for handling management the telephony features of the device.
 * 
 * Never null.
 */
inline val Context.telephonyManager: TelephonyManager
    get() = getSystemServiceOrThrow(Context.TELEPHONY_SERVICE)

/**
 * Get a [Vibrator] for interacting with the vibration hardware.
 * 
 * Never null.
 */
inline val Context.vibrator: Vibrator
    get() = getSystemServiceOrThrow(Context.VIBRATOR_SERVICE)

/**
 * Get a [WallpaperManager] for accessing wallpapers.
 */
inline val Context.wallpaperService: WallpaperManager?
    get() = getSystemServiceOrNull(Context.WALLPAPER_SERVICE)

/**
 * Get a [WifiManager] for handling management of Wi-Fi access.
 */
inline val Context.wifiManager: WifiManager?
    get() = if (SDK_INT < 24) {
        applicationContext.getSystemServiceOrNull(Context.WIFI_SERVICE)
    } else {
        getSystemServiceOrNull(Context.WIFI_SERVICE)
    }

/**
 * Get a [WindowManager] for accessing the system's window manager.
 * 
 * Never null.
 */
inline val Context.windowManager: WindowManager
    get() = getSystemServiceOrThrow(Context.WINDOW_SERVICE)

/**
 * Get a [InputMethodManager] for accessing input methods.
 * 
 * Never null.
 */
inline val Context.inputMethodManager: InputMethodManager
    get() = getSystemServiceOrThrow(Context.INPUT_METHOD_SERVICE)

/**
 * Get a [AccessibilityManager] for giving the user feedback for UI events
 * through the registered event listeners.
 * 
 * Never null.
 */
inline val Context.accessibilityManager: AccessibilityManager
    get() = getSystemServiceOrThrow(Context.ACCESSIBILITY_SERVICE)

/**
 * Get a [AccountManager] for receiving intents at a time of your choosing.
 * 
 * Never null.
 */
inline val Context.accountManager: AccountManager
    get() = getSystemServiceOrThrow(Context.ACCOUNT_SERVICE)

/**
 * Get a [DevicePolicyManager] for working with global device policy management.
 * 
 * Never null.
 */
inline val Context.devicePolicyManager: DevicePolicyManager
    get() = getSystemServiceOrThrow(Context.DEVICE_POLICY_SERVICE)

/**
 * Get a [DropBoxManager] instance for recording diagnostic logs.
 * 
 * Never null.
 */
inline val Context.dropBoxManager: DropBoxManager
    get() = getSystemServiceOrThrow(Context.DROPBOX_SERVICE)

/**
 * Get a [UiModeManager] for controlling UI modes.
 * 
 * Never null.
 */
inline val Context.uiModeManager: UiModeManager
    get() = getSystemServiceOrThrow(Context.UI_MODE_SERVICE)

/**
 * Get a [DownloadManager] for requesting HTTP downloads.
 * 
 * Never null.
 */
inline val Context.downloadManager: DownloadManager
    get() = getSystemServiceOrThrow(Context.DOWNLOAD_SERVICE)

/**
 * Get a [StorageManager] for accessing system storage functions.
 */
inline val Context.storageManager: StorageManager?
    get() = getSystemServiceOrNull(Context.STORAGE_SERVICE)

/**
 * Get a [NfcManager] for using NFC.
 * 
 * Never null.
 */
inline val Context.nfcManager: NfcManager
    get() = getSystemServiceOrThrow(Context.NFC_SERVICE)

/**
 * Get a [UsbManager] for access to USB devices (as a USB host)
 * and for controlling this device's behavior as a USB device.
 */
inline val Context.usbManager: UsbManager?
    get() = getSystemServiceOrNull(Context.USB_SERVICE)

/**
 * Get a [TextServicesManager] for accessing text services.
 */
inline val Context.textServicesManager: TextServicesManager?
    get() = getSystemServiceOrNull(Context.TEXT_SERVICES_MANAGER_SERVICE)

/**
 * Get a [WifiP2pManager] for handling management of Wi-Fi peer-to-peer connections.
 */
inline val Context.wifiP2pManager: WifiP2pManager?
    get() = getSystemServiceOrNull(Context.WIFI_P2P_SERVICE)

/**
 * Get a [InputManager] for interacting with input devices.
 * 
 * Never null.
 */
@get:RequiresApi(16)
inline val Context.inputManager: InputManager
    get() = getSystemServiceOrThrow(Context.INPUT_SERVICE)

/**
 * Get a [MediaRouter] for controlling and managing routing of media.
 */
@get:RequiresApi(16)
inline val Context.mediaRouter: MediaRouter?
    get() = getSystemServiceOrNull(Context.MEDIA_ROUTER_SERVICE)

/**
 * Get a [NsdManager] for handling management of network service discovery.
 */
@get:RequiresApi(16)
inline val Context.nsdManager: NsdManager?
    get() = getSystemServiceOrNull(Context.NSD_SERVICE)

/**
 * Get a [DisplayManager] for interacting with display devices.
 * 
 * Never null.
 */
@get:RequiresApi(17)
inline val Context.displayManager: DisplayManager
    get() = getSystemServiceOrThrow(Context.DISPLAY_SERVICE)

/**
 * Get a [UserManager] for managing users on devices that support multiple users.
 * 
 * Never null.
 */
@get:RequiresApi(17)
inline val Context.userManager: UserManager
    get() = getSystemServiceOrThrow(Context.USER_SERVICE)

/**
 * Get a [BluetoothManager] for using Bluetooth.
 */
@get:RequiresApi(18)
inline val Context.bluetoothManager: BluetoothManager?
    get() = getSystemServiceOrNull(Context.BLUETOOTH_SERVICE)

/**
 * Get a [AppOpsManager] for tracking application operations on the device.
 */
@get:RequiresApi(19)
inline val Context.appOpsManager: AppOpsManager?
    get() = getSystemServiceOrNull(Context.APP_OPS_SERVICE)

/**
 * Get a [CaptioningManager] for obtaining captioning properties
 * and listening for changes in captioning preferences.
 * 
 * Never null.
 */
@get:RequiresApi(19)
inline val Context.captioningManager: CaptioningManager
    get() = getSystemServiceOrThrow(Context.CAPTIONING_SERVICE)

/**
 * Get a [ConsumerIrManager] for transmitting infrared signals from the device.
 */
@get:RequiresApi(19)
inline val Context.consumerIrManager: ConsumerIrManager?
    get() = getSystemServiceOrNull(Context.CONSUMER_IR_SERVICE)

/**
 * Get a [PrintManager] for printing and managing printers and print tasks.
 */
@get:RequiresApi(19)
inline val Context.printManager: PrintManager?
    get() = getSystemServiceOrNull(Context.PRINT_SERVICE)

/**
 * Get a [AppWidgetManager] for accessing AppWidgets.
 */
@get:RequiresApi(21)
inline val Context.appWidgetManager: AppWidgetManager?
    get() = getSystemServiceOrNull(Context.APPWIDGET_SERVICE)

/**
 * Get a [BatteryManager] for managing battery state.
 * 
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.batteryManager: BatteryManager
    get() = getSystemServiceOrThrow(Context.BATTERY_SERVICE)

/**
 * Get a [CameraManager] for interacting with camera devices.
 */
@get:RequiresApi(21)
inline val Context.cameraManager: CameraManager?
    get() = getSystemServiceOrNull(Context.CAMERA_SERVICE)

/**
 * Get a [JobScheduler] instance for managing occasional background tasks.
 * 
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.jobScheduler: JobScheduler
    get() = getSystemServiceOrThrow(Context.JOB_SCHEDULER_SERVICE)

/**
 * Get a [LauncherApps] for querying and monitoring launchable apps across profiles of a user.
 * 
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.launcherApps: LauncherApps
    get() = getSystemServiceOrThrow(Context.LAUNCHER_APPS_SERVICE)

/**
 * Get a [MediaProjectionManager] instance for managing media projection sessions.
 */
@get:RequiresApi(21)
inline val Context.mediaProjectionManager: MediaProjectionManager?
    get() = getSystemServiceOrNull(Context.MEDIA_PROJECTION_SERVICE)

/**
 * Get a [MediaSessionManager] for managing media Sessions.
 * 
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.mediaSessionManager: MediaSessionManager
    get() = getSystemServiceOrThrow(Context.MEDIA_SESSION_SERVICE)

/**
 * Get a [RestrictionsManager] for retrieving application restrictions
 * and requesting permissions for restricted operations.
 */
@get:RequiresApi(21)
inline val Context.restrictionsManager: RestrictionsManager?
    get() = getSystemServiceOrNull(Context.RESTRICTIONS_SERVICE)

/**
 * Get a [TelecomManager] to manage telecom-related features of the device.
 * 
 * Never null.
 */
@get:RequiresApi(21)
inline val Context.telecomManager: TelecomManager
    get() = getSystemServiceOrThrow(Context.TELECOM_SERVICE)

/**
 * Get a [TvInputManager] for interacting with TV inputs on the device.
 */
@get:RequiresApi(21)
inline val Context.tvInputManager: TvInputManager?
    get() = getSystemServiceOrNull(Context.TV_INPUT_SERVICE)

/**
 * Get a [SubscriptionManager] for handling management the telephony subscriptions of the device.
 * 
 * Never null.
 */
@get:RequiresApi(22)
inline val Context.subscriptionManager: SubscriptionManager
    get() = getSystemServiceOrThrow(Context.TELEPHONY_SUBSCRIPTION_SERVICE)

/**
 * Get a [UsageStatsManager] for querying device usage stats.
 */
@get:RequiresApi(22)
inline val Context.usageStatsManager: UsageStatsManager?
    get() = getSystemServiceOrNull(Context.USAGE_STATS_SERVICE)

/**
 * Get a [CarrierConfigManager] for reading carrier configuration values.
 * 
 * Never null.
 */
@get:RequiresApi(23)
inline val Context.carrierConfigManager: CarrierConfigManager
    get() = getSystemServiceOrThrow(Context.CARRIER_CONFIG_SERVICE)

/**
 * Get a [FingerprintManager] for handling management of fingerprints.
 */
@Deprecated("Use BiometricPrompt instead.")
@get:RequiresApi(23)
inline val Context.fingerprintManager: FingerprintManager?
    get() = getSystemServiceOrNull(Context.FINGERPRINT_SERVICE)

/**
 * Get a [MidiManager] for accessing the MIDI service.
 */
@get:RequiresApi(23)
inline val Context.midiManager: MidiManager?
    get() = getSystemServiceOrNull(Context.MIDI_SERVICE)

/**
 * Get a [NetworkStatsManager] for querying network usage stats.
 */
@get:RequiresApi(23)
inline val Context.networkStatsManager: NetworkStatsManager?
    get() = getSystemServiceOrNull(Context.NETWORK_STATS_SERVICE)

/**
 * Get a [HardwarePropertiesManager] for accessing the hardware properties service.
 */
@get:RequiresApi(24)
inline val Context.hardwarePropertiesManager: HardwarePropertiesManager?
    get() = getSystemServiceOrNull(Context.HARDWARE_PROPERTIES_SERVICE)

/**
 * Get a [SystemHealthManager] for accessing system health (battery, power, memory, etc) metrics.
 */
@get:RequiresApi(24)
inline val Context.systemHealthManager: SystemHealthManager?
    get() = getSystemServiceOrNull(Context.SYSTEM_HEALTH_SERVICE)

/**
 * Get a [ShortcutManager] for accessing the launcher shortcut service.
 * 
 * Never null.
 */
@get:RequiresApi(25)
inline val Context.shortcutManager: ShortcutManager
    get() = getSystemServiceOrThrow(Context.SHORTCUT_SERVICE)

/**
 * Get a [CompanionDeviceManager] for managing companion devices.
 */
@get:RequiresApi(26)
inline val Context.companionDeviceManager: CompanionDeviceManager?
    get() = getSystemServiceOrNull(Context.COMPANION_DEVICE_SERVICE)

/**
 * Get a [StorageStatsManager] for accessing system storage statistics.
 */
@get:RequiresApi(26)
inline val Context.storageStatsManager: StorageStatsManager?
    get() = getSystemServiceOrNull(Context.STORAGE_STATS_SERVICE)

/**
 * Get a [TextClassificationManager] for text classification services.
 *
 * Never null.
 */
@get:RequiresApi(26)
inline val Context.textClassificationManager: TextClassificationManager
    get() = getSystemServiceOrThrow(Context.TEXT_CLASSIFICATION_SERVICE)

/**
 * Get a [WifiAwareManager] for handling management of Wi-Fi Aware.
 */
@get:RequiresApi(26)
inline val Context.wifiAwareManager: WifiAwareManager?
    get() = getSystemServiceOrNull(Context.WIFI_AWARE_SERVICE)

/**
 * Get a [CrossProfileApps] for cross profile operations.
 *
 * Never null.
 */
@get:RequiresApi(28)
inline val Context.crossProfileApps: CrossProfileApps
    get() = getSystemServiceOrThrow(Context.CROSS_PROFILE_APPS_SERVICE)

/**
 * Get a [EuiccManager] to manage the device eUICC (embedded SIM).
 *
 * Never null.
 */
@get:RequiresApi(28)
inline val Context.euiccManager: EuiccManager
    get() = getSystemServiceOrThrow(Context.EUICC_SERVICE)

/**
 * Get a [IpSecManager] for encrypting Sockets or Networks with IPSec.
 */
@get:RequiresApi(28)
inline val Context.ipSecManager: IpSecManager?
    get() = getSystemServiceOrNull(Context.IPSEC_SERVICE)

/**
 * Get a [WifiRttManager] for ranging devices with wifi.
 */
@get:RequiresApi(28)
inline val Context.wifiRttManager: WifiRttManager?
    get() = getSystemServiceOrNull(Context.WIFI_RTT_RANGING_SERVICE)

/**
 * Get a [BiometricManager] for handling biometric and PIN/pattern/password authentication.
 *
 * Never null.
 */
@get:RequiresApi(29)
inline val Context.biometricManager: BiometricManager
    get() = getSystemServiceOrThrow(Context.BIOMETRIC_SERVICE)

/**
 * Get a [RoleManager] for managing roles.
 *
 * Never null.
 */
@get:RequiresApi(29)
inline val Context.roleManager: RoleManager
    get() = getSystemServiceOrThrow(Context.ROLE_SERVICE)

/**
 * Return the handle to a system-level service by name, or `null` when service is not found.
 */
inline fun <reified T> Context.getSystemServiceOrNull(name: String): T? =
    getSystemService(name) as T?

/**
 * Return the handle to a system-level service by name.
 *
 * @throws ServiceNotFoundException When service is not found.
 */
inline fun <reified T> Context.getSystemServiceOrThrow(name: String): T =
    getSystemServiceOrNull<T>(name)
        ?: throw ServiceNotFoundException("${T::class.java.simpleName} not found.")

/**
 * Return the handle to a system-level service by class, or `null` when service is not found.
 */
@SuppressLint("NewApi")
inline fun <reified T> Context.getSystemServiceOrNull(): T? = try {
    ContextCompat.getSystemService(this, T::class.java)
} catch (_: LinkageError) {
    getSystemService(T::class.java)
}

/**
 * Return the handle to a system-level service by class.
 *
 * @throws ServiceNotFoundException When service is not found.
 */
inline fun <reified T> Context.getSystemServiceOrThrow(): T =
    getSystemServiceOrNull<T>()
        ?: throw ServiceNotFoundException("${T::class.java.simpleName} not found.")

typealias ServiceNotFoundException = NullPointerException

// Split into commons-servicesx.

/**
 * Get a [NotificationManagerCompat] instance for a provided context.
 *
 * Never null.
 */
@Deprecated("Use commons-servicesx.")
inline val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)

/**
 * Gets an instance of the display manager given the context.
 *
 * Never null.
 */
@Deprecated("Use commons-servicesx.")
inline val Context.displayManagerCompat: DisplayManagerCompat
    get() = DisplayManagerCompat.getInstance(this)

/**
 * Get a [FingerprintManagerCompat] instance for a provided context.
 *
 * Never null.
 */
@Deprecated("Use commons-servicesx.")
inline val Context.fingerprintManagerCompat: FingerprintManagerCompat
    get() = FingerprintManagerCompat.from(this)
