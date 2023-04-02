package com.pvsb.locktapcompose.presentation.utils.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.R

@Composable
fun BackButton(
    onClick: () -> Unit = {}
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        elevation = null, onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Sharp.ArrowBack, contentDescription = "", tint = Color.White
            )
        }

        Text(
            text = stringResource(id = R.string.button_label_back),
            color = Color.White,
            modifier = Modifier.padding(start = 10.dp),
            fontFamily = FontFamily(Font(R.font.sf_pro_display_regular))
        )
    }
}

@Preview
@Composable
private fun BackButtonPreview() {
    BackButton()
}
