package com.plcoding.nestednavigationgraphsguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.nestednavigationgraphsguide.ui.theme.NestedNavigationGraphsGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NestedNavigationGraphsGuideTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "foo"
                ) {
                    navigation(
                        route = "foo",
                        startDestination = "screen_1",
                    ) {
                        composable("screen_1") {
                            Column {
                                Text(text = "SCREEN 1")

                                Button(
                                    onClick = {
                                        navController.navigate("bar?$userIdArg=9999")
                                    }) {
                                    Text(text = "GO TO BAR GRAPH (SCREEN 2)")
                                }

                                Button(
                                    onClick = {
                                        navController.navigateToEmployeePin(path = "from_screen_1")
                                    }) {
                                    Text(text = "GO TO EMPLOYEE PIN FROM FOO GRAPH")
                                }
                            }
                        }
                        composable("register") {
                            Text(text = "REGISTER")
                        }
                        composable("forgot_password") {
                            Text(text = "FORGOT PASSWORD")
                        }

                        composable(
                            route = EMPLOYEE_PIN_ROUTE,
                            arguments = listOf(
                                navArgument(pathArg) {
                                    type = NavType.StringType
                                },
                            )
                        ) {
                            // we can't load this screen, it's expecting a userIdArg which is
                            // completely irrelevant to this composable
                            Text(text = "EMPLOYEE PIN ROUTE, WITHIN FOO GRAPH")
                        }

                    }
                    navigation(
                        route = "bar?$userIdArg={$userIdArg}",
                        startDestination = "screen_2",
                        arguments = listOf(
                            navArgument(userIdArg) {
                                type = NavType.IntType
                            },
                        )
                    ) {
                        composable("screen_2") {
                            Text(text = "SCREEN 2")

                            Button(
                                onClick = {
                                    navController.navigateToEmployeePin(path = "from_screen_2")
                                }) {
                                Text(text = "GO TO EMPLOYEE PIN FROM BAR GRAPH")
                            }

                        }
                        composable(
                            route = EMPLOYEE_PIN_ROUTE,
                            arguments = listOf(
                                navArgument(pathArg) {
                                    type = NavType.StringType
                                },
                            )
                        ) {
                            Text(text = "EMPLOYEE PIN SCREEN (WITHIN BAR GRAPH)")
                        }
                    }
                    composable("about") {}
                    composable("another_screen") {}
                }
            }
        }
    }
}

const val userIdArg = "userId"
const val pathArg = "path"
const val EMPLOYEE_PIN_ROUTE = "employee_pin_route/{$pathArg}"
fun NavController.navigateToEmployeePin(
    navOptions: NavOptions? = null,
    path: String,
) {

    this.navigate(
        EMPLOYEE_PIN_ROUTE.replace("{$pathArg}", path),
        navOptions
    )
}