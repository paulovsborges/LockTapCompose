package com.pvsb.presentation.passwords.passwordsDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.ui.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.PrimaryButton
import com.pvsb.presentation.utils.components.textField.PrimaryTextField
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeContent()
        }
    }

    @Composable
    private fun ComposeContent() {

        var searchFieldText by remember {
            mutableStateOf("")
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
        ) {

            Column {

                BackButton {
                    finish()
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = stringResource(id = R.string.new_password_label),
                        style = titleTextStyle
                    )

                    PrimaryTextField(
                        fieldLabel = R.string.new_password_text_field_title_label,
                        text = searchFieldText,
                        onValueChanged = { searchFieldText = it }
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))
/*
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {

                    var state by remember {
                        mutableStateOf(false)
                    }

                    val transition = updateTransition(
                        targetState = state,
                        label = "TextFieldInputState"
                    )

                    val progress by transition.animateFloat(
                        label = "labelProgress",
                        transitionSpec = {
                            tween(1000)
                        }
                    ) {
                        if (it) 1f else 0f
                    }

                    val color by transition.animateColor(
                        label = "labelColor",
                        transitionSpec = {
                            tween(1000)
                        }
                    ) {
                        if (it) Color.Black else Color.Green
                    }

                    val offsetY by transition.animateDp(
                        label = "labelDp",
                        transitionSpec = {
                            tween(1000)
                        }
                    ) {
                        if (it) (-100).dp else 0.dp
                    }

//                    val textStyle = lerp(
//                        10.sp,
//                        20.sp,
//                        progress
//                    ).let {
//
//                    }


                    val textStyle = androidx.compose.ui.text.lerp(
                        start = MaterialTheme.typography.subtitle1,
                        stop = MaterialTheme.typography.caption,
                        fraction = progress
                    ).copy(color = color)

                    Text(
                        text = "AAAAAAAAA",
                        color = color,
                        style = textStyle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                state = !state
                            }
                            .offset(y = offsetY),
                        textAlign = TextAlign.Center
                    )
                }

 */
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 20.dp, vertical = 25.dp
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                PrimaryButton()
            }
        }
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContent()
    }

    enum class InputPhase {
        // Text field is focused
        Focused,

        // Text field is not focused and input text is empty
        UnfocusedEmpty,

        // Text field is not focused but input text is not empty
        UnfocusedNotEmpty
    }
}