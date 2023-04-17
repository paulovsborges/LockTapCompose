package com.pvsb.presentation.passwords.passwordsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.password.getPasswords.GetPasswordsUseCase
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavoriteUseCase
import com.pvsb.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordsListViewModel @Inject constructor(
    private val getPasswordsUseCase: GetPasswordsUseCase,
    private val togglePasswordFavoriteUseCase: TogglePasswordFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PasswordsListState())
    val state = _state.asStateFlow()

    fun getPasswords() {
        viewModelScope.launch {
            when (val state = getPasswordsUseCase()) {
                is DataState.Error -> {
                    handleError(state.error)
                }
                is DataState.Success -> {
                    state.data.collect { passwords ->
                        _state.update { it.copy(passwords = passwords) }
                    }
                }
            }
        }
    }

    fun toggleFavorite(
        passwordId: String
    ) {
        viewModelScope.launch {
            togglePasswordFavoriteUseCase(passwordId)
        }
    }

    private fun handleError(error: ExceptionWrapper) {
        val typedError = TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)

        _state.update { it.copy(error = typedError) }
    }

    fun dismissError() {
        _state.update { it.copy(error = null) }
    }
}