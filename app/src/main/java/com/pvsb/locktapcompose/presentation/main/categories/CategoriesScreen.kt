package com.pvsb.locktapcompose.presentation.main.categories

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.main.MainScreens
import com.pvsb.locktapcompose.presentation.main.shared.viewPager.ComposeViewPager
import com.pvsb.locktapcompose.presentation.main.shared.viewPager.ViewPagerContentType
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
                AllScreenContent(modifier = Modifier.fillMaxSize())
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

    Box(modifier = modifier.background(Color.Red), contentAlignment = Alignment.Center) {
        Text(text = "all content")
    }
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