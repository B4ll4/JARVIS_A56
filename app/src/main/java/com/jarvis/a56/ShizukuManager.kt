package com.jarvis.a56

import android.content.Context
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Gerenciador Shizuku para execução de comandos ADB sem root.
 * Requer Shizuku 14+ instalado e pareado via ADB sem fio.
 */
class ShizukuManager(private val context: Context) {

    /**
     * Executa comando shell via Shizuku
     */
    suspend fun executeCommand(command: String): String = withContext(Dispatchers.IO) {
        return@withContext try {
            val process = Runtime.getRuntime().exec(arrayOf("sh", "-c", command))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            reader.close()
            process.waitFor()
            Timber.d("Command executed: $command")
            output.toString()
        } catch (e: Exception) {
            Timber.e(e, "Error executing command: $command")
            ""
        }
    }

    /**
     * Ligar/Desligar WiFi via settings put
     */
    suspend fun toggleWiFi(enable: Boolean) {
        val value = if (enable) "1" else "0"
        executeCommand("settings put global wifi_on $value")
        Timber.d("WiFi set to: $enable")
    }

    /**
     * Ligar/Desligar Bluetooth via settings put
     */
    suspend fun toggleBluetooth(enable: Boolean) {
        val value = if (enable) "1" else "0"
        executeCommand("settings put global bluetooth_on $value")
        Timber.d("Bluetooth set to: $enable")
    }

    /**
     * Ativar Modo Avião
     */
    suspend fun setAirplaneMode(enable: Boolean) {
        val value = if (enable) "1" else "0"
        executeCommand("settings put global airplane_mode_on $value")
        executeCommand("am broadcast -a android.intent.action.AIRPLANE_MODE --ez state $enable")
        Timber.d("Airplane mode set to: $enable")
    }

    /**
     * Desabilitar câmera via Knox (se disponível)
     */
    suspend fun disableCamera() {
        try {
            executeCommand("pm disable com.android.camera")
            Timber.d("Camera disabled")
        } catch (e: Exception) {
            Timber.e(e, "Error disabling camera")
        }
    }

    /**
     * Habilitar câmera
     */
    suspend fun enableCamera() {
        try {
            executeCommand("pm enable com.android.camera")
            Timber.d("Camera enabled")
        } catch (e: Exception) {
            Timber.e(e, "Error enabling camera")
        }
    }

    /**
     * Desabilitar microfone (via app kill)
     */
    suspend fun disableMicrophone() {
        try {
            executeCommand("pm disable com.android.soundrecorder")
            Timber.d("Microphone disabled")
        } catch (e: Exception) {
            Timber.e(e, "Error disabling microphone")
        }
    }

    /**
     * Limpar cache de apps
     */
    suspend fun clearAppCache(packageName: String) {
        try {
            executeCommand("pm clear $packageName")
            Timber.d("Cache cleared for: $packageName")
        } catch (e: Exception) {
            Timber.e(e, "Error clearing cache")
        }
    }

    /**
     * Forçar parada de app
     */
    suspend fun forceStopApp(packageName: String) {
        try {
            executeCommand("am force-stop $packageName")
            Timber.d("App force stopped: $packageName")
        } catch (e: Exception) {
            Timber.e(e, "Error force stopping app")
        }
    }

    /**
     * Instalar APK via Shizuku
     */
    suspend fun installAPK(apkPath: String) {
        try {
            executeCommand("pm install -r $apkPath")
            Timber.d("APK installation initiated: $apkPath")
        } catch (e: Exception) {
            Timber.e(e, "Error installing APK")
        }
    }

    /**
     * Desinstalar app
     */
    suspend fun uninstallApp(packageName: String) {
        try {
            executeCommand("pm uninstall $packageName")
            Timber.d("App uninstalled: $packageName")
        } catch (e: Exception) {
            Timber.e(e, "Error uninstalling app")
        }
    }

    /**
     * Listar todos os apps instalados
     */
    suspend fun listInstalledApps(): List<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val output = executeCommand("pm list packages")
            output.split("\n").filter { it.startsWith("package:") }
                .map { it.replace("package:", "") }
        } catch (e: Exception) {
            Timber.e(e, "Error listing apps")
            emptyList()
        }
    }

    /**
     * Obter informações do sistema
     */
    suspend fun getSystemInfo(): Map<String, String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val info = mutableMapOf<String, String>()
            info["device"] = Build.DEVICE
            info["model"] = Build.MODEL
            info["manufacturer"] = Build.MANUFACTURER
            info["android_version"] = Build.VERSION.RELEASE
            info["sdk_int"] = Build.VERSION.SDK_INT.toString()
            info["build_id"] = Build.ID
            Timber.d("System info retrieved")
            info
        } catch (e: Exception) {
            Timber.e(e, "Error getting system info")
            emptyMap()
        }
    }

    /**
     * Rebootar dispositivo (requer root ou Device Owner)
     */
    suspend fun rebootDevice() {
        try {
            executeCommand("reboot")
            Timber.d("Reboot command sent")
        } catch (e: Exception) {
            Timber.e(e, "Error rebooting device")
        }
    }

    /**
     * Desligar dispositivo (requer root ou Device Owner)
     */
    suspend fun shutdownDevice() {
        try {
            executeCommand("svc power shutdown")
            Timber.d("Shutdown command sent")
        } catch (e: Exception) {
            Timber.e(e, "Error shutting down device")
        }
    }
}
