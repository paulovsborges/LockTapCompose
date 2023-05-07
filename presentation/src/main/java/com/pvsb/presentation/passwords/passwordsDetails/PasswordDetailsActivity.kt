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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle.AppColors
import com.pvsb.presentation.ui.AppStyle.TextStyles.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeBottomSheetDialog
import com.pvsb.presentation.utils.components.ComposeErrorCard
import com.pvsb.presentation.utils.components.PrimaryButton
import com.pvsb.presentation.utils.components.textField.PrimaryTextField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordDetailsActivity : ComponentActivity() {

    private val viewModel: PasswordDetailsViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(PASSWORD_ID_KEY)?.let { passwordId ->
            viewModel.getPasswordDetails(passwordId)
        }

        val passwordId = intent.getStringExtra(PASSWORD_ID_KEY)

        passwordId?.let {
            viewModel.getPasswordDetails(passwordId)
        }

        setContent {

            val bottomSheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Hidden,
                confirmValueChange = { true },
                skipHalfExpanded = true
            )

            val scope = rememberCoroutineScope()

            val state = viewModel.state.collectAsState()
            if (state.value.shouldCloseScreen) finish()

            ComposeContent(
                state = state.value,
                isContactDetails = passwordId != null,
                onDeleteClick = {
                    scope.launch {
                        bottomSheetState.show()
                    }
                }
            )

            ComposeDeleteContactConfirmationDialog(
                state = bottomSheetState,
                onPositiveClick = {
                    viewModel.deletePassword(state.value.passwordDetails.details.id)
                }
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ComposeDeleteContactConfirmationDialog(
        state: ModalBottomSheetState,
        onPositiveClick: () -> Unit
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            ComposeBottomSheetDialog(
                state = state,
                positiveBtnLabel = R.string.button_label_confirm,
                negativeBtnLabel = R.string.button_label_cancel,
                title = TypedMessage.Reference(R.string.bottom_sheet_confirmation_title),
                message = TypedMessage.Reference(R.string.delete_password_dialog_message),
                onPositiveClick = onPositiveClick
            )
        }

    }

    @Composable
    private fun ComposeContent(
        state: PasswordDetailsState = PasswordDetailsState(),
        isContactDetails: Boolean = false,
        onDeleteClick: () -> Unit = {}
    ) {

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
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.new_password_label),
                        style = titleTextStyle
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    ComposeFields(
                        fields = state.fields
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    if (isContactDetails) {
                        Text(
                            text = stringResource(id = R.string.delete_password_button_label),
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            color = AppColors.red,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                onDeleteClick()
                            }
                        )
                    }
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
                PrimaryButton(isEnabled = state.isSaveButtonEnabled, onClick = {
                    viewModel.savePassword()
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
        fields: PasswordDetailsFields = PasswordDetailsFields(),
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = modifier
        ) {
            PrimaryTextField(
                fieldLabel = R.string.new_password_text_field_title_label,
                text = fields.title,
                onValueChanged = {
                    viewModel.onFieldChanged(
                        PasswordDetailsViewModel.FieldType.Title(
                            it
                        )
                    )
                }
            )
            PrimaryTextField(
                fieldLabel = R.string.new_password_text_field_password_label,
                text = fields.password,
                onValueChanged = {
                    viewModel.onFieldChanged(
                        PasswordDetailsViewModel.FieldType.Password(
                            it
                        )
                    )
                }
            )
            PrimaryTextField(
                fieldLabel = R.string.new_password_text_field_additional_info_label,
                text = fields.additionalInfo,
                onValueChanged = {
                    viewModel.onFieldChanged(
                        PasswordDetailsViewModel.FieldType.AdditionalInfo(
                            it
                        )
                    )
                }
            )
        }
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContent()
    }

    companion object {
        const val PASSWORD_ID_KEY = "PASSWORD_ID_KEY"
    }
}
