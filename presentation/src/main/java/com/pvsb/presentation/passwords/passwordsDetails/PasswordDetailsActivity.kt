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
import androidx.compose.foundation.layout.Arrangement
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

        var title by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var additionalInfo by remember {
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

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.new_password_label),
                        style = titleTextStyle
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    ComposeFields(
                        titleText = title,
                        passwordText = password,
                        infoText = additionalInfo,
                        onTitleChanged = { title = it },
                        onPasswordChanged = { password = it },
                        onInfoChanged = { additionalInfo = it },
                    )
                }
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

    @Composable
    private fun ComposeFields(
        modifier: Modifier = Modifier,
        titleText: String,
        passwordText: String,
        infoText: String,
        onTitleChanged: (String) -> Unit,
        onPasswordChanged: (String) -> Unit,
        onInfoChanged: (String) -> Unit,
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = modifier
        ) {
            PrimaryTextField(
                fieldLabel = R.string.new_password_text_field_title_label,
                text = titleText,
                onValueChanged = onTitleChanged
            )
            PrimaryTextField(
                fieldLabel = R.string.new_password_text_field_password_label,
                text = passwordText,
                onValueChanged = onPasswordChanged
            )
            PrimaryTextField(
                fieldLabel = R.string.new_password_text_field_additional_info_label,
                text = infoText,
                onValueChanged = onInfoChanged
            )
        }
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContent()
    }
}