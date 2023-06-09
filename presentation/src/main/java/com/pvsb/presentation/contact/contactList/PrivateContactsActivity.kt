package com.pvsb.presentation.contact.contactList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.R
import com.pvsb.presentation.contact.contactDetails.ContactDetailsActivity
import com.pvsb.presentation.ui.AppStyle.AppColors
import com.pvsb.presentation.ui.AppStyle.AppColors.background
import com.pvsb.presentation.ui.AppStyle.TextStyles.messageTextStyle
import com.pvsb.presentation.ui.AppStyle.TextStyles.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.ComposeContactCell
import com.pvsb.presentation.utils.components.ComposeErrorCard
import com.pvsb.presentation.utils.components.FloatingAddButton
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivateContactsActivity : ComponentActivity() {

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val state = viewModel.state.collectAsState()
            PrivateContactsScreen(
                state.value.error, state.value.contactsList
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getContacts()
    }
}

@Composable
private fun PrivateContactsActivity.PrivateContactsScreen(
    error: TypedMessage? = null,
    contacts: List<Contact> = emptyList()
) {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd
    ) {

        ComposeErrorCard(
            modifier = Modifier.padding(horizontal = 10.dp), isErrorVisible = error != null, error
        )

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

                if (contacts.isEmpty()) {
                    ComposeEmptyState(modifier = Modifier.fillMaxSize())
                } else {
                    ComposeContactsList(contacts)
                }
            }
        }

        Row(modifier = Modifier.padding(25.dp)) {
            FloatingAddButton {
                navigateToAddContact(this@PrivateContactsScreen)
            }
        }
    }
}

@Composable
private fun ComposeContactsList(
    contacts: List<Contact>
) {

    Column {

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
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_empty_content), contentDescription = ""
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
private fun ComposeEmptyStatePreview() {
    ComposeEmptyState()
}

@Preview
@Composable
private fun PrivateContactsActivity.PrivateContactsScreenPreview() {
    PrivateContactsScreen()
}

private fun navigateToAddContact(
    context: Context
) {
    val intent = Intent(context, ContactDetailsActivity::class.java)
    context.startActivity(intent)
}
