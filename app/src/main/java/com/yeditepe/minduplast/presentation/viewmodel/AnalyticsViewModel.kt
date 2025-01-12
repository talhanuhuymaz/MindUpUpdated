package com.yeditepe.minduplast.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeditepe.minduplast.domain.model.MoodEntry
import com.yeditepe.minduplast.domain.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class MoodAnalytics(
    val todayMoods: List<MoodEntry> = emptyList(),
    val weeklyMoodCount: Map<String, Int> = emptyMap(),
    val mostFrequentMood: String = "",
    val totalEntries: Int = 0
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: MoodRepository
) : ViewModel() {
    private val _analyticsState = MutableStateFlow(MoodAnalytics())
    val analyticsState: StateFlow<MoodAnalytics> = _analyticsState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMoods().collect { moods ->
                val today = LocalDateTime.now()
                val todayMoods = moods.filter {
                    it.timestamp.toLocalDate() == today.toLocalDate()
                }

                val weeklyMoodCount = moods
                    .filter {
                        it.timestamp.isAfter(today.minusDays(7))
                    }
                    .groupingBy { it.moodType.title }
                    .eachCount()

                val mostFrequentMood = weeklyMoodCount.maxByOrNull { it.value }?.key ?: ""

                _analyticsState.value = MoodAnalytics(
                    todayMoods = todayMoods,
                    weeklyMoodCount = weeklyMoodCount,
                    mostFrequentMood = mostFrequentMood,
                    totalEntries = moods.size
                )
            }
        }
    }
} 