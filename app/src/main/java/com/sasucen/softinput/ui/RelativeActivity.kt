package com.sasucen.softinput.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sasucen.softinput.R

class RelativeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContentView(R.layout.activity_relative)

    }
}
