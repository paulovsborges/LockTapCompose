package com.pvsb.presentation.categories.favoriteScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pvsb.presentation.categories.favoriteScreen.contacts.CategoriesFavoriteContactsScreen
import com.pvsb.presentation.categories.favoriteScreen.passwords.CategoriesFavoritePasswordsScreen
import com.pvsb.presentation.categories.favoriteScreen.photos.CategoriesFavoritePhotosScreen
import com.pvsb.presentation.ui.AppStyle.AppColors.background
import com.pvsb.presentation.utils.ComposableLifecycleListener
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField
import com.pvsb.presentation.utils.components.viewPager.ComposeSecondaryViewPager
import com.pvsb.presentation.utils.components.viewPager.ViewPagerContentType

private data class FavoriteScreenContentAction(
    val state: CategoriesFavoritesScreenState = CategoriesFavoritesScreenState(),
    val onPasswordFavoriteClick: (String) -> Unit = {}
)

@Composable
fun CategoriesFavoriteScreenContentContainer(
    modifier: Modifier = Modifier,
    viewModel: CategoriesFavoritesViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    ComposableLifecycleListener(onResume = {
        viewModel.getFavoriteContent()
    })

    val actions = FavoriteScreenContentAction(
        state = state.value,
        onPasswordFavoriteClick = { passwordId ->
            viewModel.toggleFavorite(passwordId)
        }
    )

    CategoriesFavoriteScreenContent(modifier = modifier, actions = actions)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategoriesFavoriteScreenContent(
    modifier: Modifier = Modifier,
    actions: FavoriteScreenContentAction,
) {

    val pagerState = rememberPagerState(
        pageCount = {
            3
        }
    )

    Column(
        modifier = modifier
            .background(background)
            .padding(vertical = 25.dp)
    ) {
        ComposePrimarySearchField()

        Spacer(modifier = Modifier.height(16.dp))

        ComposeSecondaryViewPager(
            state = pagerState,
            contents = listOf(
                ViewPagerContentType.Contacts,
                ViewPagerContentType.Passwords,
                ViewPagerContentType.Photos
            )
        )

        HandleSelectedPage(
            page = pagerState.currentPage,
            state = actions.state,
            onPasswordFavoriteClick = { passwordId ->
                actions.onPasswordFavoriteClick(passwordId)
            }
        )
    }
}

@Composable
private fun HandleSelectedPage(
    page: Int,
    state: CategoriesFavoritesScreenState,
    onPasswordFavoriteClick: (String) -> Unit
) {

    when (page) {
        0 -> {
            CategoriesFavoriteContactsScreen(contacts = state.contacts)
        }

        1 -> {
            CategoriesFavoritePasswordsScreen(
                passwords = state.passwords,
                onFavoriteClick = {
                    onPasswordFavoriteClick(it)
                }
            )
        }

        2 -> {
            CategoriesFavoritePhotosScreen(photos = state.photos)
        }

        else -> Unit
    }
}

@Preview
@Composable
private fun FavoriteScreenContentPreview() {
    CategoriesFavoriteScreenContent(
        modifier = Modifier.fillMaxSize(),
        actions = FavoriteScreenContentAction()
    )
}
