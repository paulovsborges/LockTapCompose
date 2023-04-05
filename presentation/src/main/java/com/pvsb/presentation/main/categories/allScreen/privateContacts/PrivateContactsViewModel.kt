package com.pvsb.presentation.main.categories.allScreen.privateContacts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.PrivateContact
import com.pvsb.domain.useCase.addContact.AddContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivateContactsViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase
) : ViewModel() {

    var insertContactState by mutableStateOf<DataState>(DataState.Initial)

    fun getContacts(): List<PrivateContact> {
        return emptyList()
    }

    fun insertContact(contactData: PrivateContact) {
        viewModelScope.launch {
            addContactUseCase(
                AddContactUseCase.Input(
                    contactData
                )
            ).collect {
                insertContactState = it
            }
        }
    }
}