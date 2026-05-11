package com.jarvis.a56

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.bluetooth.BluetoothAdapter
import android.location.LocationManager
import android.provider.Settings
import android.os.Build
import timber.log.Timber

class SystemControlManager(private val context: Application) {

    // WiFi Control
    fun toggleWiFi() {
        try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10+ requires Settings.Panel.ACTION_WIFI
                val intent = Intent(Settings.Panel.ACTION_WIFI)
                context.startActivity(intent)
            } else {
                wifiManager.isWifiEnabled = !wifiManager.isWifiEnabled
            }
            Timber.d("WiFi toggled")
        } catch (e: Exception) {
            Timber.e(e, "Error toggling WiFi")
        }
    }

    // Bluetooth Control
    fun toggleBluetooth() {
        try {
            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter != null) {
                if (bluetoothAdapter.isEnabled) {
                    bluetoothAdapter.disable()
                } else {
                    bluetoothAdapter.enable()
                }
                Timber.d("Bluetooth toggled")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error toggling Bluetooth")
        }
    }

    // GPS Control
    fun toggleGPS() {
        try {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            Timber.d("GPS settings opened")
        } catch (e: Exception) {
            Timber.e(e, "Error toggling GPS")
        }
    }

    // Screen Brightness
    fun setBrightness(level: Int) {
        try {
            val brightness = level.coerceIn(0, 255)
            Settings.System.putInt(
                context.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                brightness
            )
            Timber.d("Brightness set to $brightness")
        } catch (e: Exception) {
            Timber.e(e, "Error setting brightness")
        }
    }

    // Screen Timeout
    fun setScreenTimeout(milliseconds: Long) {
        try {
            Settings.System.putLong(
                context.contentResolver,
                Settings.System.SCREEN_OFF_TIMEOUT,
                milliseconds
            )
            Timber.d("Screen timeout set to $milliseconds ms")
        } catch (e: Exception) {
            Timber.e(e, "Error setting screen timeout")
        }
    }

    // Dark Mode
    fun toggleDarkMode() {
        try {
            val currentNightMode = context.resources.configuration.uiMode and
                    android.content.res.Configuration.UI_MODE_NIGHT_MASK
            val isDarkMode = currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES

            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            Timber.d("Dark mode toggle initiated")
        } catch (e: Exception) {
            Timber.e(e, "Error toggling dark mode")
        }
    }

    // Airplane Mode
    fun toggleAirplaneMode() {
        try {
            val isAirplaneModeOn = Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) != 0

            Settings.Global.putInt(
                context.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                if (isAirplaneModeOn) 0 else 1
            )

            val intent = Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            intent.putExtra("state", !isAirplaneModeOn)
            context.sendBroadcast(intent)
            Timber.d("Airplane mode toggled")
        } catch (e: Exception) {
            Timber.e(e, "Error toggling airplane mode")
        }
    }

    // Open System Settings
    fun openSystemSettings() {
        try {
            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
            Timber.d("System settings opened")
        } catch (e: Exception) {
            Timber.e(e, "Error opening settings")
        }
    }

    // Lock Screen
    fun lockScreen() {
        try {
            val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE)
                    as android.app.admin.DevicePolicyManager
            // This requires Device Admin to be enabled
            // devicePolicyManager.lockNow()
            Timber.d("Lock screen command sent")
        } catch (e: Exception) {
            Timber.e(e, "Error locking screen")
        }
    }

    // Get Battery Level
    fun getBatteryLevel(): Int {
        return try {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE)
                    as android.os.BatteryManager
            batteryManager.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } catch (e: Exception) {
            Timber.e(e, "Error getting battery level")
            -1
        }
    }

    // Get Battery Temperature
    fun getBatteryTemperature(): Int {
        return try {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE)
                    as android.os.BatteryManager
            batteryManager.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_TEMPERATURE)
        } catch (e: Exception) {
            Timber.e(e, "Error getting battery temperature")
            -1
        }
    }

    // Vibrate
    fun vibrate(duration: Long = 200) {
        try {
            val vibratorService = context.getSystemService(Context.VIBRATOR_SERVICE)
                    as android.os.Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibratorService.vibrate(
                    android.os.VibrationEffect.createOneShot(
                        duration,
                        android.os.VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                @Suppress("DEPRECATION")
                vibratorService.vibrate(duration)
            }
            Timber.d("Vibration triggered")
        } catch (e: Exception) {
            Timber.e(e, "Error triggering vibration")
        }
    }

    // Flashlight Control
    fun toggleFlashlight() {
        try {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE)
                    as android.hardware.camera2.CameraManager
            val cameraId = cameraManager.cameraIdList[0]
            // This requires camera permission
            // cameraManager.setTorchMode(cameraId, !isFlashlightOn)
            Timber.d("Flashlight toggle initiated")
        } catch (e: Exception) {
            Timber.e(e, "Error toggling flashlight")
        }
    }
}
