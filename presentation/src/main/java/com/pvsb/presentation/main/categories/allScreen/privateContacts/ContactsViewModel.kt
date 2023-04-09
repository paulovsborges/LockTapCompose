package com.pvsb.presentation.main.categories.allScreen.privateContacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.addContact.AddContact
import com.pvsb.domain.useCase.addContact.AddContactUseCase
import com.pvsb.domain.useCase.getContacts.GetContactsUseCase
import com.pvsb.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PrivateContactState())
    val state = _state.asStateFlow()

    fun getContacts() {
        viewModelScope.launch {
            when (val state = getContactsUseCase()) {
                is DataState.Error -> {
                    handleErrors(state.error)
                }
                is DataState.Success -> {
                    _state.update { it.copy(contactsList = state.data) }
                }
            }
        }
    }

    fun insertContact(contactData: Contact) {
        viewModelScope.launch {
            addContactUseCase(
                AddContactUseCase.Input(
                    contactData
                )
            ).collect { state ->

                when (state) {
                    is DataState.Error -> {
                        handleErrors(state.error)
                    }
                    is DataState.Success -> Unit
                }
            }
        }
    }

    private fun handleErrors(error: ExceptionWrapper) {
        val typedError = when (error) {
            is AddContact.Error.ContactAlreadyExists -> {
                TypedMessage.Reference(R.string.error_contact_already_exists)
            }
            else -> {
                TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)
            }
        }

        _state.update { it.copy(error = typedError) }
    }
}