package com.pvsb.presentation.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pvsb.presentation.R

object AppStyle {
    object TextStyles {
        val titleTextStyle = TextStyle(
            fontFamily = FontFamily(
                Font(
                    R.font.sf_pro_display_medium, weight = FontWeight.SemiBold
                )
            ),
            color = Color.White,
            fontSize = 24.sp
        )

        val messageTextStyle = TextStyle(
            fontFamily = FontFamily(
                Font(
                    R.font.sf_pro_display_regular, weight = FontWeight.Thin
                )
            ),
            color = AppColors.gray,
        )
    }

    object AppColors {
        val gray = Color(153, 153, 153)
        val lightBlue = Color(0, 184, 238)
        val secondary = Color(35, 41, 56)
        val red = Color(247, 72, 67)
        val background = Color(16, 23, 39)
        val translucent = Color(255, 255, 255, 51)
    }
}
