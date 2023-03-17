package com.dryad.phototag

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dryad.phototag.databinding.ItemLayoutBinding
import android.provider.MediaStore



class ItemAdapter(Context: Context, /*imageUris: MutableList<String>,*/ private val onItemClickListener: ItemClickListener): PagingDataAdapter<ItemData, ItemAdapter.ViewHolder>(
    DIFF_CALLBACK) {

    /*
    private var contentUris: MutableList<String>? = null

    init {
        this.contentUris = imageUris
    }*/

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemData>() {
            override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean =
                oldItem == newItem
        }
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
        /*val item = contentUris?.get(position)
        if (item != null) {
            Log.d("URI_adapter", item)
            viewHolder.imageView.setImageURI(Uri.parse(item))
            viewHolder.imageView.setOnClickListener{
                onItemClickListener.onItemClickListener(item)
            }
        }*/
        val item = getItem(position)
        if(item != null) {
            viewHolder.bindingAdapter.apply {
                //viewHolder.imageView.setImageURI(Uri.parse(item.contentUri.toString()))
                viewHolder.imageView.setImageBitmap(Context.contentResolver.loadThumbnail(item.contentUri))
                viewHolder.imageView.setOnClickListener{
                    onItemClickListener.onItemClickListener(item.contentUri.toString())
                }
            }
        }
    }

    //override fun getItemCount() = contentUris?.size ?: 0

    interface ItemClickListener{
        fun onItemClickListener(uri: String)
    }

}