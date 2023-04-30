package com.pvsb.presentation.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle.AppColors
import com.pvsb.presentation.ui.AppStyle.TextStyles.messageTextStyle
import com.pvsb.presentation.ui.AppStyle.TextStyles.titleTextStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComposeBottomSheetDialog(
    modifier: Modifier = Modifier,
    state: ModalBottomSheetState,
    title: TypedMessage? = null,
    message: TypedMessage? = null,
    positiveBtnLabel: Int = R.string.button_label_next,
    negativeBtnLabel: Int = R.string.button_label_skip,
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {}
) {

    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        modifier = modifier
            .fillMaxHeight(0.4f)
            .fillMaxWidth(),
        sheetContent = {
            BottomSheetContent(
                title = title,
                message = message,
                positiveBtnLabel = positiveBtnLabel,
                negativeBtnLabel = negativeBtnLabel,
                onPositiveClick = {
                    scope.launch {
                        onPositiveClick()
                        state.hide()
                    }
                },
                onNegativeClick = {
                    scope.launch {
                        onNegativeClick()
                        state.hide()
                    }
                }
            )
        },
        sheetState = state,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        scrimColor = Color.Transparent
    ) { }
}

@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
    title: TypedMessage? = null,
    message: TypedMessage? = null,
    positiveBtnLabel: Int = R.string.button_label_next,
    negativeBtnLabel: Int = R.string.button_label_skip,
    onPositiveClick: () -> Unit = {},
    onNegativeClick: () -> Unit = {}
) {

    val titleText = when (title) {
        is TypedMessage.Reference -> {
            stringResource(id = title.resId)
        }
        is TypedMessage.StringMessage -> {
            title.message
        }
        else -> {
            stringResource(id = R.string.bottom_sheet_generic_title)
        }
    }

    val messageText = when (message) {
        is TypedMessage.Reference -> {
            stringResource(id = message.resId)
        }
        is TypedMessage.StringMessage -> {
            message.message
        }
        else -> {
            stringResource(id = R.string.bottom_sheet_generic_message)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.secondary)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .height(4.dp)
                    .width(50.dp),
                backgroundColor = Color.White
            ) { }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = titleText,
                style = titleTextStyle
            )

            Text(
                text = messageText,
                style = messageTextStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 38.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onPositiveClick,
                shape = RoundedCornerShape(corner = CornerSize(40.dp)),
                modifier = Modifier
                    .width(335.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.lightBlue
                )
            ) {
                Text(
                    text = stringResource(id = positiveBtnLabel),
                    fontFamily = FontFamily(
                        Font(
                            R.font.sf_pro_display_regular, weight = FontWeight.Thin
                        )
                    ),
                    color = Color.White
                )
            }

            Button(
                onClick = onNegativeClick,
                shape = RoundedCornerShape(corner = CornerSize(40.dp)),
                modifier = Modifier
                    .width(335.dp)
                    .height(52.dp)
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppColors.secondary
                ),
                elevation = null
            ) {
                Text(
                    text = stringResource(id = negativeBtnLabel),
                    fontFamily = FontFamily(
                        Font(
                            R.font.sf_pro_display_regular, weight = FontWeight.Thin
                        )
                    ),
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun BottomSheetContentPreview() {
    BottomSheetContent(
        title = TypedMessage.StringMessage("Warning"),
        message = TypedMessage.StringMessage("This action can`t be undone")
    )
}
