package com.darktornado.floatingbrightnesschanger

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this)
        layout.orientation = 1
        val on = Switch(this)
        on.text = "구석 터치 사용"
        layout.addView(on)
        val perm1 = Button(this)
        perm1.text = "다른 앱 위에 그리기 권한 허용"
        layout.addView(perm1)
        val perm2 = Button(this)
        perm2.text = "시스템 설정 변경 권한 허용"
        layout.addView(perm2)
        setContentView(layout)
    }
}