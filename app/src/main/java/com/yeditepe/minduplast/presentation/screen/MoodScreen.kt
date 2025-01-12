package com.yeditepe.minduplast.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yeditepe.minduplast.domain.model.MoodEntry
import com.yeditepe.minduplast.domain.model.MoodType
import com.yeditepe.minduplast.presentation.components.MoodCard
import com.yeditepe.minduplast.presentation.components.MoodDialog
import com.yeditepe.minduplast.presentation.components.MoodDetailBottomSheet
import com.yeditepe.minduplast.presentation.components.MoodIcon
import com.yeditepe.minduplast.presentation.components.MoodSelector
import com.yeditepe.minduplast.presentation.viewmodel.MoodEvent
import com.yeditepe.minduplast.presentation.viewmodel.MoodViewModel

@Composable
fun MoodScreen(
    viewModel: MoodViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedMoodEntry: MoodEntry? by remember { mutableStateOf(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var moodToDelete: MoodEntry? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome, How are you today?",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        MoodSelector(
            selectedMood = uiState.selectedMood,
            onMoodSelected = { moodType ->
                viewModel.onEvent(MoodEvent.SelectMood(moodType))
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "History",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.moods) { mood ->
                MoodCard(
                    moodEntry = mood,
                    onDelete = { 
                        moodToDelete = mood
                        showDeleteDialog = true
                    },
                    onClick = { selectedMoodEntry = mood }
                )
            }
        }

        // Dialogs
        if (uiState.selectedMood != null) {
            MoodDialog(
                moodType = uiState.selectedMood!!,
                onDismiss = { viewModel.onEvent(MoodEvent.DismissDialog) },
                onSave = { note -> viewModel.onEvent(MoodEvent.SaveMood(note)) }
            )
        }

        if (selectedMoodEntry != null) {
            MoodDetailBottomSheet(
                moodEntry = selectedMoodEntry!!,
                onDismiss = { selectedMoodEntry = null },
                onDelete = {
                    moodToDelete = selectedMoodEntry
                    showDeleteDialog = true
                    selectedMoodEntry = null
                }
            )
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { 
                    showDeleteDialog = false 
                    moodToDelete = null
                },
                title = {
                    Text(
                        text = "Delete Mood Entry",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to delete this mood entry? This action cannot be undone.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            moodToDelete?.let { mood ->
                                viewModel.onEvent(MoodEvent.DeleteMood(mood))
                            }
                            showDeleteDialog = false
                            moodToDelete = null
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Delete")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { 
                            showDeleteDialog = false
                            moodToDelete = null
                        }
                    ) {
                        Text("Cancel")
                    }
                },
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                textContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(28.dp)
            )
        }
    }
}