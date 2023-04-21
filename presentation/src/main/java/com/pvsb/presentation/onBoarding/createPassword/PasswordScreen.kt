package com.pvsb.presentation.onBoarding.createPassword

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.R
import com.pvsb.presentation.mainBottomNav.MainActivity
import com.pvsb.presentation.onBoarding.OnBoardingViewModel
import com.pvsb.presentation.onBoarding.onBoarding.OnBoardingScreenState
import com.pvsb.presentation.onBoarding.onBoarding.OnBoardingScreens
import com.pvsb.presentation.ui.messageTextStyle
import com.pvsb.presentation.ui.theme.AppColors.background
import com.pvsb.presentation.ui.theme.AppColors.gray
import com.pvsb.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.presentation.ui.theme.AppColors.red
import com.pvsb.presentation.ui.theme.AppColors.secondary
import com.pvsb.presentation.ui.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeErrorCard

@Composable
fun PasswordScreenContainer(
    navController: NavController,
    screenType: PasswordScreenType = PasswordScreenType.RepeatPassword(""),
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    var isTextFieldFocused by remember { mutableStateOf(false) }

    if (state.value.isAuthenticated) {
        navigateToMainScreen(navController.context)
    }

    PasswordScreen(
        state = state.value,
        navController = navController,
        screenType = screenType,
        isTextFieldFocused = isTextFieldFocused,
        onErrorVisibilityChanged = {
            if (!it) {
                viewModel.dismissError()
            }
        },
        onTextFieldFocusChanged = { isTextFieldFocused = it },
        onPasswordFilled = { password ->

            when (screenType) {
                PasswordScreenType.CreatePassword -> {
                    navigateToRepeatPassword(navController, password)
                }
                is PasswordScreenType.RepeatPassword -> {
                    if (password == screenType.createdPassword) {
                        viewModel.registerNewPassword(password)
                        navigateToEnterPassword(navController)
                    } else {
                        viewModel.setError(
                            TypedMessage.Reference(R.string.password_incorrect_label)
                        )
                    }
                }
                PasswordScreenType.EnterPassword -> {
                    viewModel.login(password)
                }
            }
        }
    )
}

@Composable
private fun PasswordScreen(
    state: OnBoardingScreenState = OnBoardingScreenState(),
    navController: NavController = rememberNavController(),
    screenType: PasswordScreenType = PasswordScreenType.CreatePassword,
    isTextFieldFocused: Boolean = false,
    onErrorVisibilityChanged: (Boolean) -> Unit = {},
    onTextFieldFocusChanged: (Boolean) -> Unit = {},
    onPasswordFilled: (String) -> Unit = {}
) {

    val isErrorVisible = state.error != null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        contentAlignment = Alignment.TopStart
    ) {

        if (screenType is PasswordScreenType.RepeatPassword) {
            BackButton {
                navController.popBackStack()
            }
        }

        ComposeErrorCard(
            isErrorVisible = isErrorVisible,
            modifier = Modifier.padding(horizontal = 10.dp),
            error = state.error
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(65.dp))

            Text(
                text = stringResource(id = screenType.title), style = titleTextStyle
            )

            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(id = screenType.message),
                style = messageTextStyle
            )

            Spacer(modifier = Modifier.height(44.dp))

            ComposeTextField(
                isErrorVisible = isErrorVisible,
                isTextFieldFocused = isTextFieldFocused,
                onErrorChanged = onErrorVisibilityChanged,
                onTextFieldFocusChanged = onTextFieldFocusChanged,
                onPasswordFilled = onPasswordFilled
            )
        }
    }
}

@Composable
private fun ComposeTextField(
    isErrorVisible: Boolean,
    isTextFieldFocused: Boolean = false,
    onErrorChanged: (Boolean) -> Unit,
    onTextFieldFocusChanged: (Boolean) -> Unit = {},
    onPasswordFilled: (String) -> Unit = {}
) {

    var password by remember { mutableStateOf("") }

    val maxChars = 4

    val borderColor = when {
        isErrorVisible -> red
        isTextFieldFocused -> lightBlue
        else -> Color.Transparent
    }

    BasicTextField(
        value = password, onValueChange = {
            password = it.take(maxChars)
            if (password.length == maxChars) {
                onPasswordFilled(password)
            } else {
                onErrorChanged(false)
            }
        }, decorationBox = {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(40.dp),
            border = BorderStroke(1.dp, borderColor)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(secondary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    30.dp,
                    Alignment.Horizontal { _, space, _ ->
                        space / 2
                    }
                )
            ) {
                repeat(maxChars) {
                    ComposePasswordPointer(password.length - 1 >= it, isErrorVisible)
                }
            }
        }
    },
        modifier = Modifier
            .width(180.dp)
            .height(52.dp)
            .focusable(true)
            .onFocusChanged {
                onTextFieldFocusChanged(it.isFocused)
            },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions {
            defaultKeyboardAction(ImeAction.Previous)
        }
    )
}

@Composable
private fun ComposePasswordPointer(
    isFilled: Boolean = false,
    isError: Boolean = false
) {

    val backgroundColor = when {
        isError -> red
        isFilled -> lightBlue
        else -> gray
    }

    Card(
        shape = CircleShape, modifier = Modifier.size(10.dp), backgroundColor = backgroundColor
    ) { }
}

private fun navigateToRepeatPassword(
    navController: NavController,
    createdPassword: String
) {

    navController.navigate(
        route = OnBoardingScreens.PasswordScreen.Repeat.withArgs(
            createdPassword
        )
    ) {
        launchSingleTop = true
    }
}

private fun navigateToEnterPassword(navController: NavController) {
    navController.navigate(
        OnBoardingScreens.PasswordScreen.Enter.route
    ) {
        popUpTo(OnBoardingScreens.PasswordScreen.Create.route) { inclusive = true }
        launchSingleTop = true
    }
}

fun navigateToMainScreen(context: Context) {
    val intent = Intent(context, MainActivity::class.java)

    context.startActivity(intent)
}

@Preview
@Composable
private fun ComposePasswordPointerPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row {

            repeat(4) {
                ComposePasswordPointer(isFilled = false, isError = true)
            }
        }

        Row {
            repeat(4) {
                ComposePasswordPointer(isFilled = it % 2 == 0, isError = false)
            }
        }
    }
}

@Preview
@Composable
private fun CreatePasswordScreenPreview() {
    PasswordScreen()
}
