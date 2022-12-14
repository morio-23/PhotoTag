package com.dryad.phototag

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dryad.phototag.databinding.ActivityViewImageBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        binding = ActivityViewImageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        val contentUri = intent.getStringExtra("uri")

        binding.imageView.setImageURI(Uri.parse(contentUri))

        GlobalScope.launch {
            if(contentUri!=null)
            binding.toolbar.title = AppDatabase.getDatabase_item(applicationContext).DataBaseDao().returnDisplayName(contentUri)
        }

    }
}