package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import com.pvsb.domain.useCase.photoVault.getPhoto.GetPhotoFromVaultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val addPhotoToVaultUseCase: AddPhotoToVaultUseCase,
    private val getPhotoFromVaultUseCase: GetPhotoFromVaultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PhotoDetailsState())
    val state = _state.asStateFlow()

    fun addPhoto(imageFilePath: String) {
        viewModelScope.launch {
            when (addPhotoToVaultUseCase(imageFilePath)) {
                is DataState.Error -> {

                }
                is DataState.Success -> {
                    _state.update { it.copy(shouldFinishScreen = true) }
                }
            }
        }
    }

    fun getPhotoDetails(photoId: Long) {
        viewModelScope.launch {
            when (val state = getPhotoFromVaultUseCase(photoId)) {
                is DataState.Error -> {

                }
                is DataState.Success -> {
                    _state.update { it.copy(details = state.data) }
                }
            }
        }
    }
}