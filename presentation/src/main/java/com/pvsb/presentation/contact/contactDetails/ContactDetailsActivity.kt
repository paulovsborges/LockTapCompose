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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.pvsb.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.presentation.ui.theme.AppColors.red
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeErrorCard
import com.pvsb.presentation.utils.components.textField.ComposeContactInfoTextField
import com.pvsb.presentation.utils.getFirstLettersFromFullName
import dagger.hilt.android.AndroidEntryPoint
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

    var contactNameState by remember { mutableStateOf(contactData.name) }
    var contactPhoneNumber by remember { mutableStateOf(contactData.phoneNumber) }

    ComposeContent(
        contactData = contactData,
        contactNameState = contactNameState,
        contactPhoneNumber = contactPhoneNumber,
        state.value.error,
        onContactNameChange = {
            contactNameState = it
        },
        onContactPhoneNumberChange = {
            contactPhoneNumber = it
        },
        onSaveClicked = {
            viewModel.insertContact(
                Contact(
                    "", contactNameState, contactPhoneNumber, null, false
                )
            )
        }
    )
}

@Composable
private fun ContactDetailsActivity.ComposeContent(
    contactData: Contact,
    contactNameState: String = "",
    contactPhoneNumber: String = "",
    error: TypedMessage? = null,
    onContactNameChange: (String) -> Unit = {},
    onContactPhoneNumberChange: (String) -> Unit = {},
    onSaveClicked: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        BackButton {
            finish()
        }

        Column() {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Box(modifier = Modifier.clickable {

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
                        backgroundColor = AppColors.secondary
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
                        fontSize = 16.sp
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
                        Button(
                            onClick = {
                                onSaveClicked()
                            },
                            shape = RoundedCornerShape(corner = CornerSize(40.dp)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = lightBlue
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
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        ComposeErrorCard(
            modifier = Modifier.padding(horizontal = 10.dp),
            isErrorVisible = error != null,
            error
        )
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

@Preview
@Composable
private fun ContactDetailsActivity.ComposeContentPreview() {
    ComposeContent(
        Contact(
            "",
            "",
            "",
            null, false
        )
    )
}
