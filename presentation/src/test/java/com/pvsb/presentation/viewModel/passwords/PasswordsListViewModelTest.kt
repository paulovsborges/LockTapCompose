package com.pvsb.presentation.viewModel.passwords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.useCase.password.getPasswords.GetPasswordsUseCase
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavoriteUseCase
import com.pvsb.presentation.passwords.categoriesPasswordsList.PasswordsListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
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
    private lateinit var togglePasswordFavoriteUseCase: TogglePasswordFavoriteUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getPasswordsUseCase = mockk()
        togglePasswordFavoriteUseCase = spyk()
        viewModel = PasswordsListViewModel(getPasswordsUseCase, togglePasswordFavoriteUseCase)
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

        assertEquals(dummyPasswords, viewModel.state.value.allPasswords)
    }

    @Test
    fun `set favorite passwords list`() {

        val dummyPasswords = listOf(
            Password(
                "1", "Title 1", password = "1", Date(), false, null
            ),
            Password(
                "2", "Title 2", password = "2", Date(), true, null
            ),
            Password(
                "3", "Title 3", password = "3", Date(), false, null
            ),
        )

        coEvery { getPasswordsUseCase() } returns DataState.Success(flow { emit(dummyPasswords) })

        viewModel.getPasswords()

        val expectedResult = listOf(dummyPasswords[1])

        assertEquals(expectedResult, viewModel.state.value.favoritePasswords)
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
    fun `toggle favorite of password`() {

        viewModel.toggleFavorite("1")

        coVerify { togglePasswordFavoriteUseCase(any()) }
    }
}
