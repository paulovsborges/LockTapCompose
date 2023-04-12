package com.pvsb.presentation.viewModel

import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import com.pvsb.presentation.onBoarding.OnBoardingViewModel
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class OnBoardingViewModelTest {

    private lateinit var viewModel: OnBoardingViewModel
    private lateinit var skipOnBoardingUseCase: SkipOnBoardingUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        skipOnBoardingUseCase = spyk()
        viewModel = OnBoardingViewModel(skipOnBoardingUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `should call skipOnBoardingUseCase when skipOnBoarding is called`() {

        viewModel.skipOnBoarding()

        coVerify { skipOnBoardingUseCase() }
    }
}