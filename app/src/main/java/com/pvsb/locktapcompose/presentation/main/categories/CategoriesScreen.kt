package com.pvsb.locktapcompose.presentation.main.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.main.MainScreens
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.ui.titleTextStyle

@Composable
fun CategoriesScreen(
    label: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(start = 20.dp, end = 20.dp, top = 15.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = stringResource(id = label),
            style = titleTextStyle
        )

    }
}

@Composable
fun ComposeViewPager(
    modifier: Modifier = Modifier
) {




}

@Preview
@Composable
private fun ComposeViewPagerPreview() {
    ComposeViewPager()
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen(MainScreens.Categories.label)
}