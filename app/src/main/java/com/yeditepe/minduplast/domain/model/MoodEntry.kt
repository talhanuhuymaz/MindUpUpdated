package com.yeditepe.minduplast.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "mood_entries")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val moodType: MoodType,
    val note: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
) 