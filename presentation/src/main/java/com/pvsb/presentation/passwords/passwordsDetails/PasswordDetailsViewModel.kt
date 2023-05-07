package com.pvsb.presentation.passwords.passwordsDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.password.addPassword.AddPasswordUseCase
import com.pvsb.domain.useCase.password.deletePassword.DeletePasswordUseCase
import com.pvsb.domain.useCase.password.getPassword.GetPasswordUseCase
import com.pvsb.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordDetailsViewModel @Inject constructor(
    private val getPassword: GetPasswordUseCase,
    private val addPasswordUseCase: AddPasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase
) : ViewModel() {

    sealed class FieldType() {
        data class Title(val text: String) : FieldType()
        data class Password(val text: String) : FieldType()
        data class AdditionalInfo(val text: String) : FieldType()
    }

    private val _state = MutableStateFlow(PasswordDetailsState())
    val state = _state.asStateFlow()

    fun savePassword() {
        viewModelScope.launch {
            if (_state.value.passwordDetails.details.id.isEmpty()) {
                addPassword()
            } else {
                updatePassword()
            }
        }
    }

    private fun addPassword() {
        viewModelScope.launch {

            val fields = _state.value.fields

            val input = AddPasswordUseCase.Input(
                fields.title, fields.password, fields.additionalInfo
            )

            when (addPasswordUseCase(input)) {
                is DataState.Error -> {
                    setError(TypedMessage.Reference(R.string.error_there_was_an_unexpected_error))
                }
                is DataState.Success -> {
                    _state.update { it.copy(shouldCloseScreen = true) }
                }
            }
        }
    }

    private fun updatePassword() {
        viewModelScope.launch {

            val fields = _state.value.fields

            val input = _state.value.passwordDetails.details.copy(
                title = fields.title,
                password = fields.password,
                additionalInfo = fields.additionalInfo
            )

            when (addPasswordUseCase(input)) {
                is DataState.Error -> {
                    setError(TypedMessage.Reference(R.string.error_there_was_an_unexpected_error))
                }
                is DataState.Success -> {
                    _state.update { it.copy(shouldCloseScreen = true) }
                }
            }
        }
    }

    fun getPasswordDetails(passwordId: String) {
        viewModelScope.launch {
            when (val state = getPassword(passwordId)) {
                is DataState.Error -> {
                    setError(TypedMessage.Reference(R.string.error_there_was_an_unexpected_error))
                }
                is DataState.Success -> {
                    _state.update {

                        val details = state.data

                        val fields = it.fields.copy(
                            title = details.title,
                            password = details.password,
                            additionalInfo = details.additionalInfo ?: ""
                        )

                        it.copy(
                            passwordDetails = it.passwordDetails.copy(
                                details = details
                            ), fields = fields
                        )
                    }
                }
            }
        }
    }

    fun onFieldChanged(field: FieldType) {
        when (field) {
            is FieldType.AdditionalInfo -> {
                _state.update {
                    it.copy(fields = it.fields.copy(additionalInfo = field.text))
                }
            }
            is FieldType.Password -> {
                _state.update {
                    it.copy(fields = it.fields.copy(password = field.text))
                }
            }
            is FieldType.Title -> {
                _state.update {
                    it.copy(fields = it.fields.copy(title = field.text))
                }
            }
        }

        _state.update { it.copy(isSaveButtonEnabled = it.toggleButtonEnabled()) }
    }

    private fun setError(error: TypedMessage) {
        _state.update { it.copy(error = error) }
    }

    fun dismissError() {
        _state.update { it.copy(error = null) }
    }

    fun deletePassword(passwordId: String) {
        viewModelScope.launch {
            when (deletePasswordUseCase(passwordId)) {
                is DataState.Error -> {
                    setError(TypedMessage.Reference(R.string.error_there_was_an_unexpected_error))
                }
                is DataState.Success -> {
                    _state.update { it.copy(shouldCloseScreen = true) }
                }
            }
        }
    }
}
