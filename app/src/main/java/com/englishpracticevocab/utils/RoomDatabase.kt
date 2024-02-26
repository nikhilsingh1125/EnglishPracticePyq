package com.englishpracticevocab.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.englishpracticevocab.daos.BookmarkDataDao
import com.englishpracticevocab.model.BookmarkRoomModel
import com.englishpracticevocab.model.ResultShowDataDao
import com.englishpracticevocab.model.DbResultShowData

@Database(entities = [DbResultShowData::class,BookmarkRoomModel::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultShowDataDao(): ResultShowDataDao
    abstract fun resultBookmarkDao(): BookmarkDataDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}

