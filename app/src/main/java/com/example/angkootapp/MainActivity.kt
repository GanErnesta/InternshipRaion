package com.example.angkootapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.angkootapp.presentation.components.CustomBottomNav
import com.example.angkootapp.presentation.navigation.AppNavGraph
import com.example.angkootapp.presentation.navigation.Screen
import com.example.angkootapp.ui.theme.AngkootAppTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            AngkootAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val noBottomNavRoutes = listOf(
                Screen.Welcome.route,
                Screen.WelcomeSec.route,
                Screen.WelcomeThird.route,
                Screen.WelcomeFourth.route,
                Screen.Login.route,
                Screen.Register.route,
                Screen.ForgotPassword.route,
                Screen.ResetPassword.route,
                Screen.Syarat.route,
                Screen.Akun.route,
                Screen.YourSaldo.route,
                Screen.TopUpSaldo.route,
                Screen.SaldoSuccess.route,
                Screen.PaymentSaldo.route
            )

            if (currentRoute !in noBottomNavRoutes) {
                CustomBottomNav(navController = navController)
            }
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}