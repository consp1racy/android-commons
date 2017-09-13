package net.xpece.android.content

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
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.hardware.display.DisplayManagerCompat
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
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

inline val Context.activityManager: ActivityManager
    get() = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
inline val Context.alarmManager: AlarmManager
    get() = getSystemService(Context.ALARM_SERVICE) as AlarmManager
inline val Context.audioManager: AudioManager
    get() = getSystemService(Context.AUDIO_SERVICE) as AudioManager
inline val Context.clipboardManager: ClipboardManager
    get() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
inline val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
inline val Context.keyguardManager: KeyguardManager?
    get() = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager?
inline val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)
inline val Context.locationManager: LocationManager
    get() = getSystemService(Context.LOCATION_SERVICE) as LocationManager
inline val Context.notificationManager: NotificationManager
    get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
inline val Context.notificationManagerCompat: NotificationManagerCompat
    get() = NotificationManagerCompat.from(this)
inline val Context.powerManager: PowerManager
    get() = getSystemService(Context.POWER_SERVICE) as PowerManager
inline val Context.searchManager: SearchManager?
    get() = getSystemService(Context.SEARCH_SERVICE) as SearchManager
inline val Context.sensorManager: SensorManager?
    get() = getSystemService(Context.SENSOR_SERVICE) as SensorManager
inline val Context.telephonyManager: TelephonyManager?
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
inline val Context.vibrator: Vibrator?
    get() = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
inline val Context.wallpaperService: WallpaperManager
    get() = getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager
inline val Context.wifiManager: WifiManager?
    get() = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
inline val Context.windowManager: WindowManager
    get() = getSystemService(Context.WINDOW_SERVICE) as WindowManager
inline val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
inline val Context.accessibilityManager: AccessibilityManager
    get() = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
inline val Context.accountManager: AccountManager
    get() = getSystemService(Context.ACCOUNT_SERVICE) as AccountManager
inline val Context.devicePolicyManager: DevicePolicyManager
    get() = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
inline val Context.dropBoxManager: DropBoxManager
    get() = getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager
inline val Context.uiModeManager: UiModeManager
    get() = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
inline val Context.downloadManager: DownloadManager
    get() = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
inline val Context.storageManager: StorageManager
    get() = getSystemService(Context.STORAGE_SERVICE) as StorageManager
inline val Context.nfcManager: NfcManager?
    get() = getSystemService(Context.NFC_SERVICE) as NfcManager?
inline val Context.usbManager: UsbManager?
    get() = getSystemService(Context.USB_SERVICE) as UsbManager?
inline val Context.textServicesManager: TextServicesManager
    get() = getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager
inline val Context.wifiP2pManager: WifiP2pManager?
    get() = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager?

@get:RequiresApi(16)
inline val Context.inputManager: InputManager
    get() = getSystemService(Context.INPUT_SERVICE) as InputManager

@get:RequiresApi(16)
inline val Context.mediaRouter: MediaRouter
    get() = getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter

@get:RequiresApi(16)
inline val Context.nsdManager: NsdManager?
    get() = getSystemService(Context.NSD_SERVICE) as NsdManager?

@get:RequiresApi(17)
inline val Context.displayManager: DisplayManager
    get() = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
inline val Context.displayManagerCompat: DisplayManagerCompat
    get() = DisplayManagerCompat.getInstance(this)

@get:RequiresApi(17)
inline val Context.userManager: UserManager
    get() = getSystemService(Context.USER_SERVICE) as UserManager

@get:RequiresApi(18)
inline val Context.bluetoothManager: BluetoothManager?
    get() = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager?

@get:RequiresApi(19)
inline val Context.appOpsManager: AppOpsManager
    get() = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

@get:RequiresApi(19)
inline val Context.captioningManager: CaptioningManager
    get() = getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager

@get:RequiresApi(19)
inline val Context.consumerIrManager: ConsumerIrManager?
    get() = getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager?

@get:RequiresApi(19)
inline val Context.printManager: PrintManager?
    get() = getSystemService(Context.PRINT_SERVICE) as PrintManager?

@get:RequiresApi(21)
inline val Context.appWidgetManager: AppWidgetManager
    get() = getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager

@get:RequiresApi(21)
inline val Context.batteryManager: BatteryManager?
    get() = getSystemService(Context.BATTERY_SERVICE) as BatteryManager?

@get:RequiresApi(21)
inline val Context.cameraManager: CameraManager?
    get() = getSystemService(Context.CAMERA_SERVICE) as CameraManager?

@get:RequiresApi(21)
inline val Context.jobScheduler: JobScheduler
    get() = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

@get:RequiresApi(21)
inline val Context.launcherApps: LauncherApps
    get() = getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

@get:RequiresApi(21)
inline val Context.mediaProjectionManager: MediaProjectionManager
    get() = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

@get:RequiresApi(21)
inline val Context.mediaSessionManager: MediaSessionManager
    get() = getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager

@get:RequiresApi(21)
inline val Context.restrictionsManager: RestrictionsManager
    get() = getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager

@get:RequiresApi(21)
inline val Context.telecomManager: TelecomManager?
    get() = getSystemService(Context.TELECOM_SERVICE) as TelecomManager?

@get:RequiresApi(21)
inline val Context.tvInputManager: TvInputManager?
    get() = getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager?

@get:RequiresApi(22)
inline val Context.subscriptionManager: SubscriptionManager?
    get() = getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager?

@get:RequiresApi(22)
inline val Context.usageStatsManager: UsageStatsManager
    get() = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

@get:RequiresApi(23)
inline val Context.carrierConfigManager: CarrierConfigManager?
    get() = getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager?

@get:RequiresApi(23)
inline val Context.fingerprintManager: FingerprintManager?
    get() = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager?
inline val Context.fingerprintManagerCompat: FingerprintManagerCompat
    get() = FingerprintManagerCompat.from(this)

@get:RequiresApi(23)
inline val Context.midiManager: MidiManager?
    get() = getSystemService(Context.MIDI_SERVICE) as MidiManager?

@get:RequiresApi(23)
inline val Context.networkStatsManager: NetworkStatsManager
    get() = getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

@get:RequiresApi(24)
inline val Context.hardwarePropertiesManager: HardwarePropertiesManager
    get() = getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager

@get:RequiresApi(24)
inline val Context.systemHealthManager: SystemHealthManager
    get() = getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager

@get:RequiresApi(25)
inline val Context.shortcutManager: ShortcutManager
    get() = getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager

@get:RequiresApi(26)
inline val Context.companionDeviceManager : CompanionDeviceManager?
    get() = getSystemService(Context.COMPANION_DEVICE_SERVICE) as CompanionDeviceManager?

@get:RequiresApi(26)
inline val Context.storageStatsManager: StorageStatsManager
    get() = getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager

@get:RequiresApi(26)
inline val Context.textClassificationManager: TextClassificationManager
    get() = getSystemService(Context.TEXT_CLASSIFICATION_SERVICE) as TextClassificationManager

@get:RequiresApi(26)
inline val Context.wifiAwareManager: WifiAwareManager?
    get() = getSystemService(Context.WIFI_AWARE_SERVICE) as WifiAwareManager?
