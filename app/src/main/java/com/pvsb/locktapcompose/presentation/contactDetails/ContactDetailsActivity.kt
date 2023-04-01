package com.pvsb.locktapcompose.presentation.contactDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.utils.components.BackButton
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
            Text(text = contactData.name)
        }
    }
}

@Preview
@Composable
private fun ContactDetailsActivity.ComposeContentPreview() {

    val dummyData = SerializablePrivateContact(

        "12345",
        "John Doe",
        "347-671-1254",
        null
    )

    ComposeContent(dummyData)
}