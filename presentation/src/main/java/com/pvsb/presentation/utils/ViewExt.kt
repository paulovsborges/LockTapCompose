package com.pvsb.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun Int.fromPxToDp(): Int {
    val screenDpi = LocalConfiguration.current.densityDpi

    return this * (160 / screenDpi)
}
