package com.pvsb.presentation.viewModel.photoVault

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import com.pvsb.domain.useCase.photoVault.getPhoto.GetPhotoFromVaultUseCase
import com.pvsb.domain.useCase.photoVault.togglePhotoFavorite.TogglePhotoFavoriteUseCase
import com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.PhotoDetailsViewModel
import domain.useCase.photoVault.deletePhoto.DeletePhotoFromVaultUseCase
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotoDetailsViewModelTest {

    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var addPhotoToVaultUseCase: AddPhotoToVaultUseCase
    private lateinit var getPhotoFromVaultUseCase: GetPhotoFromVaultUseCase
    private lateinit var togglePhotoFavoriteUseCase: TogglePhotoFavoriteUseCase
    private lateinit var deletePhotoFromVaultUseCase: DeletePhotoFromVaultUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        addPhotoToVaultUseCase = mockk()
        getPhotoFromVaultUseCase = mockk()
        togglePhotoFavoriteUseCase = spyk()
        deletePhotoFromVaultUseCase = spyk()

        viewModel = PhotoDetailsViewModel(
            addPhotoToVaultUseCase,
            getPhotoFromVaultUseCase,
            togglePhotoFavoriteUseCase,
            deletePhotoFromVaultUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `call to add a photo and set to finish screen`() {

        coEvery { addPhotoToVaultUseCase(any()) } returns DataState.Success(Unit)

        viewModel.addPhoto("")

        coVerify { addPhotoToVaultUseCase(any()) }
        Assert.assertTrue(viewModel.state.value.shouldFinishScreen)
    }

    @Test
    fun `set details when succeeds to load photo details`() {

        val dummyPhoto = Photo(1L, "", false)

        coEvery { getPhotoFromVaultUseCase(1L) } returns DataState.Success(dummyPhoto)

        viewModel.getPhotoDetails(1L)

        Assert.assertEquals(dummyPhoto, viewModel.state.value.details)
    }

    @Test
    fun `call to toggle photo favorite`() {

        val dummyPhoto = Photo(1L, "", false)

        coEvery { getPhotoFromVaultUseCase(1L) } returns DataState.Success(dummyPhoto)

        viewModel.getPhotoDetails(1L)

        viewModel.togglePhotoFavorite()

        coVerify { togglePhotoFavoriteUseCase(1L) }
    }

    @Test
    fun `call to delete photo from vault`() {

        val dummyPhoto = Photo(1L, "", false)

        coEvery { getPhotoFromVaultUseCase(1L) } returns DataState.Success(dummyPhoto)

        viewModel.getPhotoDetails(1L)

        viewModel.deletePhoto()

        coVerify { deletePhotoFromVaultUseCase(1L) }
    }

    @Test
    fun `set error`() {
        coEvery { addPhotoToVaultUseCase(any()) } returns DataState.Error(
            ExceptionWrapper.Unknown
        )

        viewModel.addPhoto("")

        Assert.assertNotNull(viewModel.state.value.error)
    }

    @Test
    fun `dismiss error`() {
        coEvery { addPhotoToVaultUseCase(any()) } returns DataState.Error(
            ExceptionWrapper.Unknown
        )

        viewModel.addPhoto("")

        viewModel.dismissError()

        Assert.assertNull(viewModel.state.value.error)
    }
}