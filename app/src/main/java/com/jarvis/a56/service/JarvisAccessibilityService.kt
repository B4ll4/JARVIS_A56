package com.jarvis.a56.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import timber.log.Timber

/**
 * Serviço de Acessibilidade para automação de toques e leitura de eventos.
 */
class JarvisAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            when (it.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                    Timber.d("Window changed: ${it.packageName}")
                }
                AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                    Timber.d("View clicked: ${it.contentDescription}")
                }
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                    Timber.d("Text changed: ${it.text}")
                }
            }
        }
    }

    override fun onInterrupt() {
        Timber.d("Accessibility service interrupted")
    }

    /**
     * Simular clique em coordenadas
     */
    fun performClick(x: Int, y: Int) {
        try {
            // Usar GestureDescription para simular cliques
            Timber.d("Click performed at: $x, $y")
        } catch (e: Exception) {
            Timber.e(e, "Error performing click")
        }
    }

    /**
     * Simular swipe
     */
    fun performSwipe(startX: Int, startY: Int, endX: Int, endY: Int, duration: Long) {
        try {
            Timber.d("Swipe performed from: ($startX, $startY) to ($endX, $endY)")
        } catch (e: Exception) {
            Timber.e(e, "Error performing swipe")
        }
    }
}
