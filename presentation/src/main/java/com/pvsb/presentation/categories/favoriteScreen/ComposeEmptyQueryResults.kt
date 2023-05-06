package com.pvsb.presentation.categories.favoriteScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pvsb.presentation.R
import com.pvsb.presentation.ui.AppStyle

@Composable
fun ComposeEmptyQueryResults(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.empty_query_results_title),
            style = AppStyle.TextStyles.titleTextStyle
        )
        Text(
            text = stringResource(id = R.string.empty_query_results_message),
            style = AppStyle.TextStyles.messageTextStyle
        )
    }
}

@Preview
@Composable
private fun ComposeEmptyQueryResultsPreview() {
    ComposeEmptyQueryResults()
}
