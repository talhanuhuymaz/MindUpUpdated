package com.yeditepe.minduplast.data.repository

import com.yeditepe.minduplast.data.local.MoodDao
import com.yeditepe.minduplast.domain.model.MoodEntry
import com.yeditepe.minduplast.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoodRepositoryImpl @Inject constructor(
    private val dao: MoodDao
) : MoodRepository {
    override fun getMoods(): Flow<List<MoodEntry>> {
        return dao.getMoods()
    }

    override suspend fun insertMood(mood: MoodEntry) {
        dao.insertMood(mood)
    }

    override suspend fun deleteMood(mood: MoodEntry) {
        dao.deleteMood(mood)
    }
} 