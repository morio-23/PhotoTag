package com.dryad.phototag

import android.Manifest
import android.content.ContentUris
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.core.content.PermissionChecker
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val imageUris = mutableListOf<ItemData>()

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
        setContentView(R.layout.activity_main)

        //RecyclerViewの取得
        val recyclerView = findViewById<RecyclerView>(R.id.ViewList)

        loadImages()

        Log.d("size", imageUris.size.toString())

        //Adapterの設定
        val adapter = ItemAdapter(imageUris)
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

                //Log.d("URI", contentUri.toString())

                //dataApp?.setData(ItemData(displayName, contentUri))
                imageUris.add(ItemData(displayName,contentUri))
            }
        }

    }


}