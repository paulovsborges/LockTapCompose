package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val addPhotoToVaultUseCase: AddPhotoToVaultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PhotoDetailsState())
    val state = _state.asStateFlow()

    fun addPhoto(imageFilePath: String) {
        viewModelScope.launch {
            addPhotoToVaultUseCase(imageFilePath)
        }
    }

}