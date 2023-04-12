package com.pvsb.presentation.onBoarding.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pvsb.presentation.R
import com.pvsb.presentation.onBoarding.OnBoardingScreens
import com.pvsb.presentation.onBoarding.OnBoardingViewModel
import com.pvsb.presentation.ui.theme.AppColors.background
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = hiltViewModel()
    ) {

    var countDown by remember { mutableStateOf(5) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_lock_tap),
            contentDescription = ""
        )
    }

    LaunchedEffect(key1 = countDown) {
        while (countDown > 0) {
            delay(1_000)
            countDown--
        }
        navController.navigate(OnBoardingScreens.OnBoarding.route) {
            popUpTo(OnBoardingScreens.SplashScree.route) { inclusive = true }
            launchSingleTop = true
        }
    }
}
