package com.example.angkootapp.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.angkootapp.model.viewModel.LoginViewModel
import com.example.angkootapp.model.viewModel.RegisterViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.angkootapp.presentation.auth.LoginScreen
import com.example.angkootapp.presentation.auth.RegisterScreen
import com.example.angkootapp.presentation.homePage.MapsScreen
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
                    navController.navigate(Screen.MapsScreen.route){
                        popUpTo(Screen.Login.route) {inclusive = true}
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
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
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Register.route) { inclusive = true}
                    }
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                viewModel = registerViewModel

            )
        }
        composable(Screen.MapsScreen.route) {
            MapsScreen(

            )
        }
    }
}