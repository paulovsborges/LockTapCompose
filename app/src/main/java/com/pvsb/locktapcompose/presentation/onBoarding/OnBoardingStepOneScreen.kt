package com.pvsb.locktapcompose.presentation.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvsb.locktapcompose.R
import com.pvsb.locktapcompose.presentation.onBoarding.shared.ComposeOnBoardingPrintsBackgroundBuilder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnBoardingStepOneScreen() {

    Box(
        contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()
    ) {

        val drawables = listOf(
            R.drawable.on_boarding_step_1_1,
            R.drawable.on_boarding_step_1_2,
            R.drawable.on_boarding_step_1_3,
            R.drawable.on_boarding_step_1_4,
        )

        val modalSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Expanded,
            confirmStateChange = { false },
            skipHalfExpanded = true
        )

        ComposeOnBoardingPrintsBackgroundBuilder(drawables)

        ModalBottomSheetLayout(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            sheetContent = {
                BottomSheetContent(
                    stringResource(id = R.string.on_boarding_step_1_title),
                    stringResource(id = R.string.on_boarding_step_1_message),
                    modifier = Modifier.background(colorResource(id = R.color.bg_secondary))
                )
            },
            sheetState = modalSheetState,
            sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            scrimColor = Color.Transparent
        ) {}
    }
}

@Composable
private fun BottomSheetContent(
    title: String,
    message: String,
    modifier: Modifier = Modifier
) {

    var selectedPage by remember { mutableStateOf(1) }

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                contentAlignment = Alignment.Center
            ) {
                PageIndicator(currentPage = selectedPage)
            }

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = title,
                fontFamily = FontFamily(
                    Font(
                        R.font.sf_pro_display_medium,
                        weight = FontWeight.SemiBold
                    )
                ),
                color = Color.White,
                fontSize = 24.sp
            )

            Text(
                text = message,
                fontFamily = FontFamily(
                    Font(
                        R.font.sf_pro_display_regular,
                        weight = FontWeight.Thin
                    )
                ),
                color = colorResource(id = R.color.gray),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 38.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    selectedPage++
                },
                shape = RoundedCornerShape(corner = CornerSize(40.dp)),
                modifier = Modifier
                    .width(335.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.light_blue)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.button_label_next),
                    fontFamily = FontFamily(
                        Font(
                            R.font.sf_pro_display_regular,
                            weight = FontWeight.Thin
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
        stringResource(id = R.string.on_boarding_step_1_title),
        stringResource(id = R.string.on_boarding_step_1_message)
    )
}

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    totalPages: Int = 3,
    currentPage: Int = 2,
) {

    val notSelectedColor = Color(0, 0, 0, 50)
    val selectedColor = Color.White

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        for (page in 1..totalPages) {

            val backgroundColor = if (page == currentPage) {
                selectedColor
            } else notSelectedColor

            Card(
                modifier = modifier
                    .height(4.dp)
                    .width(24.dp),
                shape = RoundedCornerShape(5f),
                backgroundColor = backgroundColor,
                contentColor = backgroundColor
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(backgroundColor)
                )
            }
        }
    }
}