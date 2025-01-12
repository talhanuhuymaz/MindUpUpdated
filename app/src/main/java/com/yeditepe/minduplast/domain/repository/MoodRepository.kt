package com.yeditepe.minduplast.domain.repository

import com.yeditepe.minduplast.domain.model.MoodEntry
import kotlinx.coroutines.flow.Flow

interface MoodRepository {
    fun getMoods(): Flow<List<MoodEntry>>
    suspend fun insertMood(mood: MoodEntry)
    suspend fun deleteMood(mood: MoodEntry)
} 