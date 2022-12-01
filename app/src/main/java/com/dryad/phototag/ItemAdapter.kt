package com.dryad.phototag

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dryad.phototag.databinding.ItemLayoutBinding


class ItemAdapter(Context: Context, imageUris: MutableList<ItemDeta_register>, private val onItemClickListener: ItemClickListener): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var imageUris: MutableList<ItemDeta_register>? = null

    init {
        this.imageUris = imageUris
    }

    class ViewHolder(binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.itemImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("onBindViewHolder", "")
        val item = imageUris?.get(position)
        if (item != null) {
            Log.d("URI_adapter", item.contentUri)
            viewHolder.imageView.setImageURI(Uri.parse(item.contentUri))
            viewHolder.imageView.setOnClickListener{
                onItemClickListener.onItemClickListener(item.contentUri)
            }
        }
    }

    override fun getItemCount() = imageUris?.size ?: 0

    interface ItemClickListener{
        fun onItemClickListener(uri: String)
    }

}