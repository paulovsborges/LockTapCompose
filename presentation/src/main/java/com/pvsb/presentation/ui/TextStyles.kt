package com.pvsb.presentation.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.theme.AppColors.gray

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
    color = gray,
)
