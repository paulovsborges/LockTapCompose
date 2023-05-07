package com.pvsb.presentation.viewModel

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContactsUseCase
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswordsUseCase
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavoriteUseCase
import com.pvsb.domain.useCase.photoVault.getFavorites.GetFavoritesPhotosUseCase
import com.pvsb.presentation.R
import com.pvsb.presentation.categories.favoriteScreen.CategoriesFavoritesViewModel
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoriesFavoritesViewModelTest {

    private lateinit var viewModel: CategoriesFavoritesViewModel
    private lateinit var getFavoriteContactsUseCase: GetFavoriteContactsUseCase
    private lateinit var getFavoritesPasswordsUseCase: GetFavoritesPasswordsUseCase
    private lateinit var togglePasswordFavoriteUseCase: TogglePasswordFavoriteUseCase
    private lateinit var getFavoritesPhotosUseCase: GetFavoritesPhotosUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getFavoriteContactsUseCase = mockk(relaxed = true)
        getFavoritesPasswordsUseCase = mockk(relaxed = true)
        getFavoritesPhotosUseCase = mockk(relaxed = true)
        togglePasswordFavoriteUseCase = spyk()

        viewModel = CategoriesFavoritesViewModel(
            getFavoriteContactsUseCase,
            getFavoritesPasswordsUseCase,
            getFavoritesPhotosUseCase,
            togglePasswordFavoriteUseCase
        )
    }

    @Test
    fun `should call to get favorite content`() {

        viewModel.getFavoriteContent()

        coVerifyOrder {
            getFavoriteContactsUseCase()
            getFavoritesPasswordsUseCase()
            getFavoritesPhotosUseCase()
        }
    }

    @Test
    fun `should set favorite content`() {

        val contacts = listOf(
            Contact("123", "John doe 1", "123", null, true),
            Contact("1234", "John doe 2", "1234", null, true),
        )

        val passwords = listOf(
            Password(
                "1", "pass 1", "", null, true, null
            ),
            Password(
                "2", "pass 2", "", null, true, null
            ),
        )

        val photos = listOf(
            Photo(1L, "", true),
            Photo(2L, "", true),
        )

        coEvery { getFavoriteContactsUseCase() } returns DataState.Success(contacts)
        coEvery { getFavoritesPasswordsUseCase() } returns DataState.Success(passwords)
        coEvery { getFavoritesPhotosUseCase() } returns DataState.Success(photos)

        viewModel.getFavoriteContent()

        Assert.assertEquals(contacts, viewModel.state.value.contacts)
        Assert.assertEquals(passwords, viewModel.state.value.passwords)
        Assert.assertEquals(photos, viewModel.state.value.photos)
    }

    @Test
    fun `should call to toggle password favorite and then call fetch the passwords again`() {

        viewModel.toggleFavorite("123")

        coVerifyOrder {
            togglePasswordFavoriteUseCase("123")
            getFavoritesPasswordsUseCase()
        }
    }

    @Test
    fun `should set error`() {
        coEvery { getFavoriteContactsUseCase() } returns DataState.Error(ExceptionWrapper.Unknown)

        viewModel.getFavoriteContent()

        val expectedResult = TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)

        Assert.assertEquals(expectedResult, viewModel.state.value.error)
    }
}
