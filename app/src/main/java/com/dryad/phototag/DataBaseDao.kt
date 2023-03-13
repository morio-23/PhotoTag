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

    /*Paging用クエリ*/
    @Query("SELECT * FROM item_tbl LIMIT :limit OFFSET :offset")
    suspend fun getPagedItem(limit: Int, offset: Int): List<ItemData>

    @Query("SELECT contentUri FROM item_tbl WHERE tag like :string ")
    fun getSearchedItem(string: String): List<String>
    //where in にListを渡したときの返すカラムは一列だけじゃないといけないらしい

    @Query("SELECT displayName FROM item_tbl WHERE contentUri = :contentUri")
    fun returnDisplayName(contentUri: String): String

    @TypeConverters
    @Query("SELECT tag FROM item_tbl WHERE contentUri = :contentUri")
    fun returnTagStatus(contentUri: String): List<String>

    @Query("UPDATE item_tbl SET tag = :tag WHERE contentUri = :contentUri")
    fun updateTag(contentUri: String,tag: List<String>)

    @Query("SELECT tagName FROM tag_tbl")
    fun getAllTagName(): Array<String>?

    @Query("SELECT tagName, tagColor FROM tag_tbl")
    fun getAllTag(): List<TagData>

    @Insert
    fun addTag(tagDatabase: TagDatabase)

}

