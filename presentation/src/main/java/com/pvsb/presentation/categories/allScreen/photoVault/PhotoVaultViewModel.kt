package com.pvsb.presentation.categories.allScreen.photoVault

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import com.pvsb.domain.useCase.photoVault.getPhotos.GetPhotosFromVaultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoVaultViewModel @Inject constructor(
    private val getPhotosFromVaultUseCase: GetPhotosFromVaultUseCase,
    private val addPhotoToVaultUseCase: AddPhotoToVaultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PhotoVaultScreenState())
    val state = _state.asStateFlow()

    fun getPhotos() {
        viewModelScope.launch {
            when (val state = getPhotosFromVaultUseCase()) {
                is DataState.Error -> {}
                is DataState.Success -> {
                    _state.update { it.copy(photos = state.data) }
                }
            }
        }
    }

    fun addPhoto(imageFilePath: String) {
        viewModelScope.launch {
            addPhotoToVaultUseCase(imageFilePath)
        }
    }
}