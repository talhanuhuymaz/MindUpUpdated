package com.yeditepe.minduplast.presentation.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Timeline
import com.yeditepe.minduplast.domain.model.MoodEntry
import com.yeditepe.minduplast.domain.model.MoodType
import com.yeditepe.minduplast.presentation.viewmodel.AnalyticsViewModel

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val analyticsState by viewModel.analyticsState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Today's Overview Card
        item {
            TodayOverviewCard(analyticsState.todayMoods)
        }

        // Weekly Mood Distribution Card
        item {
            WeeklyDistributionCard(analyticsState.weeklyMoodCount)
        }

        // Most Frequent Mood Card
        item {
            MostFrequentMoodCard(
                mostFrequentMood = analyticsState.mostFrequentMood,
                weeklyMoodCount = analyticsState.weeklyMoodCount,
                totalEntries = analyticsState.totalEntries
            )
        }
    }
}

@Composable
private fun TodayOverviewCard(todayMoods: List<MoodEntry>) {
    val colorPrimaryContainer = MaterialTheme.colorScheme.primaryContainer
    val colorOnPrimaryContainer = MaterialTheme.colorScheme.onPrimaryContainer

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarToday,
                contentDescription = null,
                tint = colorOnPrimaryContainer,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Today's Mood Journey",
                style = MaterialTheme.typography.titleLarge,
                color = colorOnPrimaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))

            MoodGrid(moods = todayMoods)
        }
    }
}

@Composable
private fun MoodGrid(moods: List<MoodEntry>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val groupedMoods = moods.groupBy { it.moodType }
        groupedMoods.forEach { (moodType, moodEntries) ->
            MoodTypeItem(moodType, moodEntries.size)
        }
    }
}

@Composable
private fun MoodTypeItem(moodType: MoodType, count: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = moodType.icon),
            contentDescription = moodType.title,
            tint = Color(moodType.contentColor.value),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun WeeklyDistributionCard(weeklyMoodCount: Map<String, Int>) {
    val colorSecondaryContainer = MaterialTheme.colorScheme.secondaryContainer
    val colorOnSecondaryContainer = MaterialTheme.colorScheme.onSecondaryContainer

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorSecondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Weekly Overview",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorOnSecondaryContainer
                )
                Icon(
                    imageVector = Icons.Default.Timeline,
                    contentDescription = null,
                    tint = colorOnSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            WeeklyMoodList(weeklyMoodCount)
        }
    }
}

@Composable
private fun WeeklyMoodList(weeklyMoodCount: Map<String, Int>) {
    val maxCount = weeklyMoodCount.values.maxOrNull() ?: 1
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        weeklyMoodCount.forEach { (mood, count) ->
            MoodProgressBar(
                mood = mood,
                count = count,
                maxCount = maxCount
            )
        }
    }
}

@Composable
private fun MoodProgressBar(mood: String, count: Int, maxCount: Int) {
    val progress = count.toFloat() / maxCount.toFloat()
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "progress"
    )
    val colorOnSecondaryContainer = MaterialTheme.colorScheme.onSecondaryContainer

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mood,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(100.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRoundRect(
                    color = colorOnSecondaryContainer.copy(alpha = 0.1f),
                    size = size
                )
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .fillMaxHeight()
            ) {
                drawRoundRect(
                    color = colorOnSecondaryContainer,
                    size = size
                )
            }
        }
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun MostFrequentMoodCard(
    mostFrequentMood: String,
    weeklyMoodCount: Map<String, Int>,
    totalEntries: Int
) {
    val colorTertiaryContainer = MaterialTheme.colorScheme.tertiaryContainer
    val colorOnTertiaryContainer = MaterialTheme.colorScheme.onTertiaryContainer

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorTertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = colorOnTertiaryContainer,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your Dominant Mood",
                style = MaterialTheme.typography.titleMedium,
                color = colorOnTertiaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))

            MoodCircularProgress(
                mostFrequentMood = mostFrequentMood,
                count = weeklyMoodCount[mostFrequentMood] ?: 0,
                totalEntries = totalEntries
            )
        }
    }
}

@Composable
private fun MoodCircularProgress(
    mostFrequentMood: String,
    count: Int,
    totalEntries: Int
) {
    val colorOnTertiaryContainer = MaterialTheme.colorScheme.onTertiaryContainer

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        val sweepAngle = if (totalEntries > 0) {
            360f * count.toFloat() / totalEntries.toFloat()
        } else {
            0f
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = colorOnTertiaryContainer.copy(alpha = 0.2f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 12f, cap = StrokeCap.Round)
            )
            drawArc(
                color = colorOnTertiaryContainer,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 12f, cap = StrokeCap.Round)
            )
        }

        MoodCircularText(
            mostFrequentMood = mostFrequentMood,
            percentage = (sweepAngle / 360f * 100).toInt()
        )
    }
}

@Composable
private fun MoodCircularText(mostFrequentMood: String, percentage: Int) {
    val colorOnTertiaryContainer = MaterialTheme.colorScheme.onTertiaryContainer

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mostFrequentMood,
            style = MaterialTheme.typography.titleMedium,
            color = colorOnTertiaryContainer
        )
        Text(
            text = "$percentage%",
            style = MaterialTheme.typography.bodyLarge,
            color = colorOnTertiaryContainer
        )
    }
}
