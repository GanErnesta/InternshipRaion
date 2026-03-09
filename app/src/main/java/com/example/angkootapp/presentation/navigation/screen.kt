package com.example.angkootapp.presentation.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object MapsScreen : Screen("home")
    object Profile : Screen("profile")
    object Activity : Screen("activity")
    object Payment : Screen("payment")
}