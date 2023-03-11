package com.dryad.phototag

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dryad.phototag.databinding.ActivityViewImageBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImageBinding
    private lateinit var contentUri: String
    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        MobileAds.initialize(this) {}

        binding = ActivityViewImageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        setSupportActionBar(binding.toolbar)

        contentUri = intent.getStringExtra("uri").toString()

        binding.imageView.setImageURI(Uri.parse(contentUri))

        GlobalScope.launch {
            if(contentUri!=null)
            binding.toolbar.title = AppDatabase.getDatabase_item(applicationContext).DataBaseDao().returnDisplayName(contentUri)
        }

    }

    //アプリバーにメニューを作成するメソッド
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //インフレーターを使ってメニューを表示させる
        val inflater = menuInflater
        inflater.inflate(R.menu.image_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.set_tag -> {
                //タグ追加処理
                val dialog = SetTagDialogFragment()
                val args = Bundle()
                args.putString("contentUri",contentUri)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "simple")
                true
            }
            R.id.show_info -> {
                //画像詳細表示処理
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}