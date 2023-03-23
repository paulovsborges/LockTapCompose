package com.pvsb.locktapcompose.presentation.mainScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.R

@Composable
fun MainContent() {

    Surface(
        modifier = Modifier
            .background(color = colorResource(id = R.color.bg_splash))
            .fillMaxSize(),
        border = BorderStroke(1.dp, colorResource(id = R.color.light_blue)),
        shape = RoundedCornerShape(30.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Main screen")

            Image(
                painter = painterResource(id = R.drawable.create_password),
                contentDescription = ""
            )
        }
    }
}