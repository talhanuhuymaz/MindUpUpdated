package com.cumaliguzel.mindup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cumaliguzel.mindup.domain.model.MoodEntry
import com.cumaliguzel.mindup.domain.model.MoodType
import com.cumaliguzel.mindup.domain.repository.MoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoodScreenState(
    val moods: List<MoodEntry> = emptyList(),
    val selectedMood: MoodType? = null
)

@HiltViewModel
class MoodViewModel @Inject constructor(
    private val repository: MoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoodScreenState())
    val uiState: StateFlow<MoodScreenState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMoods().collect { moods ->
                _uiState.value = _uiState.value.copy(moods = moods)
            }
        }
    }

    fun onEvent(event: MoodEvent) {
        when (event) {
            is MoodEvent.SelectMood -> {
                _uiState.value = _uiState.value.copy(selectedMood = event.moodType)
            }
            is MoodEvent.SaveMood -> {
                viewModelScope.launch {
                    _uiState.value.selectedMood?.let { moodType ->
                        repository.insertMood(
                            MoodEntry(
                                moodType = moodType,
                                note = event.note
                            )
                        )
                    }
                    _uiState.value = _uiState.value.copy(selectedMood = null)
                }
            }
            is MoodEvent.DeleteMood -> {
                viewModelScope.launch {
                    repository.deleteMood(event.mood)
                }
            }
            MoodEvent.DismissDialog -> {
                _uiState.value = _uiState.value.copy(selectedMood = null)
            }
        }
    }
}

sealed class MoodEvent {
    data class SelectMood(val moodType: MoodType) : MoodEvent()
    data class SaveMood(val note: String) : MoodEvent()
    data class DeleteMood(val mood: MoodEntry) : MoodEvent()
    data object DismissDialog : MoodEvent()
} 