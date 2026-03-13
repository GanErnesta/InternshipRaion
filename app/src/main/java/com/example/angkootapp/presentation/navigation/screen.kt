package com.example.angkootapp.presentation.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object WelcomeSec : Screen("welcomesec")
    object WelcomeThird : Screen("welcomethird")
    object WelcomeFourth : Screen("welcomefourth")

    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot-password")
    object ResetPassword: Screen("reset-password")

    object MapsScreen : Screen("home")
    object Profile : Screen("profile")
    object Akun : Screen("akun")
    object Booklet : Screen("booklet")
    object Activity : Screen("activity")
    object Syarat : Screen("syarat")
    object TopUpSaldo : Screen("topup")
    object YourSaldo : Screen("your-saldo")
    object PaymentSaldo : Screen("payment-saldo")
    object SaldoSuccess : Screen("success-saldo")


}