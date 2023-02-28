package com.dryad.phototag

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dryad.phototag.databinding.ItemLayoutBinding


class ItemAdapter(Context: Context, imageUris: MutableList<String>, private val onItemClickListener: ItemClickListener): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private var contentUris: MutableList<String>? = null

    init {
        this.contentUris = imageUris
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
        val item = contentUris?.get(position)
        if (item != null) {
            Log.d("URI_adapter", item)
            viewHolder.imageView.setImageURI(Uri.parse(item))
            viewHolder.imageView.setOnClickListener{
                onItemClickListener.onItemClickListener(item)
            }
        }
    }

    override fun getItemCount() = contentUris?.size ?: 0

    interface ItemClickListener{
        fun onItemClickListener(uri: String)
    }

}