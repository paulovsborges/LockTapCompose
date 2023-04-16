package com.pvsb.presentation.viewModel.passwords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.password.getPassword.GetPasswordUseCase
import com.pvsb.presentation.R
import com.pvsb.presentation.passwords.passwordsDetails.PasswordDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PasswordDetailsViewModelTest {

    private lateinit var viewModel: PasswordDetailsViewModel
    private lateinit var getPassword: GetPasswordUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getPassword = mockk()
        viewModel = PasswordDetailsViewModel(getPassword)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set password details`() {

        val password = Password(
            "3",
            "home wifi",
            "123456",
            null,
            false,
            "do not share it"
        )

        coEvery { getPassword("3") } returns DataState.Success(password)

        viewModel.getPasswordDetails("3")

        assertEquals(password, viewModel.state.value.details)
    }

    @Test
    fun `set error`() {

        coEvery { getPassword("3") } returns DataState.Error(ExceptionWrapper.Unknown)

        viewModel.getPasswordDetails("3")

        val expectedError = TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)

        assertEquals(expectedError, viewModel.state.value.error)
    }

    @Test
    fun `enable button to save changes`() {

        viewModel.onFieldsChanged(
            viewModel.state.value.details.copy(
                title = "super secret home wifi pass"
            )
        )

        assertTrue(viewModel.state.value.isSaveButtonEnabled)
    }
}