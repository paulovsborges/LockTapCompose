package com.pvsb.locktapcompose.presentation.main.shared.searchFIeld

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors
import com.pvsb.locktapcompose.presentation.ui.theme.AppColors.gray

@Composable
fun ComposeSearchField(
    modifier: Modifier = Modifier,
    text: String = "",
    onValueChanged: (String) -> Unit = {}
) {

    val placeHolderText = stringResource(id = R.string.search_field_placeholder_enter_a_title)
    val textToDisplay = text.ifEmpty { placeHolderText }
    val textColor = if (text.isEmpty()) gray else Color.White

    BasicTextField(
        modifier = modifier.height(44.dp),
        value = text,
        onValueChange = onValueChanged,
        decorationBox = {

            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = AppColors.secondary,
                shape = RoundedCornerShape(5.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize().padding(start = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "",
                        tint = Color.White
                    )

                    Text(text = textToDisplay, color = textColor)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun ComposeSearchFieldPreview() {
    Box(modifier = Modifier.padding(10.dp)) {
        ComposeSearchField(modifier = Modifier.fillMaxWidth(), text = "")
    }
}