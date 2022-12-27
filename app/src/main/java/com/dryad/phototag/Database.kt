package com.dryad.phototag

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item_tbl")
data class ItemDatabase(
    @PrimaryKey
    @ColumnInfo(name = "URI") val uri: String,
    @ColumnInfo(name = "displayName") val displayName: String,
    @ColumnInfo(name = "tag") val tag: ArrayList<String>?
)

@Entity(tableName = "Tag_tbl")
data class TagDatabase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "tagName") val tagName: String,
    @ColumnInfo(name = "tagColor") val tagColor: String?
)
