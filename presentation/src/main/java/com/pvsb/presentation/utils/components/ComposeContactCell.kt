package com.pvsb.presentation.utils.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pvsb.domain.entity.Contact
import com.pvsb.presentation.R
import com.pvsb.presentation.contact.contactDetails.ContactDetailsActivity
import com.pvsb.presentation.contact.contactDetails.SerializableContact
import com.pvsb.presentation.ui.AppStyle.AppColors.gray
import com.pvsb.presentation.ui.AppStyle.AppColors.secondary
import com.pvsb.presentation.utils.copyTextToClipBoard
import com.pvsb.presentation.utils.getFirstLettersFromFullName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ComposeContactCell(
    modifier: Modifier = Modifier,
    contactData: Contact
) {

    val context = LocalContext.current

    Row(
        modifier = modifier
            .height(55.dp)
            .fillMaxWidth()
            .clickable {
                navigateToContactDetails(context, contactData)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            backgroundColor = secondary,
            shape = CircleShape,
            modifier = Modifier.size(45.dp)
        ) {
            contactData.imageFilePath?.let { imagePath ->
                ComposeContactImage(imagePath = imagePath)
            } ?: ComposeContactImagePlaceholder(
                name = contactData.name
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    alignment = { _, space ->
                        space / 2
                    }
                )
            ) {
                Text(text = contactData.name, color = Color.White)
                Text(text = contactData.phoneNumber, color = gray)
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "",
                    tint = gray,
                    modifier = Modifier.clickable {
                        context.copyTextToClipBoard(contactData.phoneNumber)
                    }
                )
            }
        }
    }
}

private fun navigateToContactDetails(
    context: Context,
    contactData: Contact
) {

    val serializableData = contactData.run {
        SerializableContact(
            contactId,
            name,
            phoneNumber,
            imageFilePath,
            isFavorite
        )
    }

    val serializedData = Json.encodeToString(serializableData)

    val intent = Intent(context, ContactDetailsActivity::class.java)

    intent.putExtra(
        SerializableContact.PRIVATE_CONTACT_DATA_KEY,
        serializedData
    )

    context.startActivity(intent)
}

@Composable
private fun ComposeContactImagePlaceholder(
    modifier: Modifier = Modifier,
    name: String
) {
    val firstLetter = getFirstLettersFromFullName(name).take(1)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = firstLetter,
            fontSize = 20.sp,
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

    Image(
        painter = rememberAsyncImagePainter(model = imagePath),
        contentDescription = "",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun ComposeContactCellPreview() {

    val dummyContact = Contact(
        "12345",
        "John Doe",
        "347-671-1254",
        null,
        false
    )

    ComposeContactCell(contactData = dummyContact)
}
