package com.pvsb.presentation.contact.contactDetails

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.R
import com.pvsb.presentation.contact.contactList.ContactsViewModel
import com.pvsb.presentation.ui.theme.AppColors.background
import com.pvsb.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.presentation.ui.theme.AppColors.red
import com.pvsb.presentation.ui.theme.AppColors.secondary
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeBottomSheetDialog
import com.pvsb.presentation.utils.components.ComposeErrorCard
import com.pvsb.presentation.utils.components.PrimaryButton
import com.pvsb.presentation.utils.components.textField.PrimaryTextField
import com.pvsb.presentation.utils.getFirstLettersFromFullName
import com.pvsb.presentation.utils.getUriAccessPermission
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContactDetailsActivity.ComposeContentContainer(
    viewModel: ContactsViewModel
) {

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { true },
        skipHalfExpanded = true
    )

    val state = viewModel.state.collectAsState()
    val contactData = state.value.contactDetails.details
    val isSaveButtonEnabled = state.value.contactDetails.isSaveButtonEnabled

    val scope = rememberCoroutineScope()
    var currentName by remember { mutableStateOf(contactData.name) }
    var currentPhoneNumber by remember { mutableStateOf(contactData.phoneNumber) }
    var currentIsFavorite by remember { mutableStateOf(contactData.isFavorite) }
    var currentImagePath by remember { mutableStateOf(contactData.imageFilePath) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let(::getUriAccessPermission)
        currentImagePath = uri.toString()
    }

    val currentContactData = Contact(
        contactData.contactId, currentName, currentPhoneNumber, currentImagePath, currentIsFavorite
    ).also {
        viewModel.onFieldsChanged(it)
    }

    if (state.value.shouldCloseScreen) {
        finish()
    }

    ComposeContent(
        contactData = currentContactData,
        error = state.value.error,
        isSaveButtonEnabled = isSaveButtonEnabled,
        photoPicker = singlePhotoPickerLauncher,
        onContactNameChange = {
            currentName = it
        },
        onContactPhoneNumberChange = {
            currentPhoneNumber = it
        },
        onFavoriteClicked = {
            currentIsFavorite = it
        },
        onSaveClicked = {
            viewModel.insertContact(
                Contact(
                    contactData.contactId,
                    currentName,
                    currentPhoneNumber,
                    currentImagePath,
                    currentIsFavorite
                )
            )
        },
        onDismissError = {
            viewModel.dismissError()
        },
        onDeleteClick = {
            scope.launch {
                bottomSheetState.show()
            }
        }
    )

    ComposeDeleteContactConfirmationDialog(
        bottomSheetState,
        contactData.contactId,
        viewModel
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContactDetailsActivity.ComposeDeleteContactConfirmationDialog(
    state: ModalBottomSheetState,
    contactId: String,
    viewModel: ContactsViewModel
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        ComposeBottomSheetDialog(
            state = state,
            positiveBtnLabel = R.string.button_label_confirm,
            negativeBtnLabel = R.string.button_label_cancel,
            title = TypedMessage.Reference(R.string.bottom_sheet_confirmation_title),
            message = TypedMessage.Reference(R.string.bottom_sheet_delete_contact_message),
            onPositiveClick = {
                viewModel.deleteContact(contactId)
                finish()
            }
        )
    }
}

@Composable
private fun ContactDetailsActivity.ComposeContent(
    contactData: Contact,
    error: TypedMessage? = null,
    isSaveButtonEnabled: Boolean = false,
    photoPicker: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>? = null,
    onContactNameChange: (String) -> Unit = {},
    onContactPhoneNumberChange: (String) -> Unit = {},
    onFavoriteClicked: (Boolean) -> Unit = {},
    onSaveClicked: () -> Unit = {},
    onDismissError: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        BackButton {
            finish()
        }

        Column {

            ComposeFavoriteButton(
                isFavorite = contactData.isFavorite, onFavoriteClicked = onFavoriteClicked
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    ComposeContactImage(
                        contactImage = contactData.imageFilePath, contactName = contactData.name
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.contact_details_change_photo_btn_label),
                        fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White,
                        modifier = Modifier.clickable {
                            photoPicker?.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    PrimaryTextField(
                        fieldLabel = R.string.contact_details_text_field_first_last_name,
                        text = contactData.name,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        onContactNameChange(it)
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    PrimaryTextField(
                        fieldLabel = R.string.contact_details_text_field_phone_number,
                        text = contactData.phoneNumber,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        onContactPhoneNumberChange(it)
                    }

                    Spacer(modifier = Modifier.height(35.dp))

                    if (contactData.contactId.isNotBlank()) {

                        Text(
                            text = stringResource(id = R.string.contact_details_delete_contact_btn_label),
                            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                            color = red,
                            fontSize = 16.sp,
                            modifier = Modifier.clickable {
                                onDeleteClick()
                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 20.dp, vertical = 25.dp
                            ),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PrimaryButton(
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
    }
}

@Composable
private fun ComposeFavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onFavoriteClicked: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier.clickable {
                onFavoriteClicked(!isFavorite)
            }
        ) {
            if (isFavorite) {

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
}

@Composable
private fun ComposeContactImage(
    modifier: Modifier = Modifier,
    contactImage: String? = null,
    contactName: String = ""
) {
    Card(
        shape = CircleShape, modifier = modifier.size(150.dp), backgroundColor = secondary
    ) {

        contactImage?.let { imagePath ->
            ComposeContactImage(
                imagePath = imagePath
            )
        } ?: ComposeContactImagePlaceholder(
            name = contactName
        )
    }
}

@Composable
private fun ComposeContactImagePlaceholder(
    modifier: Modifier = Modifier,
    name: String
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
    modifier: Modifier = Modifier,
    imagePath: String
) {

    val painter =
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = imagePath)
                .build(),
            onError = {
                Log.d("", "### ${it.result.throwable.message}")
            }
        )

    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun ContactDetailsActivity.ComposeContentPreview() {
    ComposeContent(
        Contact(
            "", "paulo borges", "", null, false
        )
    )
}
