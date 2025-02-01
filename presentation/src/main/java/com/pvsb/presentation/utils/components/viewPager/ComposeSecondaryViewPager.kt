package com.pvsb.presentation.utils.components.viewPager

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
import androidx.compose.foundation.pager.PagerState
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
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle.AppColors.background
import com.pvsb.presentation.ui.AppStyle.AppColors.gray
import com.pvsb.presentation.ui.AppStyle.AppColors.lightBlue
import kotlinx.coroutines.launch

@Composable
fun ComposeSecondaryViewPager(
    modifier: Modifier = Modifier,
    contents: List<ViewPagerContentType> = emptyList(),
    state: PagerState = rememberPagerState(
        pageCount = {
            contents.size
        }
    ),
) {

    val scope = rememberCoroutineScope()

    HorizontalPager(modifier = modifier, state = state) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            contents.forEachIndexed { currentPos, type ->

                val isSelectedTab = state.currentPage == currentPos

                ComposeTab(
                    label = type.label,
                    isSelected = isSelectedTab,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    scope.launch {
                        state.scrollToPage(currentPos)
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
private fun ComposeTabPreview() {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.background(background)
    ) {
        ComposeTab(
            label = ViewPagerContentType.Contacts.label,
            isSelected = true
        )

        ComposeTab(label = ViewPagerContentType.Passwords.label)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun ComposeSecondaryViewPagerPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        ComposeSecondaryViewPager(
            contents = listOf(
                ViewPagerContentType.Contacts,
                ViewPagerContentType.Passwords,
                ViewPagerContentType.Photos
            )
        )
    }
}
