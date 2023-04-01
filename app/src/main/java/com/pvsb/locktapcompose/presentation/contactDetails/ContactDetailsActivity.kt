package com.pvsb.locktapcompose.presentation.contactDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.locktapcompose.presentation.utils.components.BackButton
import com.pvsb.locktapcompose.presentation.utils.getFirstLettersFromFullName
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ContactDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val serializedData = intent.getStringExtra(
            SerializablePrivateContact.PRIVATE_CONTACT_DATA_KEY
        ) ?: throw NullPointerException("contact data is null")

        val contactData = Json.decodeFromString<SerializablePrivateContact>(
            serializedData
        )

        setContent {
            ComposeContent(contactData)
        }
    }
}

@Composable
private fun ContactDetailsActivity.ComposeContent(
    contactData: SerializablePrivateContact
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
                if (contactData.isFavorite) {

                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Rounded.Favorite, contentDescription = "",
                        tint = lightBlue
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder, contentDescription = "",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

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
            }
        }
    }
}

@Composable
private fun ComposeContactImagePlaceholder(
    modifier: Modifier = Modifier,
    name: String
) {
    val firstLetter = getFirstLettersFromFullName(name)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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

    val dummyData = SerializablePrivateContact(

        "12345",
        "John Doe",
        "347-671-1254",
        null,
        true
    )

    ComposeContent(dummyData)
}