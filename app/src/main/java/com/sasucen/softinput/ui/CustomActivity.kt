package com.sasucen.softinput.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView
import com.sasucen.softinput.Constants
import com.sasucen.softinput.R
import com.sasucen.softinput.base.BaseActivity
import com.sasucen.softinput.bean.emoji.EmojiBean
import com.sasucen.softinput.bean.emoji.EmojiDataSource
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import java.util.*


class CustomActivity : BaseActivity() {

    private val emojiDataSources: MutableList<EmojiDataSource> = mutableListOf()
    private var index = 0
    val data = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        setContentView(R.layout.activity_custom)
        initToolBar(custom_top_layout.tb_toolbar)
        tb_toolbar.tv_title.text = "计算软键盘高度"
        custom_items.run {
            this.layoutManager = LinearLayoutManager(this@CustomActivity)

            for (i in 0 until 30) {
                data.add("序号$i")
            }
            this.adapter = object : CommonAdapter<String>(
                this@CustomActivity,
                R.layout.item_custom, data
            ) {
                @SuppressLint("LogNotTimber")
                override fun convert(holder: ViewHolder?, t: String?, position: Int) {
                    val tv = holder?.getView<TextView>(R.id.item_tv_input)
                    tv?.text = t
                    tv?.setOnClickListener {
                        index = position
                        layout_face_panel.showEmojiPanel()
                        Log.e("TAG", "show ${tv.hashCode()}")
                    }
                }

            }
        }
        loadEmojis()
        layout_face_panel.initEmojiPanel(emojiDataSources)
        layout_face_panel.setCallback {
            data[index] = it
            custom_items.adapter?.notifyDataSetChanged()
        }
    }

//    override fun initToolBar(toolbar: Toolbar) {
//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
//    }

    private fun loadEmojis() {
        for (i in 0..1) {
            val emojiDataSource = EmojiDataSource()
            val typeEmojiBeans = ArrayList<EmojiBean>()
            if (i == 0) {
                for (j in 0 until Constants.TYPE01_EMOJI_NAME.size) {
                    val emojiBean = EmojiBean()
                    emojiBean.emojiName = Constants.TYPE01_EMOJI_NAME[j]
                    emojiBean.emojiResource = Constants.TYPE01_EMOJI_DREWABLES[j]
                    typeEmojiBeans.add(emojiBean)
                }
                emojiDataSource.emojiType = Constants.EmojiType.EMOJI_TYPE_01
            } else {
                for (j in 0 until Constants.TYPE02_EMOJI_NAME.size) {
                    val emojiBean = EmojiBean()
                    emojiBean.emojiName = Constants.TYPE02_EMOJI_NAME[j]
                    emojiBean.emojiResource = Constants.TYPE02_EMOJI_DREWABLES[j]
                    typeEmojiBeans.add(emojiBean)
                }
                emojiDataSource.emojiType = Constants.EmojiType.EMOJI_TYPE_02
            }
            emojiDataSource.emojiList = typeEmojiBeans
            emojiDataSources.add(emojiDataSource)
        }
    }


}
