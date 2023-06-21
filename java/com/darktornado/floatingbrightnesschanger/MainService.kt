package com.darktornado.floatingbrightnesschanger

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainService : Service() {

    private var btn: TextView? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createUI()
        val noti: Notification.Builder
        if (Build.VERSION.SDK_INT >= 26) {
            val nc = NotificationChannel("main_nofi_channel", "foreground", NotificationManager.IMPORTANCE_LOW)
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(nc)
            noti = Notification.Builder(this, "main_nofi_channel")
        } else {
            noti = Notification.Builder(this);
        }
        noti.setSmallIcon(R.mipmap.ic_launcher)
        noti.setContentTitle("화면 밝기 조절")
        noti.setContentText("화면 밝기 조절 상시 대기 실행중")
        noti.setContentIntent(PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0))
        startForeground(1, noti.build())
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
                    WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT)
        }
        mParams.gravity = Gravity.RIGHT or Gravity.TOP
        if (btn != null) wm.removeView(btn)
        btn = TextView(this)
        btn?.setBackgroundColor(Color.argb(90, 0, 0, 0))
        btn?.setLayoutParams(LinearLayout.LayoutParams(-1, -1))
        btn?.setOnClickListener(View.OnClickListener { view: View? ->
            Toast.makeText(this, "test", 1).show()
        })

        wm.addView(btn, mParams)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            val wm = getSystemService(WINDOW_SERVICE) as WindowManager
            if (btn != null) wm.removeView(btn)
            btn = null
        } catch (e: Exception) {
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
//        TODO("Not yet implemented")
        return null
    }

    fun dip2px(dips: Int) = Math.ceil((dips * resources.displayMetrics.density).toDouble()).toInt()

}