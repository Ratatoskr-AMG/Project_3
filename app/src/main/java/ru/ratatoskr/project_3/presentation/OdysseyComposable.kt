package ru.ratatoskr.project_3.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.core.LaunchFlag
import ru.alexgladkov.odyssey.core.RootController
import ru.alexgladkov.odyssey.core.animations.defaultPresentationAnimation
import ru.ratatoskr.project_3.presentation.activity.DetailParams
import ru.ratatoskr.project_3.presentation.activity.DialogParams
import ru.ratatoskr.project_3.presentation.activity.NavigationTree



class OdysseyComposable {

    @Composable
    fun DialogScreen(rootController: RootController, params: DialogParams) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Modal Dialog Screen with ${params.value}", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                    color = Color.Black
                )
            }

            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
                Button(onClick = {
                    rootController.popBackStack()
                }) {
                    Text("Close Dialog")
                }
            }
        }
    }

    @Composable
    fun ProfileScreen() {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Profile Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )
        }
    }

    @Composable
    fun FavoriteScreen(rootController: RootController) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Favorite Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )

            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
                Button(onClick = {
                    rootController.parentRootController?.parentRootController?.launch(
                        NavigationTree.Root.Dialog.name,
                        params = DialogParams("Favorite"),
                        animationType = defaultPresentationAnimation()
                    )
                }) {
                    Text("Open Modal Dialog")
                }
            }
        }
    }

    @Composable
    fun DetailScreen(rootController: RootController, param: DetailParams) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Detail Screen with $param", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )

            Row(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(16.dp)) {
                Button(onClick = {
                    rootController.popBackStack()
                }) {
                    Text("Go back")
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

    @Composable
    fun OnboardingScreen(rootController: RootController) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Onboarding Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )

            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
                Button(onClick = {
                    rootController.push(NavigationTree.Auth.Login.toString(), params = "Onboarding")
                }) {
                    Text("Go to Login")
                }
            }
        }
    }

    @Composable
    fun SplashScreen(rootController: RootController) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Splash Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )

            Column(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
                Row {
                    Button(onClick = {
                        rootController.present(
                            screen = NavigationTree.Root.Auth.toString(),
                            params = "Splash",
                            launchFlag = LaunchFlag.SingleNewTask
                        )
                    }) {
                        Text("Auth With No History")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = {
                        rootController.present(
                            screen = NavigationTree.Root.Auth.toString(),
                            params = "Splash",
                        )
                    }) {
                        Text("Auth With History")
                    }
                }

            }

        }
    }

    @Composable
    fun LoginScreen(rootController: RootController, source: String? = null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Login Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )

            Row(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(16.dp)) {
                Button(onClick = {
                    rootController.popBackStack()
                }) {
                    Text("Go back")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    rootController.push(NavigationTree.Auth.TwoFactor.toString(), "Phone")
                }) {
                    Text("Go to Confirm Code")
                }
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun LoginCodeScreen(rootController: RootController, type: String?) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = "Login Code Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                color = Color.Black
            )

            Row(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(16.dp)) {
                Button(onClick = {
                    rootController.popBackStack()
                }) {
                    Text("Go back")
                }

                Spacer(modifier = Modifier.weight(1f))

                Button(onClick = {
                    rootController.parentRootController?.launch(
                        screen = NavigationTree.Root.Main.toString(),
                        animationType = defaultPresentationAnimation()
                    )
                }) {
                    Text("Go to Main")
                }
            }
        }
    }

    @Composable
    fun FeedScreen(rootController: RootController) {
        val paramState = remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Feed Screen", fontWeight = FontWeight.Medium, fontSize = 28.sp,
                    color = Color.Black
                )

                TextField(
                    modifier = Modifier.padding(top = 8.dp),
                    value = paramState.value,
                    onValueChange = {
                        paramState.value = it
                    },
                    placeholder = {
                        Text("Enter param to test navigation")
                    }
                )
            }

            Row(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) {
                Button(onClick = {
                    rootController.push(NavigationTree.Main.Detail.toString(), DetailParams(paramState.value))
                }) {
                    Text("Go to Detail")
                }
            }
        }
    }
}

