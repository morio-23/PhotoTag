package com.dryad.phototag

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataBaseDao {
    @Nullable
    @Insert
    suspend fun insertAll(ItemDatabase: MutableList<ItemDatabase>)

    @Query("SELECT * FROM item_tbl")
    fun getAll(): List<ItemDatabase>

    @Query("SELECT displayName FROM item_tbl WHERE URI = :uri")
    fun returnDisplayName(uri: String): String

}