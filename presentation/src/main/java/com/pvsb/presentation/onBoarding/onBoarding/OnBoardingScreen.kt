package com.pvsb.presentation.onBoarding.onBoarding

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pvsb.presentation.R
import com.pvsb.presentation.onBoarding.OnBoardingViewModel
import com.pvsb.presentation.ui.messageTextStyle
import com.pvsb.presentation.ui.theme.AppColors.lightBlue
import com.pvsb.presentation.ui.theme.AppColors.secondary
import com.pvsb.presentation.ui.titleTextStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
) {

    var selectedPage by remember { mutableStateOf(1) }
    var isLastPage by remember { mutableStateOf(false) }
    val onBoardingStepsData = getOnBoardingData()

    Box(
        contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()
    ) {

        val modalSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Expanded,
            confirmValueChange = { false },
            skipHalfExpanded = true
        )

        ComposeOnBoardingPrintsBackgroundBuilder(onBoardingStepsData[selectedPage - 1].drawables)

        ModalBottomSheetLayout(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            sheetContent = {
                BottomSheetContent(
                    selectedPage, onNextClicked = {

                        if (isLastPage) {
                            navigateToNextDestination(navController)
                            return@BottomSheetContent
                        }

                        selectedPage++

                        if (selectedPage == onBoardingStepsData.size) {
                            isLastPage = true
                        }
                    },
                    onSKipClicked = {
                        navigateToNextDestination(navController)
                        viewModel.skipOnBoarding()
                    },
                    modifier = Modifier.background(secondary)
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
    selectedPage: Int,
    onNextClicked: () -> Unit,
    onSKipClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val data = getOnBoardingData()[selectedPage - 1]

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                contentAlignment = Alignment.Center
            ) {
                PageIndicator(currentPage = selectedPage)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = data.title,
                style = titleTextStyle
            )

            Text(
                text = data.message,
                style = messageTextStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 38.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = onNextClicked,
                shape = RoundedCornerShape(corner = CornerSize(40.dp)),
                modifier = Modifier
                    .width(335.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = lightBlue
                )
            ) {
                Text(
                    text = stringResource(id = R.string.button_label_next),
                    fontFamily = FontFamily(
                        Font(
                            R.font.sf_pro_display_regular, weight = FontWeight.Thin
                        )
                    ),
                    color = Color.White
                )
            }

            Button(
                onClick = onSKipClicked,
                shape = RoundedCornerShape(corner = CornerSize(40.dp)),
                modifier = Modifier
                    .width(335.dp)
                    .height(52.dp)
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = secondary
                ),
                elevation = null
            ) {
                Text(
                    text = stringResource(id = R.string.button_label_skip),
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

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    totalPages: Int = 3,
    currentPage: Int = 2,
) {

    val notSelectedColor = Color(0, 0, 0, 50)
    val selectedColor = Color.White

    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)
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

private fun navigateToNextDestination(
    navController: NavController
) {
    navController.navigate(OnBoardingScreens.PasswordScreen.Create.route) {
        popUpTo(OnBoardingScreens.OnBoarding.route) { inclusive = true }
        launchSingleTop = true
    }
}
