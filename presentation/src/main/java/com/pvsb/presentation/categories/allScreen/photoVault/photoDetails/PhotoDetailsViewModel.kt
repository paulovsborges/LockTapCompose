package com.pvsb.presentation.categories.allScreen.photoVault.photoDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import com.pvsb.domain.useCase.photoVault.getPhoto.GetPhotoFromVaultUseCase
import com.pvsb.domain.useCase.photoVault.togglePhotoFavorite.TogglePhotoFavoriteUseCase
import com.pvsb.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.useCase.photoVault.deletePhoto.DeletePhotoFromVaultUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val addPhotoToVaultUseCase: AddPhotoToVaultUseCase,
    private val getPhotoFromVaultUseCase: GetPhotoFromVaultUseCase,
    private val togglePhotoFavoriteUseCase: TogglePhotoFavoriteUseCase,
    private val deletePhotoFromVaultUseCase: DeletePhotoFromVaultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PhotoDetailsState())
    val state = _state.asStateFlow()

    fun addPhoto(imageFilePath: String) {
        viewModelScope.launch {
            when (val state = addPhotoToVaultUseCase(imageFilePath)) {
                is DataState.Error -> {
                    setError(state.error)
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
                    setError(state.error)
                }
                is DataState.Success -> {
                    _state.update { it.copy(details = state.data) }
                }
            }
        }
    }

    fun togglePhotoFavorite() {
        viewModelScope.launch {
            _state.value.details?.let { photo ->
                when (val state = togglePhotoFavoriteUseCase(photo.id)) {
                    is DataState.Error -> {
                        setError(state.error)
                    }
                    is DataState.Success -> {
                        getPhotoDetails(photo.id)
                    }
                }
            }
        }
    }

    fun deletePhoto() {
        viewModelScope.launch {
            _state.value.details?.let { photo ->
                when (val state = deletePhotoFromVaultUseCase(photo.id)) {
                    is DataState.Error -> {
                        setError(state.error)
                    }
                    is DataState.Success -> {
                        _state.update { it.copy(shouldFinishScreen = true) }
                    }
                }
            }
        }
    }

    fun dismissError() {
        _state.update { it.copy(error = null) }
    }

    private fun setError(error: ExceptionWrapper) {
        when (error) {
            ExceptionWrapper.Unknown -> {
                _state.update {
                    it.copy(
                        error = TypedMessage.Reference(
                            R.string.error_there_was_an_unexpected_error
                        )
                    )
                }
            }
        }
    }
}
