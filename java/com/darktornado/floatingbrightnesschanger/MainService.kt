package com.darktornado.floatingbrightnesschanger

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

class MainService : Service() {

    private var btn: TextView? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createUI();
        return START_NOT_STICKY
    }

    fun createUI() {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager;
        val mParams: WindowManager.LayoutParams
        if (Build.VERSION.SDK_INT >= 26) {
            mParams = WindowManager.LayoutParams(
                    dip2px(15), dip2px(22),
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT)
        } else {
            mParams = WindowManager.LayoutParams(
                    dip2px(15), dip2px(22),
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT)
        }
        mParams.gravity = Gravity.RIGHT or Gravity.TOP
        if (btn != null) wm.removeView(btn)
        btn = TextView(this)
        btn?.setBackgroundColor(Color.argb(90, 0, 0, 0))
        btn?.setLayoutParams(LinearLayout.LayoutParams(-1, -1))
//        btn.setOnClickListener(View.OnClickListener { view: View? -> openMenu(Gravity.RIGHT, +1) })

        wm.addView(btn, mParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        try {
            wm.removeView(btn)
        } catch (e: Exception) {
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
//        TODO("Not yet implemented")
        return null
    }

    fun dip2px(dips: Int) = Math.ceil((dips * resources.displayMetrics.density).toDouble()).toInt()

}