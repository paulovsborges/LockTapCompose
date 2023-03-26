package com.pvsb.locktapcompose.presentation.createPassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.ui.messageTextStyle
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.gray
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.secondary
import com.pvsb.locktapcompose.presentation.ui.theme.Shapes
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle
import kotlinx.coroutines.flow.Flow

@Composable
fun CreatePasswordScreen(
    navController: NavController
) {

    val maxChars = 4
    var password by remember { mutableStateOf("") }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    val borderColor = if (isTextFieldFocused) lightBlue else Color.Transparent

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.bg_splash)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(65.dp))

        Text(
            text = stringResource(id = R.string.create_password_title), style = titleTextStyle
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = stringResource(id = R.string.create_password_message),
            style = messageTextStyle
        )

        Spacer(modifier = Modifier.height(44.dp))

        BasicTextField(
            value = password,
            onValueChange = {
                password = it.take(4)
            },
            decorationBox = {
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
                            })
                    ) {
                        repeat(maxChars) {
                            ComposePasswordPointer(password.length - 1 >= it)
                        }
                    }
                }
            },
            modifier = Modifier
                .width(180.dp)
                .height(52.dp)
                .focusable(true)
                .onFocusChanged {
                    isTextFieldFocused = it.isFocused
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions {
                defaultKeyboardAction(ImeAction.Previous)
            }
        )
    }
}

@Composable
private fun ComposePasswordPointer(
    isFilled: Boolean
) {

    val backgroundColor = if (isFilled) {
        lightBlue
    } else {
        gray
    }

    Card(
        shape = CircleShape,
        modifier = Modifier
            .size(10.dp),
        backgroundColor = backgroundColor
    ) { }
}

@Preview
@Composable
private fun ComposePasswordPointerPreview() {
    Row() {
        ComposePasswordPointer(true)
        ComposePasswordPointer(false)
    }
}

@Preview
@Composable
fun CreatePasswordScreenPreview() {
    val navController = rememberNavController()
    CreatePasswordScreen(navController)
}