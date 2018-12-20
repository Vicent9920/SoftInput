package com.sasucen.softinput.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sasucen.softinput.Constants
import com.sasucen.softinput.R
import com.sasucen.softinput.bean.emoji.EmojiBean
import com.sasucen.softinput.bean.emoji.EmojiDataSource
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_listOption.setOnClickListener { startActivity(Intent(this, ListActivity::class.java)) }
        tv_other.setOnClickListener { startActivity(Intent(this, RelativeActivity::class.java)) }
        tv_custom.setOnClickListener { startActivity(Intent(this,CustomActivity::class.java)) }

    }


}
