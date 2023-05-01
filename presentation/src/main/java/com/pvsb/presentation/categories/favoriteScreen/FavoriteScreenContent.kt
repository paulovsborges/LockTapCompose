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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pvsb.presentation.categories.favoriteScreen.contacts.CategoriesFavoriteContactsScreen
import com.pvsb.presentation.categories.favoriteScreen.passwords.CategoriesFavoritePasswordsScreen
import com.pvsb.presentation.ui.AppStyle.AppColors.background
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField
import com.pvsb.presentation.utils.components.viewPager.ComposeSecondaryViewPager
import com.pvsb.presentation.utils.components.viewPager.ViewPagerContentType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesFavoriteScreenContent(
    modifier: Modifier = Modifier,
    viewModel: CategoriesFavoritesViewModel = hiltViewModel()
) {

    val pagerState = rememberPagerState()
    val state = viewModel.state.collectAsState()
    viewModel.getFavoriteContent()

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
            pagerState.currentPage,
            state.value
        )
    }
}

@Composable
fun HandleSelectedPage(
    page: Int,
    state: CategoriesFavoritesScreenState
) {

    when (page) {
        0 -> {
            CategoriesFavoriteContactsScreen(contacts = state.contacts)
        }
        1 -> {
            CategoriesFavoritePasswordsScreen(passwords = state.passwords)
        }
        2 -> {
            CategoriesFavoriteContactsScreen(contacts = state.contacts)
        }
        else -> Unit
    }
}

@Preview
@Composable
fun FavoriteScreenContentPreview() {
    CategoriesFavoriteScreenContent(modifier = Modifier.fillMaxSize())
}
