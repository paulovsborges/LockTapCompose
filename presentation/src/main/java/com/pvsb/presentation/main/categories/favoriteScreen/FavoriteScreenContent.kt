package com.pvsb.presentation.main.categories.favoriteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.presentation.ui.theme.AppColors.background
import com.pvsb.presentation.utils.components.textField.ComposePrimarySearchField
import com.pvsb.presentation.utils.components.viewPager.ComposeSecondaryViewPager
import com.pvsb.presentation.utils.components.viewPager.ViewPagerContentType

@Composable
fun FavoriteScreenContent(
    modifier: Modifier = Modifier
) {

    var currentPage by remember { mutableStateOf<ViewPagerContentType>(ViewPagerContentType.Contacts) }

    Column(
        modifier = modifier
            .background(background)
            .padding(vertical = 25.dp)
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
            currentPage = it
        }

        HandleSelectedPage(currentPage)
    }
}

@Composable
fun HandleSelectedPage(
    type: ViewPagerContentType
) {

    CategoriesFavoriteContactsScreen()

//    when(type){
//        ViewPagerContentType.Contacts -> TODO()
//        ViewPagerContentType.Photos -> TODO()
//        ViewPagerContentType.WifiPass -> TODO()
//        else -> Unit
//    }
}

@Preview
@Composable
fun FavoriteScreenContentPreview() {
    FavoriteScreenContent(modifier = Modifier.fillMaxSize())
}
