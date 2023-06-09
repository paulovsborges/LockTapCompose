package com.pvsb.presentation.onBoarding.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pvsb.presentation.ui.AppStyle.AppColors.background

@Composable
fun ComposeOnBoardingPrintsBackgroundBuilder(drawables: List<Int>, modifier: Modifier = Modifier) {

    val composeDrawableAppPreviewModifier = Modifier
        .width(160.dp)
        .height(310.dp)

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(background),
        horizontalArrangement = Arrangement.spacedBy(
            30.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {

        Row(verticalAlignment = { _, _ -> 50 }) {

            Column(verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)) {
                ComposeDrawableAppPrint(
                    drawables[0],
                    modifier = composeDrawableAppPreviewModifier
                )

                ComposeDrawableAppPrint(
                    drawables[1],
                    modifier = composeDrawableAppPreviewModifier
                )
            }
        }

        Row(verticalAlignment = { _, _ -> -400 }) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)) {
                ComposeDrawableAppPrint(
                    drawables[2],
                    modifier = composeDrawableAppPreviewModifier
                )

                ComposeDrawableAppPrint(
                    drawables[3],
                    modifier = composeDrawableAppPreviewModifier
                )
            }
        }
    }
}
