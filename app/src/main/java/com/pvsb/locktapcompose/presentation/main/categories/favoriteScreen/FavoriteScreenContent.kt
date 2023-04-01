package com.pvsb.locktapcompose.presentation.main.categories.favoriteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.presentation.utils.components.searchFIeld.ComposePrimarySearchField
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.utils.components.viewPager.ComposeSecondaryViewPager
import com.pvsb.locktapcompose.presentation.utils.components.viewPager.ViewPagerContentType


@Composable
fun FavoriteScreenContent(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(background)
            .padding(horizontal = 20.dp, vertical = 25.dp)
    ) {
        ComposePrimarySearchField()

        Spacer(modifier = Modifier.height(16.dp))

        ComposeSecondaryViewPager(
            contents = listOf(
                ViewPagerContentType.Contacts,
                ViewPagerContentType.WifiPass,
                ViewPagerContentType.Photos
            )
        ) {

        }
    }
}

@Preview
@Composable
fun FavoriteScreenContentPreview() {
    FavoriteScreenContent(modifier = Modifier.fillMaxSize())
}
