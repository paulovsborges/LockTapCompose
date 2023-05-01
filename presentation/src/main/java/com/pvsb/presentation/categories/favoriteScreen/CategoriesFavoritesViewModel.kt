package com.pvsb.presentation.categories.favoriteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContactsUseCase
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswordsUseCase
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
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesFavoritesScreenState())
    val state = _state.asStateFlow()

    fun getFavoriteContent() {
        viewModelScope.launch {
            when (val state = getFavoriteContactsUseCase()) {
                is DataState.Error -> {
                }
                is DataState.Success -> {
                    _state.update { it.copy(contacts = state.data) }
                }
            }

            when (val state = getFavoritePasswordsUseCase()) {
                is DataState.Error -> {
                }
                is DataState.Success -> {
                    _state.update { it.copy(passwords = state.data) }
                }
            }
        }
    }
}
