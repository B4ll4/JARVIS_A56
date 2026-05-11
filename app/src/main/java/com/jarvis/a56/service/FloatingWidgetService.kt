package com.jarvis.a56.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout
import timber.log.Timber

/**
 * Serviço para exibir widget flutuante com animação do Reator Arc.
 */
class FloatingWidgetService : Service() {

    private var windowManager: WindowManager? = null
    private var floatingView: FrameLayout? = null
    private var params: WindowManager.LayoutParams? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("Floating widget service started")
        createFloatingWidget()
        return START_STICKY
    }

    private fun createFloatingWidget() {
        try {
            windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

            floatingView = FrameLayout(this).apply {
                setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }

            params = WindowManager.LayoutParams().apply {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                format = PixelFormat.TRANSLUCENT
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                width = 100
                height = 100
                gravity = Gravity.TOP or Gravity.START
                x = 0
                y = 0
            }

            windowManager?.addView(floatingView, params)
            Timber.d("Floating widget created")
        } catch (e: Exception) {
            Timber.e(e, "Error creating floating widget")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            floatingView?.let { windowManager?.removeView(it) }
            Timber.d("Floating widget destroyed")
        } catch (e: Exception) {
            Timber.e(e, "Error destroying floating widget")
        }
    }
}
