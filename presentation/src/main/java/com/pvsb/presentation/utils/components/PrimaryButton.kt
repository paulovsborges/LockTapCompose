package com.pvsb.presentation.utils.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle.AppColors

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isEnabled: Boolean = false
) {

    val backgroundColor = if (isEnabled) AppColors.lightBlue else AppColors.secondary
    val textColor = if (isEnabled) Color.White else AppColors.gray

    Button(
        onClick = {
            if (isEnabled) {
                onClick()
            }
        },
        shape = RoundedCornerShape(corner = CornerSize(40.dp)),
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        elevation = null
    ) {
        Text(
            text = stringResource(id = R.string.contact_details_save_contact),
            fontFamily = FontFamily(
                Font(
                    R.font.sf_pro_display_regular, weight = FontWeight.Thin
                )
            ),
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryButtonPreview() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PrimaryButton(isEnabled = true)
        PrimaryButton()
    }
}
