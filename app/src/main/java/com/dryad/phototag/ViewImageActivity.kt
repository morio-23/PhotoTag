package com.dryad.phototag

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dryad.phototag.databinding.ActivityViewImageBinding

class ViewImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        binding = ActivityViewImageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val contentUri = intent.getStringExtra("uri")

        binding.imageView.setImageURI(Uri.parse(contentUri))
    }
}