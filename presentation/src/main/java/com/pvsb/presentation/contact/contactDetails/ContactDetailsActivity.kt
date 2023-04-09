package com.pvsb.presentation.contact.contactDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.R
import com.pvsb.presentation.contact.contactList.ContactsViewModel
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.ui.theme.AppColors.background
import com.pvsb.presentation.ui.theme.AppColors.gray
import com.pvsb.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.presentation.ui.theme.AppColors.red
import com.pvsb.presentation.ui.theme.AppColors.secondary
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeBottomSheetDialog
import com.pvsb.presentation.utils.components.ComposeErrorCard
import com.pvsb.presentation.utils.components.textField.ComposeContactInfoTextField
import com.pvsb.presentation.utils.getFirstLettersFromFullName
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class ContactDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = viewModel<ContactsViewModel>()

            intent.getStringExtra(
                SerializableContact.PRIVATE_CONTACT_DATA_KEY
            )?.let { serializedData ->

                val contactData = Json.decodeFromString<SerializableContact>(
                    serializedData
                )

                viewModel.setContactDetails(contactData.toModel())
            }

            ComposeContentContainer(viewModel)
        }
    }
}

@Composable
private fun ContactDetailsActivity.ComposeContentContainer(
    viewModel: ContactsViewModel
) {

    val state = viewModel.state.collectAsState()
    val contactData = state.value.contactDetails.details
    val isSaveButtonEnabled = state.value.contactDetails.isSaveButtonEnabled

    var contactNameState by remember { mutableStateOf(contactData.name) }
    var contactPhoneNumber by remember { mutableStateOf(contactData.phoneNumber) }
    var isFavorite by remember { mutableStateOf(contactData.isFavorite) }

    val currentContactDetails = Contact(
        "", contactNameState, contactPhoneNumber, null, isFavorite
    )

    ComposeContent(contactData = contactData.copy(
        isFavorite = isFavorite
    ),
        contactNameState = contactNameState,
        contactPhoneNumber = contactPhoneNumber,
        error = state.value.error,
        isSaveButtonEnabled = isSaveButtonEnabled,
        onContactNameChange = {
            contactNameState = it
            viewModel.onFieldsChanged(currentContactDetails)
        },
        onContactPhoneNumberChange = {
            contactPhoneNumber = it
            viewModel.onFieldsChanged(currentContactDetails)
        },
        onFavoriteClicked = {
            isFavorite = it
            viewModel.onFieldsChanged(currentContactDetails)
        },
        onSaveClicked = {
            viewModel.insertContact(
                Contact(
                    contactData.contactId, contactNameState, contactPhoneNumber, null, isFavorite
                )
            )
        },
        onDelete = {
            viewModel.deleteContact(contactData.contactId)
            finish()
        },
        onDismissError = {
            viewModel.dismissError()
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContactDetailsActivity.ComposeContent(
    contactData: Contact,
    contactNameState: String = "",
    contactPhoneNumber: String = "",
    error: TypedMessage? = null,
    isSaveButtonEnabled: Boolean = false,
    onContactNameChange: (String) -> Unit = {},
    onContactPhoneNumberChange: (String) -> Unit = {},
    onFavoriteClicked: (Boolean) -> Unit = {},
    onSaveClicked: () -> Unit = {},
    onDelete: () -> Unit = {},
    onDismissError: () -> Unit = {},
) {

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true },
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        BackButton {
            finish()
        }

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Box(modifier = Modifier.clickable {
                    onFavoriteClicked(!contactData.isFavorite)
                }) {
                    if (contactData.isFavorite) {

                        Icon(
                            modifier = Modifier.size(25.dp),
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = "",
                            tint = lightBlue
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(150.dp),
                        backgroundColor = secondary
                    ) {
                        contactData.imageFilePath?.let { imagePath ->
                            ComposeContactImage(
                                imagePath = imagePath
                            )
                        } ?: ComposeContactImagePlaceholder(
                            name = contactData.name
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = stringResource(id = R.string.contact_details_change_photo_btn_label),
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White,
                        modifier = Modifier.clickable {})

                    Spacer(modifier = Modifier.height(25.dp))

                    ComposeContactInfoTextField(
                        fieldLabel = R.string.contact_details_text_field_first_last_name,
                        text = contactNameState,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        onContactNameChange(it)
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    ComposeContactInfoTextField(
                        fieldLabel = R.string.contact_details_text_field_phone_number,
                        text = contactPhoneNumber,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        onContactPhoneNumberChange(it)
                    }

                    Spacer(modifier = Modifier.height(35.dp))

                    Text(
                        text = stringResource(id = R.string.contact_details_delete_contact_btn_label),
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        color = red,
                        fontSize = 16.sp,
                        modifier = Modifier.clickable {
                            scope.launch {
                                modalSheetState.show()
                            }
                        }
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 20.dp, vertical = 25.dp
                            ),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ComposeSaveButton(
                            onClick = onSaveClicked, isEnabled = isSaveButtonEnabled
                        )
                    }
                }
            }
        }

        ComposeErrorCard(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable {
                    onDismissError()
                },
            isErrorVisible = error != null, error
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            ComposeBottomSheetDialog(
                state = modalSheetState,
                positiveBtnLabel = R.string.button_label_confirm,
                negativeBtnLabel = R.string.button_label_cancel,
                title = TypedMessage.Reference(R.string.bottom_sheet_confirmation_title),
                message = TypedMessage.Reference(R.string.bottom_sheet_delete_contact_message),
                onPositiveClick = onDelete
            )
        }
    }
}

@Composable
private fun ComposeContactImagePlaceholder(
    modifier: Modifier = Modifier, name: String
) {
    val firstLetter = getFirstLettersFromFullName(name)

    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(
            text = firstLetter,
            fontSize = 30.sp,
            fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
            color = Color.White
        )
    }
}

@Composable
private fun ComposeContactImage(
    modifier: Modifier = Modifier, imagePath: String
) {

    Image(
        painter = rememberAsyncImagePainter(model = imagePath),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun ComposeSaveButton(
    modifier: Modifier = Modifier, onClick: () -> Unit = {}, isEnabled: Boolean = false
) {

    val backgroundColor = if (isEnabled) lightBlue else secondary
    val textColor = if (isEnabled) Color.White else gray

    Button(
        onClick = {
            if (isEnabled) {
                onClick()
            }
        },
        shape = RoundedCornerShape(corner = CornerSize(40.dp)),
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        elevation = null
    ) {
        Text(
            text = stringResource(id = R.string.contact_details_save_contact),
            fontFamily = FontFamily(
                Font(
                    R.font.sf_pro_display_regular, weight = FontWeight.Thin
                )
            ),
            color = textColor
        )
    }
}

@Preview
@Composable
fun ComposeSaveButtonPreview() {
    Column(
        modifier = Modifier
            .background(background)
            .wrapContentSize()
            .padding(
                horizontal = 20.dp, vertical = 25.dp
            ),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ComposeSaveButton(isEnabled = true)

        Spacer(modifier = Modifier.height(20.dp))
        ComposeSaveButton()
    }
}

@Preview
@Composable
private fun ContactDetailsActivity.ComposeContentPreview() {
    ComposeContent(
        Contact(
            "", "", "", null, false
        )
    )
}
