package com.pvsb.presentation.passwords.categoriesPasswordsList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.domain.entity.Password
import com.pvsb.presentation.R
import com.pvsb.presentation.passwords.ComposeEmptyPasswordsListState
import com.pvsb.presentation.passwords.navigateToPasswordDetails
import com.pvsb.presentation.ui.AppStyle.AppColors
import com.pvsb.presentation.ui.AppStyle.TextStyles.titleTextStyle
import com.pvsb.presentation.utils.components.BackButton
import com.pvsb.presentation.utils.components.FloatingAddButton
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField
import com.pvsb.presentation.utils.copyTextToClipBoard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordsListActivity : ComponentActivity() {

    private val viewModel: PasswordsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val state = viewModel.state.collectAsState()
            viewModel.getPasswords()

            ComposeContent(state.value)
        }
    }

    @Composable
    private fun ComposeContent(
        state: PasswordsListState = PasswordsListState()
    ) {

        val passwords = state.allPasswords

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background)
        ) {

            Column {
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
                        text = stringResource(id = R.string.session_option_label_passwords),
                        style = titleTextStyle
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    ComposePrimarySearchField()

                    Spacer(modifier = Modifier.height(20.dp))

                    if (passwords.isEmpty()) {
                        ComposeEmptyPasswordsListState(modifier = Modifier.fillMaxSize())
                    } else {
                        ComposePasswordsList(passwords = passwords)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(25.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingAddButton {
                    navigateToPasswordDetails()
                }
            }
        }
    }

    @Composable
    private fun ComposePasswordsList(
        modifier: Modifier = Modifier,
        passwords: List<Password>
    ) {

        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(passwords) {
                    ComposePasswordCard(password = it, onCardClick = { passwordId ->
                        navigateToPasswordDetails(passwordId)
                    }, onCopyPassword = { password ->
                        copyTextToClipBoard(password)
                    }, onFavoriteClick = { passwordId ->
                        viewModel.toggleFavorite(passwordId)
                    })
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ComposeContentPreview() {
        ComposeContent(
            PasswordsListState(
                allPasswords = listOf(
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
        )
    }
}
