package com.example.angkootapp.presentation.navigation

sealed class Screen(val route: String) {
    // Welcome / Onboarding
    object Welcome : Screen("welcome")
    object WelcomeSec : Screen("welcomesec")
    object WelcomeThird : Screen("welcomethird")
    object WelcomeFourth : Screen("welcomefourth")

    // Auth
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password") // Gunakan underscore agar konsisten
    object ResetPassword : Screen("reset_password")

    // Main Features
    object MapsScreen : Screen("home")
    object Tracking : Screen("tracking")
    object Activity : Screen("activity")
    object Booklet : Screen("booklet")

    // Profile & Info
    object Profile : Screen("profile")
    object Akun : Screen("akun")
    object Syarat : Screen("syarat")

    // Payment & Saldo
    object TopUpSaldo : Screen("topup_saldo")
    object YourSaldo : Screen("your_saldo")
    object PaymentSaldo : Screen("payment_saldo")
    object SaldoSuccess : Screen("success_saldo")
    object OrderSuccess : Screen("order_success")
}