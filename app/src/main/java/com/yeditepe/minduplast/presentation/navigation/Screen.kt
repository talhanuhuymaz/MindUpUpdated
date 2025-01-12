package com.yeditepe.minduplast.presentation.navigation

sealed class Screen(val route: String) {
    data object Moods : Screen("moods")
    data object Analytics : Screen("analytics")
} 