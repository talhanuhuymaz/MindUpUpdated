package com.yeditepe.minduplast.data.local

import androidx.room.*

import com.yeditepe.minduplast.domain.model.MoodEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Query("SELECT * FROM mood_entries ORDER BY timestamp DESC")
    fun getMoods(): Flow<List<MoodEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: MoodEntry)

    @Delete
    suspend fun deleteMood(mood: MoodEntry)
} 