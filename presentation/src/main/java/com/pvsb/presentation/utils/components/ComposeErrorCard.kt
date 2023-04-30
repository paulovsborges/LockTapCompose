package com.pvsb.presentation.utils.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle.AppColors

@Composable
fun ComposeErrorCard(
    modifier: Modifier = Modifier,
    isErrorVisible: Boolean = false,
    error: TypedMessage? = null
) {

    val textError = when (error) {
        is TypedMessage.Reference -> {
            stringResource(id = error.resId)
        }
        is TypedMessage.StringMessage -> {
            error.message
        }
        else -> {
            stringResource(id = R.string.error_there_was_an_unexpected_error)
        }
    }

    val density = LocalDensity.current
    AnimatedVisibility(
        visible = isErrorVisible,
        enter = slideInVertically {
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(5.dp),
            color = AppColors.secondary
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(painter = painterResource(id = R.drawable.ic_error), "", tint = AppColors.red)

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = textError,
                    fontFamily = FontFamily(Font(R.font.sf_pro_display_regular)),
                    color = AppColors.red,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComposeErrorCardPreview() {
    ComposeErrorCard(modifier = Modifier.padding(20.dp), true)
}
