package com.pvsb.locktapcompose.presentation.main.categories.privateContacts

import android.os.Bundle
import android.text.Html
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.domain.entity.PrivateContact
import com.pvsb.locktapcompose.presentation.ui.messageTextStyle
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors
import com.pvsb.locktapcompose.presentation.utils.components.textField.ComposePrimarySearchField
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle
import com.pvsb.locktapcompose.presentation.utils.components.BackButton
import com.pvsb.locktapcompose.presentation.utils.components.ComposeContactCell
import com.pvsb.locktapcompose.presentation.utils.components.FloatingAddButton

class PrivateContactsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PrivateContactsScreen()
        }
    }
}

@Composable
private fun PrivateContactsActivity.PrivateContactsScreen() {

    val dummyData = List(10) {
        PrivateContact(
            it.toString(),
            "John ${it + 1}",
            "123",
            null, false
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
        ) {
            BackButton {
                finish()
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.session_option_label_private_contacts),
                    style = titleTextStyle
                )

                Spacer(modifier = Modifier.height(25.dp))

                ComposePrimarySearchField()

                if (dummyData.isEmpty()) {
                    ComposeEmptyState(modifier = Modifier.fillMaxSize())
                } else {
                    ComposeContactsList(dummyData)
                }
            }
        }

        Row(modifier = Modifier.padding(25.dp)) {
            FloatingAddButton() {}
        }
    }
}

@Composable
private fun ComposeContactsList(
    contacts: List<PrivateContact>
) {

    Column() {

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = contacts) {
                ComposeContactCell(contactData = it)
            }
        }
    }
}

@Composable
private fun ComposeEmptyState(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_empty_content),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.empty_content_label),
            style = titleTextStyle,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.private_contacts_empty_content_message),
            color = AppColors.gray,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            style = messageTextStyle
        )
    }
}

@Preview
@Composable
fun ComposeEmptyStatePreview() {
    ComposeEmptyState()
}

@Preview
@Composable
private fun PrivateContactsActivity.PrivateContactsScreenPreview() {
    PrivateContactsScreen()
}
