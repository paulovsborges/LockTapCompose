package com.pvsb.locktapcompose.presentation.main.categories.privateContacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.utils.components.searchFIeld.ComposePrimarySearchField
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle
import com.pvsb.locktapcompose.presentation.utils.components.BackButton

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

    Column(
        modifier = Modifier.fillMaxSize().background(background)
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

            ComposePrimarySearchField()
        }
    }
}

@Preview
@Composable
private fun PrivateContactsActivity.PrivateContactsScreenPreview() {
    PrivateContactsScreen()
}