package com.pvsb.presentation.passwords

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.pvsb.presentation.ui.AppStyle
import com.pvsb.presentation.ui.AppStyle.AppColors.background
import com.pvsb.presentation.utils.components.FloatingAddButton
import com.pvsb.presentation.utils.components.viewPager.ComposePrimaryViewPager
import com.pvsb.presentation.utils.components.viewPager.ViewPagerContentType

@Composable
fun PasswordsScreenContainer() {


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordsScreen() {

    val pagerState = rememberPagerState()

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

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp), contentAlignment = Alignment.BottomEnd
        ) {
            FloatingAddButton {

            }
        }
    }
}

@Preview
@Composable
fun PasswordsScreenPreview() {
    PasswordsScreen()
}