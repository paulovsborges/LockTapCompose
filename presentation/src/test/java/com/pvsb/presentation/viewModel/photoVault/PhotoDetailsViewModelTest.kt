package com.pvsb.presentation.viewModel.photoVault

import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import com.pvsb.domain.useCase.photoVault.getPhotos.GetPhotosFromVaultUseCase
import com.pvsb.presentation.categories.allScreen.photoVault.PhotoVaultListViewModel
import com.pvsb.presentation.categories.allScreen.photoVault.photoDetails.PhotoDetailsViewModel
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
class PhotoDetailsViewModelTest {

    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var addPhotoToVaultUseCase: AddPhotoToVaultUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        addPhotoToVaultUseCase = spyk()
        viewModel = PhotoDetailsViewModel(addPhotoToVaultUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}