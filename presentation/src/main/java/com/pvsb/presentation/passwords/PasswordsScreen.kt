package com.pvsb.presentation.passwords

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.categories.favoriteScreen.ComposeEmptyQueryResults
import com.pvsb.presentation.mainBottomNav.MainScreens
import com.pvsb.presentation.passwords.categoriesPasswordsList.ComposePasswordCard
import com.pvsb.presentation.passwords.categoriesPasswordsList.PasswordsListViewModel
import com.pvsb.presentation.ui.AppStyle
import com.pvsb.presentation.ui.AppStyle.AppColors.background
import com.pvsb.presentation.utils.components.FloatingAddButton
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField
import com.pvsb.presentation.utils.components.viewPager.ComposePrimaryViewPager
import com.pvsb.presentation.utils.components.viewPager.ViewPagerContentType
import com.pvsb.presentation.utils.copyTextToClipBoard

private data class PasswordsScreenActions(
    val allPasswords: List<Password> = listOf(),
    val favoritePasswords: List<Password> = listOf(),
    val error: TypedMessage? = null,
    val onPasswordFavoriteClick: (String) -> Unit = {}
)

@Composable
fun PasswordsScreenContainer(
    viewModel: PasswordsListViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    viewModel.getPasswords()

    PasswordsScreen(
        PasswordsScreenActions(allPasswords = state.value.allPasswords,
            favoritePasswords = state.value.favoritePasswords,
            error = state.value.error,
            onPasswordFavoriteClick = { passwordId ->
                viewModel.toggleFavorite(passwordId)
            })
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PasswordsScreen(
    actions: PasswordsScreenActions
) {

    val pagerState = rememberPagerState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
    ) {

        Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 40.dp)) {
            Text(
                text = stringResource(id = MainScreens.Passwords.label),
                style = AppStyle.TextStyles.titleTextStyle
            )

            Spacer(modifier = Modifier.height(20.dp))

            ComposePrimaryViewPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState,
                contents = listOf(
                    ViewPagerContentType.All,
                    ViewPagerContentType.Favorites,
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            ComposePrimarySearchField()

            Spacer(modifier = Modifier.height(20.dp))

            ComposeContent(
                viewPagerPos = pagerState.currentPage,
                context = context,
                actions = actions
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp), contentAlignment = Alignment.BottomEnd
        ) {
            FloatingAddButton {
                context.navigateToPasswordDetails()
            }
        }
    }
}

@Composable
private fun ComposeContent(
    viewPagerPos: Int,
    actions: PasswordsScreenActions,
    context: Context
) {

    when (viewPagerPos) {
        0 -> {
            ComposeAllPasswordsList(
                actions = actions, context = context
            )
        }
        1 -> {
            ComposeFavoritePasswordsList(
                actions = actions, context = context
            )
        }
        else -> Unit
    }
}

@Composable
private fun ComposeAllPasswordsList(
    actions: PasswordsScreenActions, context: Context
) {
    if (actions.allPasswords.isEmpty()) {
        ComposeEmptyPasswordsListState(modifier = Modifier.fillMaxSize())
    } else {
        ComposePasswordsList(passwords = actions.allPasswords,
            context = context,
            onPasswordFavoriteClick = { passwordId ->
                actions.onPasswordFavoriteClick(passwordId)
            })
    }
}

@Composable
private fun ComposeFavoritePasswordsList(
    actions: PasswordsScreenActions, context: Context
) {
    if (actions.favoritePasswords.isEmpty()) {
        ComposeEmptyQueryResults(modifier = Modifier.fillMaxSize())
    } else {
        ComposePasswordsList(passwords = actions.favoritePasswords,
            context = context,
            onPasswordFavoriteClick = { passwordId ->
                actions.onPasswordFavoriteClick(passwordId)
            })
    }
}

@Composable
private fun ComposePasswordsList(
    modifier: Modifier = Modifier,
    passwords: List<Password>,
    context: Context,
    onPasswordFavoriteClick: (String) -> Unit
) {

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(passwords) {
                ComposePasswordCard(password = it, onCardClick = { passwordId ->
                    context.navigateToPasswordDetails(passwordId)
                }, onCopyPassword = { password ->
                    context.copyTextToClipBoard(password)
                }, onFavoriteClick = { passwordId ->
                    onPasswordFavoriteClick(passwordId)
                })
            }
        }
    }
}

@Preview
@Composable
private fun PasswordsScreenPreview() {
    PasswordsScreen(
        PasswordsScreenActions(
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