package com.pvsb.locktapcompose.presentation.utils.components.viewPager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.background
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.gray
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.lightBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComposeSecondaryViewPager(
    modifier: Modifier = Modifier,
    contents: List<ViewPagerContentType> = emptyList(),
    initialPage: Int = 0,
    contentPage: (ViewPagerContentType) -> Unit = {}
) {

    val state = rememberPagerState(initialPage)
    val scope = rememberCoroutineScope()

    HorizontalPager(pageCount = contents.size, modifier = modifier, state = state) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            contents.forEachIndexed { currentPos, type ->

                val isSelectedTab = state.currentPage == currentPos

                ComposeTab(label = type.label, isSelected = isSelectedTab) {
                    scope.launch {
                        state.scrollToPage(currentPos)
                        contentPage(type)
                    }
                }
            }
        }
    }
}

@Composable
private fun ComposeTab(
    modifier: Modifier = Modifier,
    label: Int = 0,
    isSelected: Boolean = false,
    onTabClicked: () -> Unit = {}
) {

    val textColor = if (isSelected) lightBlue else gray
    val indicatorColor = if (isSelected) lightBlue else Color.Transparent

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(112.dp)
            .clickable {
                onTabClicked()
            }
    ) {

        Text(
            text = stringResource(id = label),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
            color = textColor, textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(indicatorColor)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeTabPreview() {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(background)
    ) {
        ComposeTab(
            label = ViewPagerContentType.Contacts.label,
            isSelected = true
        )

        ComposeTab(label = ViewPagerContentType.WifiPass.label)
    }
}

@Preview
@Composable
fun ComposeSecondaryViewPagerPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        ComposeSecondaryViewPager(
            contents = listOf(
                ViewPagerContentType.Contacts,
                ViewPagerContentType.WifiPass,
                ViewPagerContentType.Photos
            ),
            initialPage = 1
        )
    }
}
