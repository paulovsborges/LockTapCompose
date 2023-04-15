package com.pvsb.presentation.viewModel

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.entity.User
import com.pvsb.domain.useCase.getUserData.GetUserDataUseCase
import com.pvsb.domain.useCase.login.Login
import com.pvsb.domain.useCase.login.LoginUseCase
import com.pvsb.domain.useCase.registerPassword.RegisterPasswordUseCase
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import com.pvsb.presentation.R
import com.pvsb.presentation.onBoarding.onBoarding.OnBoardingScreens
import com.pvsb.presentation.onBoarding.OnBoardingViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
    private lateinit var registerPasswordUseCase: RegisterPasswordUseCase
    private lateinit var loginUseCase: LoginUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        skipOnBoardingUseCase = spyk()
        getUserDataUseCase = mockk()
        registerPasswordUseCase = mockk()
        loginUseCase = mockk()

        viewModel = OnBoardingViewModel(
            skipOnBoardingUseCase, getUserDataUseCase, registerPasswordUseCase, loginUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should call to skip on boarding`() {

        viewModel.skipOnBoarding()

        coVerify { skipOnBoardingUseCase() }
    }

    @Test
    fun `should call to register password`() {

        viewModel.registerNewPassword("123")

        coVerify { registerPasswordUseCase(any()) }
    }

    @Test
    fun `should set invalid password on login`() {

        coEvery { loginUseCase(any()) } returns DataState.Error(Login.Error.INVALID_PASSWORD)

        viewModel.login("123")

        val expectedResult = TypedMessage.Reference(R.string.password_incorrect_label)

        assertEquals(expectedResult, viewModel.state.value.error)
    }

    @Test
    fun `should set unknown error on login`() {

        coEvery { loginUseCase(any()) } returns DataState.Error(ExceptionWrapper.Unknown)

        viewModel.login("123")

        val expectedResult = TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)

        assertEquals(expectedResult, viewModel.state.value.error)
    }

    @Test
    fun `should set that user is authenticated`() {

        coEvery { loginUseCase(any()) } returns DataState.Success(Unit)

        viewModel.login("123")

        assertTrue(viewModel.state.value.isAuthenticated)
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
                        User("", false), OnBoardingScreens.OnBoarding
                    )
                ),
                arrayOf(
                    NextUserDestinationParameterizedInput(
                        User("123", false), OnBoardingScreens.PasswordScreen.Enter
                    )
                ),
                arrayOf(
                    NextUserDestinationParameterizedInput(
                        User("", true), OnBoardingScreens.PasswordScreen.Create
                    )
                ),
            )
        }

        @Test
        fun `should handle next user destination`() {

            coEvery { getUserDataUseCase() } returns DataState.Success(input.userData)

            viewModel.resolveNextUsersDestinationFromSplash()

            assertEquals(
                input.expectedNextDestination, viewModel.state.value.nextDestination
            )
        }

        data class NextUserDestinationParameterizedInput(
            val userData: User, val expectedNextDestination: OnBoardingScreens
        )
    }
}