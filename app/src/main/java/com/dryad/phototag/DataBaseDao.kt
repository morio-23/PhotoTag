package com.dryad.phototag

import androidx.annotation.Nullable
import androidx.room.*
import androidx.room.TypeConverters
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken


@Dao
interface DataBaseDao {
    @Nullable
    @TypeConverters
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(ItemDatabase: MutableList<ItemDatabase>)

    @Query("SELECT * FROM item_tbl")
    fun getAllItem(): List<ItemDatabase>

    @Query("SELECT displayName FROM item_tbl WHERE URI = :uri")
    fun returnDisplayName(uri: String): String

    @TypeConverters
    @Query("SELECT tag FROM item_tbl WHERE URI = :uri")
    fun returnTagStatus(uri: String): List<String>

    @Query("UPDATE item_tbl SET tag = :tag WHERE URI = :uri")
    fun updateTag(uri: String,tag: List<String>)

    @Query("SELECT tagName FROM tag_tbl")
    fun getAllTagName(): Array<String>?

    @Query("SELECT tagName, tagColor FROM tag_tbl")
    fun getAllTag(): List<TagData>

    @Insert
    fun addTag(tagDatabase: TagDatabase)

}

