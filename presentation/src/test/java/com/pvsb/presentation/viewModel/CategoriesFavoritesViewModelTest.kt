package com.pvsb.presentation.viewModel

import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContactsUseCase
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswordsUseCase
import com.pvsb.presentation.categories.favoriteScreen.CategoriesFavoritesViewModel
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoriesFavoritesViewModelTest {

    private lateinit var viewModel: CategoriesFavoritesViewModel
    private lateinit var getFavoriteContactsUseCase: GetFavoriteContactsUseCase
    private lateinit var getFavoritesPasswordsUseCase: GetFavoritesPasswordsUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getFavoriteContactsUseCase = mockk(relaxed = true)
        getFavoritesPasswordsUseCase = mockk(relaxed = true)
        viewModel = CategoriesFavoritesViewModel(
            getFavoriteContactsUseCase,
            getFavoritesPasswordsUseCase
        )
    }

    @Test
    fun `should call to get favorite content`() {

        viewModel.getFavoriteContent()

        coVerifyOrder {
            getFavoriteContactsUseCase()
            getFavoritesPasswordsUseCase()
        }
    }
}
