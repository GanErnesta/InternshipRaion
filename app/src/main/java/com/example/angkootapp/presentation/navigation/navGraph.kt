package com.example.angkootapp.presentation.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.angkootapp.model.viewModel.LoginViewModel
import com.example.angkootapp.model.viewModel.RegisterViewModel
import com.example.angkootapp.presentation.activityScreen.ActivityScreen
import com.example.angkootapp.presentation.auth.*
import com.example.angkootapp.presentation.bookletPage.BookletScreen
import com.example.angkootapp.presentation.components.WelcomeCarouselScreen
import com.example.angkootapp.presentation.homePage.MapPage
import com.example.angkootapp.presentation.homePage.TrackingScreen
import com.example.angkootapp.presentation.profil.AkunScreen
import com.example.angkootapp.presentation.profil.SyaratScreen
import com.example.angkootapp.presentation.profile.ProfilScreen
import com.example.angkootapp.presentation.topUpScreen.*
import com.example.angkootapp.presentation.welcomePage.WelcomeScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var globalTripProgress by remember { mutableFloatStateOf(0f) }
    var currentStepIndex by remember { mutableIntStateOf(0) }
    var isHeadingToDestination by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onNextClick = { navController.navigate(Screen.WelcomeSec.route) }
            )
        }

        composable(Screen.WelcomeSec.route) {
            WelcomeCarouselScreen(
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                onBackClick = { navController.navigate(Screen.Register.route) },
                onLoginSucces = {
                    navController.navigate(Screen.MapsScreen.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate(Screen.Register.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                viewModel = loginViewModel
            )
        }

        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            RegisterScreen(
                onBackClick = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onLoginClick = { navController.navigate(Screen.Login.route) },
                viewModel = registerViewModel
            )
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToReset = { code ->
                    navController.navigate(Screen.ResetPassword.route + "/$code")
                })
        }

        composable(Screen.ResetPassword.route + "/{oobCode}") { backStackEntry ->
            val oobCode = backStackEntry.arguments?.getString("oobCode") ?: ""
            ResetPasswordScreen(
                oobCode = oobCode,
                onBackClick = { navController.popBackStack() },
                onResetSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.MapsScreen.route) {
            MapPage(navController = navController)
        }

        composable(Screen.Tracking.route) {
            TrackingScreen(
                navController = navController,
                savedStepIndex = currentStepIndex,
                savedIsHeadingToKos = isHeadingToDestination,
                onProgressUpdate = { progress, step, isAntar ->
                    globalTripProgress = progress
                    currentStepIndex = step
                    isHeadingToDestination = isAntar
                }
            )
        }

        composable(Screen.Activity.route) {
            ActivityScreen(
                navController = navController,
                currentProgress = globalTripProgress
            )
        }

        composable(Screen.Booklet.route) { BookletScreen() }
        composable(Screen.Profile.route) { ProfilScreen(navController = navController) }
        composable(Screen.Akun.route) { AkunScreen(navController = navController) }
        composable(Screen.Syarat.route) { SyaratScreen(navController = navController) }

        composable(Screen.TopUpSaldo.route) {
            TopUpSaldoScreen(
                onBackClick = { navController.popBackStack() },
                onConfirmClick = {}
            )
        }

        composable(Screen.YourSaldo.route) {
            YourSaldoScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                onTopUpClick = { navController.navigate(Screen.TopUpSaldo.route) }
            )
        }

        composable(Screen.OrderSuccess.route) {
            OrderSuccessScreen(navController = navController)
        }
    }
}