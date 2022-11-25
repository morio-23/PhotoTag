package com.dryad.phototag

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemAdapter(imageUris: MutableList<ItemData>): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val returnContext: ReturnContext? = null
    var imageUris: MutableList<ItemData>? = null

    init {
        this.imageUris = imageUris
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        init {
            textView = view.findViewById(R.id.item_text)
            imageView = view.findViewById(R.id.item_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("onBindViewHolder", "")
        val item = imageUris?.get(position)
        if (item != null) {
            Log.d("URI_adapter", item.contentUri.toString())
            viewHolder.textView.text = item.displayName
            //Glide.with(this.returnContext?.getAppContext()!!).load(item.contentUri).into(viewHolder.imageView)
            viewHolder.imageView.setImageURI(item.contentUri)
        }
    }

    override fun getItemCount() = imageUris?.size ?: 0
}