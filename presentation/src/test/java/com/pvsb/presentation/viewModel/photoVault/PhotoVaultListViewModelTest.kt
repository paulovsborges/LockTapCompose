package com.pvsb.presentation.viewModel.photoVault

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import com.pvsb.domain.useCase.photoVault.getPhotos.GetPhotosFromVaultUseCase
import com.pvsb.presentation.categories.allScreen.photoVault.PhotoVaultListViewModel
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
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotoVaultListViewModelTest {

    private lateinit var viewModel: PhotoVaultListViewModel
    private lateinit var getPhotosFromVaultUseCase: GetPhotosFromVaultUseCase
    private lateinit var addPhotoToVaultUseCase: AddPhotoToVaultUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getPhotosFromVaultUseCase = mockk()
        addPhotoToVaultUseCase = spyk()
        viewModel = PhotoVaultListViewModel(getPhotosFromVaultUseCase, addPhotoToVaultUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set photos list to state`() {

        val dummyPhotos = List(10) {
            Photo(
                (it + 1).toLong(),
                "",
                false
            )
        }

        coEvery { getPhotosFromVaultUseCase() } returns DataState.Success(dummyPhotos)

        viewModel.getPhotos()

        assertEquals(dummyPhotos, viewModel.state.value.photos)
    }

    @Test
    fun `add photo to vault`() {

        viewModel.addPhoto("")

        coVerify { addPhotoToVaultUseCase(any()) }
    }
}
