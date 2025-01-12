package com.yeditepe.minduplast.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yeditepe.minduplast.domain.model.MoodEntry

@Database(
    entities = [MoodEntry::class], 
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MoodDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao

    companion object {
        const val DATABASE_NAME = "mood_db"
    }
} 