package com.dryad.phototag

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        binding.floatingAddButton.setOnClickListener {
            val dialog = AddTagDialogFragment()
            dialog.show(supportFragmentManager, "simple")
            true
        }

    }

    override fun onItemClickListener() {
        //タグのタップ後処理
        //タグの詳細設定かな？
        //フラグメント起動
        Log.d("Listener","tapped")

    }

    //アプリバーにメニューを作成するメソッド
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //インフレーターを使ってメニューを表示させる
        val inflater = menuInflater
        inflater.inflate(R.menu.tagsetting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_tag -> {
                //タグ追加フラグメント起動

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}