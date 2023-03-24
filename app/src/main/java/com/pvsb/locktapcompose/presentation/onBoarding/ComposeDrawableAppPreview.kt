package com.pvsb.locktapcompose.presentation.onBoarding.shared

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.R

@Composable
fun ComposeDrawableAppPreview(
    @DrawableRes drawable: Int,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier
            .background(color = colorResource(id = R.color.bg_splash)),
        border = BorderStroke(
            1.dp, colorResource(id = R.color.light_blue)
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

@Preview
@Composable
private fun PreviewComposeDrawableAppPreview() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        ComposeDrawableAppPreview(
            R.drawable.on_boarding_step_2_1,
            modifier = Modifier
                .width(160.dp)
                .height(310.dp)
        )
    }
}