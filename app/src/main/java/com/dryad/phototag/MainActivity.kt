package com.dryad.phototag

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.GridLayoutManager
import com.dryad.phototag.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), ItemAdapter.ItemClickListener {

    private lateinit var binding: ActivityMainBinding

    private val imageUris = mutableListOf<ItemDeta_register>()
    private val getdata = mutableListOf<ItemDatabase>()

    val colums: Int = 5
    val collection = if(Build.VERSION_CODES.Q <= Build.VERSION.SDK_INT){
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    }else{
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.BUCKET_DISPLAY_NAME
    )
    val selection = null
    val selectionArgs = null
    val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN}"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        //RecyclerViewの取得
        val recyclerView = binding.ViewList

        loadImages()

        Log.d("size", imageUris.size.toString())

        //Adapterの設定
        val adapter = ItemAdapter(this,imageUris, this)
        recyclerView.adapter = adapter

        //LayoutManagerの設定
        val layoutManager = GridLayoutManager(this, colums)
        recyclerView.layoutManager = layoutManager

    }

    override fun onResume() {
        super.onResume()

        /*
        READ_EXTERNAL_STORAGEパーミッション取得済みかどうかの確認。
        READ_EXTERNAL_STORAGEは実行時に権限を要求する必要がある。
        簡単のため、パーミッション関係の実装は正常系のみとしている。
        */
        val result = PermissionChecker.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        /* パーミッション未取得なら、要求する。 */
        if (result != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            return
        }
    }

    private fun loadImages(){

        imageUris.clear()
        getdata.clear()

        val query = contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        Log.d("MediaStore", collection.toString())

        query?.use{
            val idColmun = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val displayNameColmun = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            while (it.moveToNext()){
                //Log.d("query","info")
                val displayName = it.getString(displayNameColmun)
                val contentUri = ContentUris.withAppendedId(collection, it.getLong(idColmun))

                Log.d("URI", contentUri.toString())

                imageUris.add(ItemDeta_register(contentUri.toString(),displayName))
                getdata.add(ItemDatabase(contentUri.toString(), displayName, arrayListOf()))
            }
        }

        GlobalScope.launch {
            AppDatabase.getDatabase_item(applicationContext).DataBaseDao().insertAll(getdata)
            AppDatabase.getDatabase_item(applicationContext).DataBaseDao().getAllItem().forEach {
                Log.d("MainActivity", "${it.displayName}${it.uri}")
            }
        }

    }

    override fun onItemClickListener(uri :String) {
        val toViewImage = Intent(this, ViewImageActivity::class.java)
        toViewImage.putExtra("uri", uri)
        startActivity(toViewImage)
    }

    //アプリバーにメニューを作成するメソッド
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //インフレーターを使ってメニューを表示させる
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.open_tag_setting -> {
                //タグ管理画面
                val toTagSetting = Intent(this, TagSettingActivity::class.java)
                startActivity(toTagSetting)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}