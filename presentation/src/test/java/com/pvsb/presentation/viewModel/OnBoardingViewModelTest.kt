package com.pvsb.presentation.viewModel

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.User
import com.pvsb.domain.useCase.getUserData.GetUserDataUseCase
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import com.pvsb.presentation.onBoarding.OnBoardingScreens
import com.pvsb.presentation.onBoarding.OnBoardingViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
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
    private lateinit var getUserDataUseCase: GetUserDataUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        skipOnBoardingUseCase = spyk()
        getUserDataUseCase = mockk()
        viewModel = OnBoardingViewModel(skipOnBoardingUseCase, getUserDataUseCase)
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

    @Test
    fun `should set next destination to on boarding`(){

        val dummyUser = User(
            "123",
            false
        )

        coEvery { getUserDataUseCase() } returns DataState.Success(dummyUser)

        viewModel.resolveNextUsersDestinationFromSplash()

        assertEquals(OnBoardingScreens.OnBoarding, viewModel.state.value.nextDestination)
    }

    @Test
    fun `should set next destination to enter password`(){

        val dummyUser = User(
            "123",
            true
        )

        coEvery { getUserDataUseCase() } returns DataState.Success(dummyUser)

        viewModel.resolveNextUsersDestinationFromSplash()

        assertEquals(OnBoardingScreens.PasswordScreen.Enter, viewModel.state.value.nextDestination)

    }
}