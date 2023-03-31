package com.pvsb.locktapcompose.presentation.main.categories

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.main.MainActivity
import com.pvsb.locktapcompose.presentation.main.MainScreens
import com.pvsb.locktapcompose.presentation.main.categories.privateContacts.PrivateContactsActivity
import com.pvsb.locktapcompose.presentation.main.shared.searchFIeld.ComposeSearchField
import com.pvsb.locktapcompose.presentation.main.shared.sessionOptions.ComposeSessionOptionButton
import com.pvsb.locktapcompose.presentation.main.shared.viewPager.ComposeViewPager
import com.pvsb.locktapcompose.presentation.main.shared.viewPager.ViewPagerContentType
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle


enum class SessionOptionsButton(val id: Int) {
    PRIVATE_CONTACTS(0),
    WIFI_PASSWORDS(1),
    PHOTO_VAULT(2),
}

@ExperimentalFoundationApi
@Composable
fun CategoriesScreen(
    label: Int
) {

    var contentPage by remember { mutableStateOf<ViewPagerContentType>(ViewPagerContentType.All) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(start = 20.dp, end = 20.dp, top = 15.dp), horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(id = label), style = titleTextStyle
        )

        Spacer(modifier = Modifier.height(20.dp))

        ComposeViewPager(
            modifier = Modifier.fillMaxWidth(), contents = listOf(
                ViewPagerContentType.All,
                ViewPagerContentType.Favorites
            )
        ) { type ->

            contentPage = type
        }

        when (contentPage) {
            ViewPagerContentType.All -> {
                AllScreenContent(
                    modifier = Modifier.fillMaxSize()
                )
            }
            ViewPagerContentType.Favorites -> {
                FavoriteScreenContent(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun AllScreenContent(
    modifier: Modifier = Modifier
) {

    val optionsLabels = listOf(
        R.string.session_option_label_private_contacts,
        R.string.session_option_label_wifi_passwords,
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
        ComposeSearchField(modifier = Modifier, text = searchFieldText) {
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
            MainActivity::class.java
        }
        SessionOptionsButton.PHOTO_VAULT.id -> {
            PrivateContactsActivity::class.java
        }
        else -> return
    }

    val intent = Intent(context, activity)
    context.startActivity(intent)
}

@Composable
private fun FavoriteScreenContent(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.background(Color.Blue), contentAlignment = Alignment.Center) {
        Text(text = "favorites content")
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen(MainScreens.Categories.label)
}