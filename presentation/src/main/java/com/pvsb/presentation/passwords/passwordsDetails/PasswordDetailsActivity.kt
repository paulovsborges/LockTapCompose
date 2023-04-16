package com.pvsb.presentation.passwords.passwordsDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.domain.useCase.password.addPassword.AddPasswordUseCase
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.ui.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeErrorCard
import com.pvsb.presentation.utils.components.PrimaryButton
import com.pvsb.presentation.utils.components.textField.PrimaryTextField
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordDetailsActivity : ComponentActivity() {

    private val viewModel: PasswordDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.state.collectAsState()

            if (state.value.shouldCloseScreen) finish()

            ComposeContent(state.value)
        }
    }

    @Composable
    private fun ComposeContent(
        state: PasswordDetailsState = PasswordDetailsState()
    ) {

        var title by remember { mutableStateOf(state.details.title) }
        var password by remember { mutableStateOf(state.details.password) }
        var additionalInfo by remember { mutableStateOf(state.details.additionalInfo ?: "") }

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
                        onTitleChanged = {
                            title = it
                            viewModel.onFieldsChanged(state.details.copy(title = it))
                        },
                        onPasswordChanged = {
                            password = it
                            viewModel.onFieldsChanged(state.details.copy(password = it))
                        },
                        onInfoChanged = {
                            additionalInfo = it
                            viewModel.onFieldsChanged(state.details.copy(additionalInfo = it))
                        },
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 20.dp, vertical = 25.dp
                    ), contentAlignment = Alignment.BottomCenter
            ) {
                PrimaryButton(
                    isEnabled = state.isSaveButtonEnabled,
                    onClick = {
                        viewModel.addPassword(
                            AddPasswordUseCase.Input(
                                title, password, additionalInfo
                            )
                        )
                    })
            }

            ComposeErrorCard(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable {
                        viewModel.dismissError()
                    },
                isErrorVisible = state.error != null
            )
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
            verticalArrangement = Arrangement.spacedBy(25.dp), modifier = modifier
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
