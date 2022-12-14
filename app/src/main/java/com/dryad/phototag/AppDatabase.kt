package com.dryad.phototag

import android.content.Context
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Database(entities = [ItemDatabase::class, TagDatabase::class], version = 1, exportSchema = false)//最終アップデート：2022/11/29
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun DataBaseDao(): DataBaseDao

    companion object {
        @Volatile
        private var INSTANCE_main: AppDatabase? = null

        fun getDatabase_item(
            context: Context
        ): AppDatabase {
            return INSTANCE_main ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "ItemDatabase"
                )
                    .apply {
                        //migration
                    }
                    .build()
                INSTANCE_main = instance

                instance
            }
        }

        fun getDatabase_tag(
            context: Context
        ): AppDatabase {
            return INSTANCE_main ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "TagDatabase"
                )
                    .apply {
                        //migration
                    }
                    .build()
                INSTANCE_main = instance

                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
