package com.pvsb.presentation.categories.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContactsUseCase
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswordsUseCase
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavoriteUseCase
import com.pvsb.domain.useCase.photoVault.getFavorites.GetFavoritesPhotosUseCase
import com.pvsb.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesFavoritesViewModel @Inject constructor(
    private val getFavoriteContactsUseCase: GetFavoriteContactsUseCase,
    private val getFavoritePasswordsUseCase: GetFavoritesPasswordsUseCase,
    private val getFavoritesPhotosUseCase: GetFavoritesPhotosUseCase,
    private val togglePasswordFavoriteUseCase: TogglePasswordFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesFavoritesScreenState())
    val state = _state.asStateFlow()

    fun getFavoriteContent() {
        viewModelScope.launch {
            getFavoriteContacts()
            getFavoritePasswords()
            getFavoritesPhotos()
        }
    }

    private suspend fun getFavoriteContacts() {
        when (val state = getFavoriteContactsUseCase()) {
            is DataState.Error -> {
                setError(state.error)
            }
            is DataState.Success -> {
                _state.update { it.copy(contacts = state.data) }
            }
        }
    }

    private suspend fun getFavoritePasswords() {
        when (val state = getFavoritePasswordsUseCase()) {
            is DataState.Error -> {
                setError(state.error)
            }
            is DataState.Success -> {
                _state.update { it.copy(passwords = state.data) }
            }
        }
    }

    private suspend fun getFavoritesPhotos() {
        when (val state = getFavoritesPhotosUseCase()) {
            is DataState.Error -> {
                setError(state.error)
            }
            is DataState.Success -> {
                _state.update { it.copy(photos = state.data) }
            }
        }
    }

    private fun setError(exception: ExceptionWrapper) {
        _state.update {
            it.copy(error = TypedMessage.Reference(R.string.error_there_was_an_unexpected_error))
        }
    }

    fun toggleFavorite(
        passwordId: String
    ) {
        viewModelScope.launch {
            togglePasswordFavoriteUseCase(passwordId)
            getFavoritePasswords()
        }
    }
}
