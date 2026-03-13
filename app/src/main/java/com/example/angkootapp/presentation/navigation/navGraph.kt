package com.example.angkootapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.angkootapp.model.viewModel.LoginViewModel
import com.example.angkootapp.model.viewModel.RegisterViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.angkootapp.presentation.activityScreen.ActivityScreen
import com.example.angkootapp.presentation.auth.ForgotPasswordScreen
import com.example.angkootapp.presentation.auth.LoginScreen
import com.example.angkootapp.presentation.auth.RegisterScreen
import com.example.angkootapp.presentation.auth.ResetPasswordScreen
import com.example.angkootapp.presentation.bookletPage.BookletScreen
import com.example.angkootapp.presentation.components.WelcomeCarouselScreen
import com.example.angkootapp.presentation.homePage.MapPage
import com.example.angkootapp.presentation.profil.AkunScreen
import com.example.angkootapp.presentation.profil.SyaratScreen
import com.example.angkootapp.presentation.profile.ProfilScreen
import com.example.angkootapp.presentation.topUpScreen.TopUpSaldoScreen
//import com.example.angkootapp.presentation.topUpScreen.TopUpSaldoScreen
import com.example.angkootapp.presentation.topUpScreen.YourSaldoScreen
import com.example.angkootapp.presentation.welcomePage.WelcomeScreen


@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onNextClick = {
                    navController.navigate(Screen.WelcomeSec.route)
                }
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
                onBackClick = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSucces = {
                    navController.navigate(Screen.MapsScreen.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) },
                viewModel = loginViewModel
            )
        }
        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel = viewModel()
            RegisterScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                viewModel = registerViewModel
            )
        }
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToReset = { codeFromUser ->
                    navController.navigate(Screen.ResetPassword.route + "/$codeFromUser")
                })
        }
        composable(Screen.ResetPassword.route + "/{oobCode}") { backStackEntry ->
            val oobCode = backStackEntry.arguments?.getString("oobCode") ?: ""

            ResetPasswordScreen(
                oobCode = oobCode,
                onBackClick = {
                    navController.popBackStack()
                },
                onResetSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.MapsScreen.route) {
            MapPage(
                navController = navController
            )
        }
        composable(Screen.Activity.route) {
            ActivityScreen(
                navController = navController
            )
        }
        composable(Screen.Booklet.route) {
            BookletScreen(
            )
        }
        composable(Screen.Profile.route) {
            ProfilScreen(
                navController = navController
            )
        }
        composable(Screen.Akun.route) {
            AkunScreen(
                navController = navController
            )
        }
        composable(Screen.Syarat.route) {
            SyaratScreen(
                navController = navController
            )
        }
        composable(Screen.TopUpSaldo.route) {
            TopUpSaldoScreen(
                onBackClick = {navController.popBackStack()},
                onConfirmClick = {}
            )
        }
        composable(Screen.YourSaldo.route) {
            YourSaldoScreen(
                navController = navController,
                onBackClick = {
                    navController.popBackStack()
                },
                onTopUpClick = {navController.navigate("topup")}
            )
        }
        composable(Screen.Syarat.route) {
            SyaratScreen(
                navController = navController
            )
        }
    }
}