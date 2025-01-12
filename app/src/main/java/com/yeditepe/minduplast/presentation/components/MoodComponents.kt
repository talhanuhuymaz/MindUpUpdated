package com.yeditepe.minduplast.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yeditepe.minduplast.domain.model.MoodType

@Composable
fun MoodIcon(
    moodType: MoodType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = moodType.icon),
        contentDescription = moodType.title,
        modifier = modifier
            .size(48.dp)
            .clickable(onClick = onClick),
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun MoodDialog(
    moodType: MoodType,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Note") },
        text = {
            Column {
                Text(
                    text = "How are you feeling ${moodType.title.lowercase()}?",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Note") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(note) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}