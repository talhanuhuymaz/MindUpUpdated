package com.yeditepe.minduplast.domain.model

import androidx.compose.ui.graphics.Color
import com.yeditepe.minduplast.R
import com.yeditepe.minduplast.mindup.R

enum class MoodType(
    val icon: Int,
    val title: String,
    val backgroundColor: Color,
    val contentColor: Color
) {
    HAPPY(
        icon = R.drawable.ic_mood_happy,
        title = "Happy",
        backgroundColor = Color(0xFFFFE0B2), // Sıcak sarı/turuncu
        contentColor = Color(0xFFFF9800)
    ),
    EXCITED(
        icon = R.drawable.ic_mood_excited,
        title = "Excited",
        backgroundColor = Color(0xFFFFCDD2), // Açık pembe
        contentColor = Color(0xFFE91E63)
    ),
    CALM(
        icon = R.drawable.ic_mood_calm,
        title = "Calm",
        backgroundColor = Color(0xFFB2DFDB), // Açık yeşil
        contentColor = Color(0xFF009688)
    ),
    TIRED(
        icon = R.drawable.ic_mood_tired,
        title = "Tired",
        backgroundColor = Color(0xFFD1C4E9), // Açık mor
        contentColor = Color(0xFF673AB7)
    ),
    SAD(
        icon = R.drawable.ic_mood_sad,
        title = "Sad",
        backgroundColor = Color(0xFFBBDEFB), // Açık mavi
        contentColor = Color(0xFF2196F3)
    ),
    ANXIOUS(
        icon = R.drawable.ic_mood_anxious,
        title = "Anxious",
        backgroundColor = Color(0xFFFFF3E0), // Açık turuncu
        contentColor = Color(0xFFFF5722)
    ),
    LOVED(
        icon = R.drawable.ic_mood_loved,
        title = "Loved",
        backgroundColor = Color(0xFFF8BBD0), // Açık kırmızı
        contentColor = Color(0xFFE91E63)
    ),
    CONFUSED(
        icon = R.drawable.ic_mood_confused,
        title = "Confused",
        backgroundColor = Color(0xFFE1BEE7), // Açık mor
        contentColor = Color(0xFF9C27B0)
    ),
    NEUTRAL(
        icon = R.drawable.ic_mood_neutral,
        title = "Neutral",
        backgroundColor = Color(0xFFCFD8DC), // Açık gri
        contentColor = Color(0xFF607D8B)
    )
} 