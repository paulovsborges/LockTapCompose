package com.pvsb.presentation.categories.favoriteScreen.passwords

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.pvsb.domain.entity.Password
import com.pvsb.presentation.passwords.passwordsDetails.PasswordDetailsActivity
import com.pvsb.presentation.passwords.passwordsList.ComposePasswordCard
import com.pvsb.presentation.passwords.passwordsList.PasswordsListViewModel
import com.pvsb.presentation.utils.copyTextToClipBoard

@Composable
fun CategoriesFavoritePasswordsScreen(
    modifier: Modifier = Modifier,
    passwords: List<Password>,
    passwordsListViewModel: PasswordsListViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    Column(modifier = modifier) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(passwords) {
                ComposePasswordCard(
                    password = it,
                    onCardClick = { passwordId ->
                        navigateToDetails(passwordId, context)
                    }, onCopyPassword = { password ->
                        context.copyTextToClipBoard(password)
                    }, onFavoriteClick = { passwordId ->
                        passwordsListViewModel.toggleFavorite(passwordId)
                    }
                )
            }
        }
    }
}

private fun navigateToDetails(
    passwordId: String,
    context: Context
) {

    val intent = Intent(context, PasswordDetailsActivity::class.java)
    intent.putExtra(PasswordDetailsActivity.PASSWORD_ID_KEY, passwordId)

    startActivity(context, intent, null)
}

@Preview
@Composable
fun CategoriesFavoritePasswordsScreenPreview() {
    CategoriesFavoritePasswordsScreen(
        modifier = Modifier.fillMaxSize(),
        passwords = listOf(
            Password(
                "", "Home wifi", "123456", null, true, null
            ),
            Password(
                "", "Home wifi", "123456", null, true, null
            ),
            Password(
                "", "Home wifi", "123456", null, true, null
            ),
        )
    )
}