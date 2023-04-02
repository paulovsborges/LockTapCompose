package com.pvsb.presentation.utils.components.viewPager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.theme.AppColors
import com.pvsb.presentation.ui.theme.AppColors.lightBlue
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun ComposePrimaryViewPager(
    modifier: Modifier = Modifier,
    state: PagerState = rememberPagerState(),
    contents: List<ViewPagerContentType> = emptyList(),
    contentPage: (ViewPagerContentType) -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    HorizontalPager(pageCount = contents.size, modifier = modifier, state = state) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            repeat(contents.size) { currentPosition ->

                val isSelectedTab = state.currentPage == currentPosition

                val backgroundColor = if (isSelectedTab) lightBlue
                else Color.Transparent

                val labelColor = if (isSelectedTab) Color.White else AppColors.gray

                Button(
                    onClick = {
                        scope.launch {
                            state.scrollToPage(currentPosition)
                            contentPage(contents[currentPosition])
                        }
                    },
                    shape = RoundedCornerShape(corner = CornerSize(40.dp)),
                    modifier = Modifier
                        .width(168.dp)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = backgroundColor
                    ),
                    elevation = null
                ) {
                    Text(
                        text = stringResource(id = contents[currentPosition].label),
                        fontFamily = FontFamily(
                            Font(
                                R.font.sf_pro_display_regular, weight = FontWeight.Thin
                            )
                        ),
                        color = labelColor
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
private fun ComposePrimaryViewPagerPreview() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        ComposePrimaryViewPager(
            contents = listOf(
                ViewPagerContentType.All, ViewPagerContentType.Favorites
            )
        )
    }
}
