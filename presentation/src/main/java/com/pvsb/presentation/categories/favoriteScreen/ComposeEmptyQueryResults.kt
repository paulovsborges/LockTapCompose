package com.pvsb.presentation.categories.favoriteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pvsb.presentation.ui.AppStyle

@Composable
fun ComposeEmptyQueryResults(modifier: Modifier = Modifier) {

//    stringResource(id = R.string.empty_query_results_title)

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Oops... ", style = AppStyle.TextStyles.titleTextStyle)
        Text(text = "No results were found for your query", style = AppStyle.TextStyles.messageTextStyle)
    }
}

@Preview
@Composable
fun ComposeEmptyQueryResultsPreview() {
    ComposeEmptyQueryResults()
}
