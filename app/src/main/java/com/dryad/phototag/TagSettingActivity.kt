package com.dryad.phototag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dryad.phototag.databinding.ActivityMainBinding
import com.dryad.phototag.databinding.ActivityTagSettingBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TagSettingActivity : AppCompatActivity(), TagAdapter.ItemClickListener {

    private lateinit var binding: ActivityTagSettingBinding

    var tagData: List<TagData> = listOf(TagData("",""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_tag_setting)

        binding = ActivityTagSettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.tagRecyclerView

        GlobalScope.launch {
            tagData = AppDatabase.getDatabase_tag(applicationContext).DataBaseDao().getAllTag()
        }

        //Adapterの設定
        val adapter = TagAdapter(tagData, this)
        recyclerView.adapter = adapter

        //LayoutManagerの設定
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    override fun onItemClickListener() {
        //タグのタップ後処理
        //タグの詳細設定かな？
        //フラグメント起動
        Log.d("Listener","tapped")
    }
}