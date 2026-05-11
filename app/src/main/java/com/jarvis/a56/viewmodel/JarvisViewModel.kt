package com.jarvis.a56.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class JarvisViewModel(application: Application) : AndroidViewModel(application) {

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _systemStatus = MutableStateFlow("Ready")
    val systemStatus: StateFlow<String> = _systemStatus.asStateFlow()

    private val _batteryLevel = MutableStateFlow(85)
    val batteryLevel: StateFlow<Int> = _batteryLevel.asStateFlow()

    private val systemManager = SystemControlManager(application)

    fun toggleListening() {
        _isListening.value = !_isListening.value
        _systemStatus.value = if (_isListening.value) "Listening..." else "Ready"
    }

    fun toggleWiFi() {
        systemManager.toggleWiFi()
        _systemStatus.value = "WiFi toggled"
    }

    fun toggleBluetooth() {
        systemManager.toggleBluetooth()
        _systemStatus.value = "Bluetooth toggled"
    }

    fun toggleGPS() {
        systemManager.toggleGPS()
        _systemStatus.value = "GPS toggled"
    }

    fun openSettings() {
        systemManager.openSystemSettings()
        _systemStatus.value = "Opening Settings..."
    }

    fun updateBatteryLevel(level: Int) {
        _batteryLevel.value = level
    }
}
