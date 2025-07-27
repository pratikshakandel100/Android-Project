package com.pratiksha.rojgarihub.navigation

import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pratiksha.rojgarihub.presentation.auth.login.LoginScreenRoot
import com.pratiksha.rojgarihub.presentation.auth.register.RegisterScreenRoot
import com.pratiksha.rojgarihub.presentation.job.list_job.JobListScreenRoot
import com.pratiksha.rojgarihub.presentation.job.post_job.PostJobScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
): Unit {
    NavHost(
        navController = navController,
        startDestination = Screens.AuthGraph
    ) {
        authGraph(navController)
        jobGraph(navController)
    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<Screens.AuthGraph>(
        startDestination = Screens.LogIn
    ) {
        composable<Screens.Register> {
            RegisterScreenRoot(
                onLoginClick = {
                    navController.navigate(Screens.LogIn) {
                        popUpTo(route = Screens.Register) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState =
                            true  // restoreState for login screen, if with mistakenly navigate
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate(Screens.LogIn)
                },
            )
        }

        composable<Screens.LogIn> {
            LoginScreenRoot(
                onRegisterClick = {
                    navController.navigate(Screens.Register) {
                        popUpTo(Screens.LogIn) {
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulLogin = {
                    navController.navigate(Screens.JobGraph) {
                        popUpTo(Screens.AuthGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }

    }
}

private fun NavGraphBuilder.jobGraph(navController: NavHostController) {
    navigation<Screens.JobGraph>(
        startDestination = Screens.JobList
    ) {
        composable<Screens.JobList> {
            JobListScreenRoot(
                onAddJobClick = {
                    navController.navigate(Screens.AddJob)
                },
                onLogOutClick = {
                    navController.navigate(Screens.AuthGraph) {
                        popUpTo(Screens.JobGraph) {
                            inclusive = true
                        }
                    }
                },
                onEditJobClick = { jobId ->
                    navController.navigate(
                        Screens.AddJob
                    )
                }
            )
        }
        composable<Screens.AddJob> {
            PostJobScreenRoot(
                onSuccessfulPostJob = {
                    navController.navigateUp()
                },
                onCancelClick = {
                    navController.navigateUp()
                }
            )
        }

    }
}