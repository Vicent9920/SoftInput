package com.sasucen.softinput.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.EditText
import com.sasucen.softinput.R
import com.sasucen.softinput.base.BaseActivity
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class ListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initToolBar(list_top_layout.tb_toolbar)
        initStatusBar()
        list_top_layout.tv_title.text = "列表Activity"
        list_items.run {
            this.layoutManager = LinearLayoutManager(this@ListActivity)
            val data = mutableListOf<String>()
            for (i in 0 until 30) {
                data.add("$i")
            }
            this.adapter = object : CommonAdapter<String>(
                this@ListActivity,
                R.layout.item_list, data
            ) {
                override fun convert(holder: ViewHolder?, t: String?, position: Int) {
                    val et = holder?.itemView as EditText
                    et.hint = t
                }

            }
        }
    }


}
