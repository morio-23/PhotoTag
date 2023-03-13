package com.dryad.phototag

import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "Item_tbl")
data class ItemDatabase(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "contentUri") val uri: String,
    @ColumnInfo(name = "displayName") val displayName: String,
    @ColumnInfo(name = "tag") val tag: List<String>
)

@Entity(tableName = "Tag_tbl")
data class TagDatabase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "tagName") val tagName: String,
    @ColumnInfo(name = "tagColor") val tagColor: String?
)


