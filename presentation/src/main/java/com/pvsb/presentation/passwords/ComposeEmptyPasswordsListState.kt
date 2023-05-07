package com.pvsb.presentation.passwords

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle

@Composable
fun ComposeEmptyPasswordsListState(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_empty_content), contentDescription = ""
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = R.string.empty_content_label),
            style = AppStyle.TextStyles.titleTextStyle,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.wifi_passwords_empty_content_message),
            color = AppStyle.AppColors.gray,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            style = AppStyle.TextStyles.messageTextStyle
        )
    }
}
