package com.pvsb.locktapcompose.presentation.utils.components.textField

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors

@Composable
fun ComposeContactInfoTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    fieldLabel: Int = 0,
    onValueChanged: (String) -> Unit = {}
) {

    val placeHolderText = stringResource(id = R.string.search_field_placeholder_enter_a_title)
    val textToDisplay = text.ifEmpty { placeHolderText }
    val textColor = if (text.isEmpty()) AppColors.gray else Color.White
    var isTextFieldFocused by remember { mutableStateOf(false) }
    val borderColor = if (isTextFieldFocused) AppColors.lightBlue else Color.Transparent

    BasicTextField(
        modifier = modifier
            .height(50.dp)
            .onFocusChanged {
                isTextFieldFocused = it.isFocused
            },
        value = text,
        onValueChange = {

            val t = TextFieldValue(
                text, TextRange(
                    it.length
                )
            )
            t.text

            onValueChanged(it)
        },
        decorationBox = {

            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = AppColors.secondary,
                shape = RoundedCornerShape(30.dp),
                border = BorderStroke(1.dp, color = borderColor)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        4.dp,
                        alignment = { _, space ->
                            space / 2
                        })
                ) {

                    Text(
                        text = stringResource(id = fieldLabel),
                        color = AppColors.gray,
                        fontSize = 10.sp
                    )

                    Text(text = textToDisplay, color = textColor, fontSize = 14.sp)
                }
            }
        }, cursorBrush = SolidColor(Color.White)
    )
}

@Preview(showBackground = true)
@Composable
private fun ComposeContactInfoTextFieldPreview() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        ComposeContactInfoTextField(
            fieldLabel = R.string.contact_details_text_field_first_last_name,
            text = "john doe"
        )
    }
}