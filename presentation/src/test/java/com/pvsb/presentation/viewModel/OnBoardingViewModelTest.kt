package com.pvsb.presentation.viewModel

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.User
import com.pvsb.domain.useCase.getUserData.GetUserDataUseCase
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import com.pvsb.presentation.onBoarding.onBoarding.OnBoardingScreens
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
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@ExperimentalCoroutinesApi
open class OnBoardingViewModelTest {

    protected lateinit var viewModel: OnBoardingViewModel
    protected lateinit var skipOnBoardingUseCase: SkipOnBoardingUseCase
    protected lateinit var getUserDataUseCase: GetUserDataUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        skipOnBoardingUseCase = spyk()
        getUserDataUseCase = mockk()
        viewModel = OnBoardingViewModel(skipOnBoardingUseCase, getUserDataUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should call skipOnBoardingUseCase when skipOnBoarding is called`() {

        viewModel.skipOnBoarding()

        coVerify { skipOnBoardingUseCase() }
    }

    @RunWith(Parameterized::class)
    class NextUserDestinationParameterizedTest(
        private val input: NextUserDestinationParameterizedInput
    ) : OnBoardingViewModelTest() {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data() = listOf(
                arrayOf(
                    NextUserDestinationParameterizedInput(
                        User("", false),
                        OnBoardingScreens.OnBoarding
                    )
                ),
                arrayOf(
                    NextUserDestinationParameterizedInput(
                        User("123", false),
                        OnBoardingScreens.PasswordScreen.Enter
                    )
                ),
                arrayOf(
                    NextUserDestinationParameterizedInput(
                        User("", true),
                        OnBoardingScreens.PasswordScreen.Create
                    )
                ),
            )
        }

        @Test
        fun `should handle next user destination`() {

            coEvery { getUserDataUseCase() } returns DataState.Success(input.userData)

            viewModel.resolveNextUsersDestinationFromSplash()

            assertEquals(
                input.expectedNextDestination,
                viewModel.state.value.nextDestination
            )
        }

        data class NextUserDestinationParameterizedInput(
            val userData: User,
            val expectedNextDestination: OnBoardingScreens
        )
    }
}