package com.pvsb.presentation.utils.components.textField

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.utils.components.textField.PrimaryTextFieldConst.ANIMATION_DURATION

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    fieldLabel: Int = 0,
    onValueChanged: (String) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var currentStyle by remember {
        mutableStateOf(TextStyle())
    }

    var offsetY by remember {
        mutableStateOf(0.dp)
    }

    var isTextFieldFocused by remember { mutableStateOf(false) }
    val borderColor = if (isTextFieldFocused) AppColors.lightBlue else Color.Transparent

    HandleLabelTransition(
        isFocused = isTextFieldFocused,
        isTextFilled = text.isNotEmpty()
    ) { style, offset ->
        currentStyle = style
        offsetY = offset
    }

    BasicTextField(
        modifier = modifier
            .height(50.dp)
            .onFocusChanged {
                isTextFieldFocused = it.isFocused
            },
        value = text,
        onValueChange = onValueChanged,
        decorationBox = {

            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = AppColors.secondary,
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(1.dp, color = borderColor)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        0.dp,
                        alignment = { _, space ->
                            space / 2
                        }
                    )
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(id = fieldLabel),
                            style = currentStyle,
                            modifier = Modifier.offset(y = offsetY)
                        )

                        it()
                        Text(
                            text = text,
                            color = Color.White
                        )
                    }
                }
            }
        },
        cursorBrush = SolidColor(Color.White),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
        )
    )
}

@Composable
private fun HandleLabelTransition(
    isFocused: Boolean,
    isTextFilled: Boolean,
    onAnimationProgress: (TextStyle, Dp) -> Unit,
) {

    val transition = updateTransition(
        targetState = isFocused,
        label = "PrimaryTextFieldLabelTransition"
    )

    val progress by transition.animateFloat(
        label = "labelProgress",
        transitionSpec = {
            tween(ANIMATION_DURATION)
        }
    ) {
        when {
            isTextFilled -> {
                1f
            }
            it -> {
                1f
            }
            else -> {
                0f
            }
        }
    }

    val color by transition.animateColor(
        label = "labelColor",
        transitionSpec = {
            tween(1000)
        }
    ) {

        if (it) PrimaryTextFieldConst.FOCUSED_COLOR
        else PrimaryTextFieldConst.NOT_FOCUSED_COLOR
    }

    val offsetY by transition.animateDp(
        label = "labelDp",
        transitionSpec = {
            tween(1000)
        }
    ) {
        when {
            isTextFilled -> {
                (-10).dp
            }
            it -> {
                (-10).dp
            }
            else -> {
                0.dp
            }
        }
    }

    val textStyle = androidx.compose.ui.text.lerp(
        start = PrimaryTextFieldConst.START_STYLE,
        stop = PrimaryTextFieldConst.STOP_STYLE,
        fraction = progress
    ).copy(color = color)

    onAnimationProgress(textStyle, offsetY)
}

@Preview(showBackground = true)
@Composable
private fun ComposeContactInfoTextFieldPreview() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        PrimaryTextField(
            fieldLabel = R.string.contact_details_text_field_first_last_name,
            text = "john doe"
        )
    }
}

private object PrimaryTextFieldConst {
    const val ANIMATION_DURATION = 150
    val FOCUSED_TEXT_SIZE = 10.sp
    val NOT_FOCUSED_TEXT_SIZE = 14.sp
    val FOCUSED_COLOR = AppColors.lightBlue
    val NOT_FOCUSED_COLOR = AppColors.gray

    val START_STYLE = TextStyle(
        fontSize = NOT_FOCUSED_TEXT_SIZE,
    )
    val STOP_STYLE = TextStyle(
        fontSize = FOCUSED_TEXT_SIZE,
    )
}
