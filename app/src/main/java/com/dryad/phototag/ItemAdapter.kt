package com.dryad.phototag

import android.app.Application
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
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Size
import androidx.core.net.toUri
import java.net.URI


class ItemAdapter(context: Context, /*imageUris: MutableList<String>,*/ private val onItemClickListener: ItemClickListener): PagingDataAdapter<ItemData, ItemAdapter.ViewHolder>(
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

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)

        context = parent.context
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
            val thumbnail: Bitmap =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    context.contentResolver.loadThumbnail(
                        item.contentUri.toUri(), Size(200, 200), null)
                } else {
                    //API29以下の挙動のためDEPRECATEDを無視
                    //そもそもよくわからないのでバグ注意
                    MediaStore.Images.Thumbnails.getThumbnail(context.contentResolver, item.contentUri.toUri().lastPathSegment!!.toLong() ,MediaStore.Images.Thumbnails.MICRO_KIND,null )
                }
            viewHolder.bindingAdapter.apply {
                //viewHolder.imageView.setImageURI(Uri.parse(item.contentUri.toString()))
                viewHolder.imageView.setImageBitmap(thumbnail)
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