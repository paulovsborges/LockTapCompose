package com.pvsb.presentation.onBoarding.onBoarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pvsb.presentation.ui.theme.AppColors.background
import com.pvsb.presentation.ui.theme.AppColors.lightBlue

@Composable
fun ComposeDrawableAppPrint(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .background(color = background),
        border = BorderStroke(
            1.dp, lightBlue
        ),
        shape = RoundedCornerShape(20.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Image(
                painter = painterResource(id = drawable),
                contentDescription = ""
            )
        }
    }
}
