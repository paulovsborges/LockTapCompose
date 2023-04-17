package com.pvsb.presentation.viewModel.passwords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.useCase.password.getPasswords.GetPasswordsUseCase
import com.pvsb.presentation.passwords.passwordsList.PasswordsListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.util.Date

@ExperimentalCoroutinesApi
class PasswordsListViewModelTest {

    private lateinit var viewModel: PasswordsListViewModel
    private lateinit var getPasswordsUseCase: GetPasswordsUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getPasswordsUseCase = mockk()
        viewModel = PasswordsListViewModel(getPasswordsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set passwords list`() {

        val dummyPasswords = List(5) {
            Password(
                "$it", "Title $it", password = it.toString(), Date(), it % 2 == 0, null
            )
        }

        coEvery { getPasswordsUseCase() } returns DataState.Success(flow { emit(dummyPasswords) })

        viewModel.getPasswords()

        assertEquals(dummyPasswords, viewModel.state.value.passwords)
    }

    @Test
    fun `set error`() {

        coEvery { getPasswordsUseCase() } returns DataState.Error(ExceptionWrapper.Unknown)

        viewModel.getPasswords()

        assertNotNull(viewModel.state.value.error)

    }

    @Test
    fun `dismiss set error`() {

        coEvery { getPasswordsUseCase() } returns DataState.Error(ExceptionWrapper.Unknown)

        viewModel.getPasswords()

        viewModel.dismissError()

        assertNull(viewModel.state.value.error)
    }

    @Test
    fun `toggle favorite of a single password`() {

        val passwords = listOf(
            Password(
                "1",
                "",
                "",
                null,
                false,
                null
            ),
            Password(
                "2",
                "",
                "",
                null,
                false,
                null
            ),
            Password(
                "3",
                "",
                "",
                null,
                false,
                null
            )
        )

        coEvery { getPasswordsUseCase() } returns DataState.Success(flow { emit(passwords) })

        viewModel.getPasswords()

        viewModel.toggleFavorite("3")

        val expectedPasswords = listOf(
            Password(
                "1",
                "",
                "",
                null,
                false,
                null
            ),
            Password(
                "2",
                "",
                "",
                null,
                false,
                null
            ),
            Password(
                "3",
                "",
                "",
                null,
                true,
                null
            )
        )

        assertEquals(expectedPasswords, viewModel.state.value.passwords)
    }
}