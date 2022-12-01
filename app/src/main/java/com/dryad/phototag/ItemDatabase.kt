package com.dryad.phototag

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Item_tbl")
data class ItemDatabase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "displayName") val displayName: String,
    @ColumnInfo(name = "URI") val uri: String?,
    @ColumnInfo(name = "tag") val tag: ArrayList<String>?
)
