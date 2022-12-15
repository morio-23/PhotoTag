package com.dryad.phototag

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dryad.phototag.databinding.TagLayoutBinding

class TagAdapter(val tagList: List<TagData>,private val onItemClickListener: TagAdapter.ItemClickListener): RecyclerView.Adapter<TagAdapter.ViewHolder>() {


    class ViewHolder(binding: TagLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        val tagImageView: ImageView = binding.imageTag
        var tagNameText: TextView = binding.textTagName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TagLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("TagAdapter", "TagData:"+tagList[position])
        Log.d("TagAdapter", "position:$position")
        if(tagList[0] == TagData("","") || tagList.isEmpty()) { //初期化のままであればタグが存在しないと出力。全てのタグを消した時も同様になるよう設定すること。
            viewHolder.tagNameText.text = "タグが存在しません"
        }else{
            viewHolder.tagNameText.text = tagList[position].tagName
            if (!tagList[position].tagColor.isNullOrBlank()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    viewHolder.tagImageView.colorFilter = BlendModeColorFilter(
                        Color.parseColor(tagList[position].tagColor),
                        BlendMode.SRC_ATOP
                    )
                } else {
                    viewHolder.tagImageView.setColorFilter(
                        Color.parseColor(tagList[position].tagColor),
                        PorterDuff.Mode.SRC_ATOP
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int = tagList.size

    interface ItemClickListener{
        fun onItemClickListener()
    }

}