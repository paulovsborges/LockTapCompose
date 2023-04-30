package com.pvsb.presentation.categories.allScreen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pvsb.presentation.R
import com.pvsb.presentation.categories.allScreen.photoVault.PhotoVaultActivity
import com.pvsb.presentation.contact.contactList.PrivateContactsActivity
import com.pvsb.presentation.utils.components.ComposeSessionOptionButton
import com.pvsb.presentation.passwords.passwordsList.PasswordsListActivity
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField

@Composable
fun AllScreenContent(
    modifier: Modifier = Modifier
) {

    val optionsLabels = listOf(
        R.string.session_option_label_private_contacts,
        R.string.session_option_label_passwords,
        R.string.session_option_label_photo_vault
    )

    val context = LocalContext.current

    var searchFieldText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ComposePrimarySearchField(modifier = Modifier.fillMaxWidth(), text = searchFieldText) {
            searchFieldText = it
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            repeat(optionsLabels.size) { btnId ->
                ComposeSessionOptionButton(label = optionsLabels[btnId], onClick = {
                    onOptionClicked(btnId, context)
                })
            }
        }
    }
}

private fun onOptionClicked(
    btnId: Int,
    context: Context
) {

    val activity = when (btnId) {
        SessionOptionsButton.PRIVATE_CONTACTS.id -> {
            PrivateContactsActivity::class.java
        }
        SessionOptionsButton.WIFI_PASSWORDS.id -> {
            PasswordsListActivity::class.java
        }
        SessionOptionsButton.PHOTO_VAULT.id -> {
            PhotoVaultActivity::class.java
        }
        else -> return
    }

    val intent = Intent(context, activity)
    context.startActivity(intent)
}
