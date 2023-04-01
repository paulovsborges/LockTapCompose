package com.pvsb.locktapcompose.presentation.utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.gray
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.secondary

@Composable
fun ComposeContactCell(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .height(55.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            backgroundColor = secondary,
            shape = CircleShape,
            modifier = Modifier.size(45.dp)
        ) {
            ComposeContactImage()
        }

        Spacer(modifier = Modifier.width(12.dp))

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    alignment = { _, space ->
                        space / 2
                    })
            ) {
                Text(text = "Richard", color = Color.White)
                Text(text = "347-671-1254", color = gray)
            }

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {


                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "",
                    tint = gray
                )
            }
        }
    }
}

@Composable
fun ComposeContactImagePlaceholder(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "R",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
            color = Color.White
        )
    }
}

@Composable
fun ComposeContactImage(
    modifier: Modifier = Modifier
) {

    val url =
        "https://images.unsplash.com/photo-1551887373-3c5bd224f6e2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Y3JhenklMjBkb2d8ZW58MHx8MHx8&w=1000&q=80"

    Image(
        painter = rememberAsyncImagePainter(model = url),
        contentDescription = "",
        modifier = modifier
    )
}

@Preview
@Composable
fun ComposeContactCellPreview() {
    ComposeContactCell()
}