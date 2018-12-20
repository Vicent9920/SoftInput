package com.sasucen.softinput.base

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager

/**
 * 创建日期：2018/12/20 0020on 上午 11:02
 * 描述：
 * @author：Vincent
 * QQ：3332168769
 * 备注：
 */
open class BaseActivity : AppCompatActivity() {

     open fun initToolBar(toolbar: Toolbar) {

        setStatusBarPaddingAndHeight(toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }

    fun setStatusBarPaddingAndHeight(toolBar: View?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (toolBar != null) {
                val statusBarHeight = getSystemBarHeight()
                toolBar.setPadding(
                    toolBar.paddingLeft, statusBarHeight, toolBar.paddingRight,
                    toolBar.paddingBottom
                )
                toolBar.layoutParams.height += statusBarHeight
            }
        }
    }

    private fun getSystemBarHeight(): Int {
        val resources = resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }


     fun initStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        }

    }

}