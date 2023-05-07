package com.pvsb.presentation.viewModel.passwords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.password.addPassword.AddPasswordUseCase
import com.pvsb.domain.useCase.password.deletePassword.DeletePasswordUseCase
import com.pvsb.domain.useCase.password.getPassword.GetPasswordUseCase
import com.pvsb.presentation.R
import com.pvsb.presentation.passwords.passwordsDetails.PasswordDetailsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
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
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@ExperimentalCoroutinesApi
open class PasswordDetailsViewModelTest {

    protected lateinit var viewModel: PasswordDetailsViewModel
    private lateinit var getPassword: GetPasswordUseCase
    private lateinit var addPasswordUseCase: AddPasswordUseCase
    private lateinit var deletePasswordUseCase: DeletePasswordUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getPassword = mockk()
        deletePasswordUseCase = mockk()
        addPasswordUseCase = spyk()
        viewModel = PasswordDetailsViewModel(getPassword, addPasswordUseCase, deletePasswordUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set password details`() {

        val password = Password(
            "3", "home wifi", "123456", null, false, "do not share it"
        )

        coEvery { getPassword("3") } returns DataState.Success(password)

        viewModel.getPasswordDetails("3")

        assertEquals(password, viewModel.state.value.passwordDetails.details)
    }

    @Test
    fun `set error`() {

        coEvery { getPassword("3") } returns DataState.Error(ExceptionWrapper.Unknown)

        viewModel.getPasswordDetails("3")

        val expectedError = TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)

        assertEquals(expectedError, viewModel.state.value.error)
    }

    @Test
    fun `add password`() {

        viewModel.savePassword()

        coVerify { addPasswordUseCase(any<AddPasswordUseCase.Input>()) }
    }

    @Test
    fun `update password`() {

        val password = Password(
            "3", "home wifi", "123456", null, false, "do not share it"
        )

        coEvery { getPassword("3") } returns DataState.Success(password)

        viewModel.getPasswordDetails("3")

        viewModel.savePassword()

        coVerify { addPasswordUseCase(any<Password>()) }
    }

    @Test
    fun `set to close screen when delete password returns success`() {

        coEvery { deletePasswordUseCase(any()) } returns DataState.Success(Unit)

        viewModel.deletePassword("123")

        assertTrue(viewModel.state.value.shouldCloseScreen)
    }

    @RunWith(Parameterized::class)
    class FieldChangesParameterizedTest(
        private val data: FieldChangedParameterizedData
    ) : PasswordDetailsViewModelTest() {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data() = listOf(
                arrayOf(
                    FieldChangedParameterizedData(
                        PasswordDetailsViewModel.FieldType.Title(
                            "123"
                        ),
                        true
                    )
                ),
                arrayOf(
                    FieldChangedParameterizedData(
                        PasswordDetailsViewModel.FieldType.Password(
                            "123"
                        ),
                        true
                    )
                ),
                arrayOf(
                    FieldChangedParameterizedData(
                        PasswordDetailsViewModel.FieldType.AdditionalInfo(
                            "123"
                        ),
                        true
                    )
                ),
                arrayOf(
                    FieldChangedParameterizedData(
                        PasswordDetailsViewModel.FieldType.AdditionalInfo(
                            "",
                        ),
                        false
                    )
                ),
            )
        }

        @Test
        fun `enable save button on field changed`() {

            print("testing with ${data.field}")

            viewModel.onFieldChanged(data.field)

            assertEquals(data.expectedResult, viewModel.state.value.isSaveButtonEnabled)
        }

        data class FieldChangedParameterizedData(
            val field: PasswordDetailsViewModel.FieldType,
            val expectedResult: Boolean
        )
    }
}
