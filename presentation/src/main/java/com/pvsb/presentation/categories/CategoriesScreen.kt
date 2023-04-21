package com.pvsb.presentation.categories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.presentation.mainBottomNav.MainScreens
import com.pvsb.presentation.categories.allScreen.AllScreenContent
import com.pvsb.presentation.categories.favoriteScreen.FavoriteScreenContent
import com.pvsb.presentation.ui.theme.AppColors.background
import com.pvsb.presentation.ui.titleTextStyle
import com.pvsb.presentation.utils.components.viewPager.ComposePrimaryViewPager
import com.pvsb.presentation.utils.components.viewPager.ViewPagerContentType

@ExperimentalFoundationApi
@Composable
fun CategoriesScreen(
    label: Int
) {

    val viePagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(start = 20.dp, end = 20.dp, top = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(id = label), style = titleTextStyle
        )

        Spacer(modifier = Modifier.height(20.dp))

        ComposePrimaryViewPager(
            state = viePagerState, modifier = Modifier.fillMaxWidth(),
            contents = listOf(
                ViewPagerContentType.All, ViewPagerContentType.Favorites
            )
        )

        when (viePagerState.currentPage) {
            0 -> {
                AllScreenContent(
                    modifier = Modifier.fillMaxSize()
                )
            }
            1 -> {
                FavoriteScreenContent(modifier = Modifier.fillMaxSize())
            }
            else -> Unit
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen(MainScreens.Categories.label)
}
