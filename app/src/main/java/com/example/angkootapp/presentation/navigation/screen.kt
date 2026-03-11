package com.example.angkootapp.presentation.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object WelcomeSec : Screen("welcomesec")
    object WelcomeThird : Screen("welcomethird")
    object WelcomeFourth : Screen("welcomefourth")

    object Login : Screen("login")
    object Register : Screen("register")
    object MapsScreen : Screen("home")
    object Profile : Screen("profile")
    object Activity : Screen("activity")
    object Booklet : Screen("booklet")
}