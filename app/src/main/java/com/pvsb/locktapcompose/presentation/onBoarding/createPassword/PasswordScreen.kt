package com.pvsb.locktapcompose.presentation.onBoarding.createPassword

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.main.MainActivity
import com.pvsb.locktapcompose.presentation.onBoarding.OnBoardingScreens
import com.pvsb.locktapcompose.presentation.ui.messageTextStyle
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.gray
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.red
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.secondary
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle
import com.pvsb.locktapcompose.presentation.utils.components.BackButton

@Composable
fun PasswordScreen(
    navController: NavController, screenType: PasswordScreenType = PasswordScreenType.CreatePassword
) {

    var isErrorVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg_splash)),
        contentAlignment = Alignment.TopStart
    ) {

        if (screenType is PasswordScreenType.RepeatPassword) {
            BackButton {
                navController.popBackStack()
            }
        }

        ComposeErrorCard(
            isErrorVisible = isErrorVisible,
            modifier = Modifier.padding(horizontal = 10.dp)
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

            ComposeTextField(isErrorVisible, screenType, navController) {
                isErrorVisible = it
            }
        }
    }
}

@Composable
private fun ComposeTextField(
    isErrorVisible: Boolean,
    screenType: PasswordScreenType,
    navController: NavController,
    onErrorChanged: (Boolean) -> Unit
) {

    val context = LocalContext.current
    val maxChars = 4
    var password by remember { mutableStateOf("") }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    val borderColor = when {
        isErrorVisible -> red
        isTextFieldFocused -> lightBlue
        else -> Color.Transparent
    }

    BasicTextField(value = password, onValueChange = {
        password = it.take(maxChars)

        if (password.length == maxChars) {
            when (screenType) {
                PasswordScreenType.CreatePassword -> {
                    navigateToRepeatPassword(navController, password)
                }
                is PasswordScreenType.RepeatPassword -> {
                    if (it == screenType.createdPassword) {
                        navigateToEnterPassword(navController)
                    } else {
                        onErrorChanged(true)
                    }
                }
                PasswordScreenType.EnterPassword -> {
                    navigateToMainScreen(context)
                }
            }
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
                horizontalArrangement = Arrangement.spacedBy(30.dp,
                    Alignment.Horizontal { _, space, _ ->
                        space / 2
                    })
            ) {
                repeat(maxChars) {
                    ComposePasswordPointer(password.length - 1 >= it, isErrorVisible)
                }
            }
        }
    }, modifier = Modifier
        .width(180.dp)
        .height(52.dp)
        .focusable(true)
        .onFocusChanged {
            isTextFieldFocused = it.isFocused
        }, keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
    ), keyboardActions = KeyboardActions {
        defaultKeyboardAction(ImeAction.Previous)
    })
}

@Composable
private fun ComposePasswordPointer(
    isFilled: Boolean = false, isError: Boolean = false
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
    navController: NavController, createdPassword: String
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

@Composable
private fun ComposeErrorCard(
    modifier: Modifier = Modifier,
    isErrorVisible: Boolean
) {

    val density = LocalDensity.current
    AnimatedVisibility(
        visible = isErrorVisible,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(5.dp),
            color = secondary
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(painter = painterResource(id = R.drawable.ic_error), "", tint = red)

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(id = R.string.password_incorrect_label),
                    fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                    color = red,
                    fontSize = 14.sp
                )
            }
        }
    }
}

fun navigateToMainScreen(context: Context) {
    val intent = Intent(context, MainActivity::class.java)

    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun ComposeErrorCardPreview() {
    ComposeErrorCard(modifier = Modifier.padding(20.dp), true)
}

@Preview
@Composable
private fun ComposePasswordPointerPreview() {
    Row {
        repeat(4) {
            ComposePasswordPointer(false, true)
        }
    }
}

@Preview
@Composable
fun CreatePasswordScreenPreview() {
    val navController = rememberNavController()
    PasswordScreen(navController)
}