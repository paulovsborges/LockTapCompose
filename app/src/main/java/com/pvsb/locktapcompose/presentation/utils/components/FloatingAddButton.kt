package com.pvsb.locktapcompose.presentation.utils.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.lightBlue

@Composable
fun FloatingAddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {

    Card(
        shape = CircleShape,
        modifier = modifier
            .size(55.dp)
            .clickable {
                onClick()
            },
        backgroundColor = lightBlue,
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun FloatingAddButtonPreview() {
    FloatingAddButton()
}