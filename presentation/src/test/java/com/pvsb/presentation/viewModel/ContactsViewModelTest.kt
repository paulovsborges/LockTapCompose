package com.pvsb.presentation.viewModel

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.useCase.addContact.AddContact
import com.pvsb.domain.useCase.addContact.AddContactUseCase
import com.pvsb.domain.useCase.getContacts.GetContactsUseCase
import com.pvsb.presentation.R
import com.pvsb.presentation.main.categories.allScreen.privateContacts.ContactsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactsViewModelTest {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var addContactUseCase: AddContactUseCase
    private lateinit var getContactsUseCase: GetContactsUseCase
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        addContactUseCase = mockk()
        getContactsUseCase = mockk()
        viewModel = ContactsViewModel(addContactUseCase, getContactsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `add contact with success`() {

        val stream = flow<DataState<Unit>> {
            emit(DataState.Success(Unit))
        }

        val dummyContact = mockk<Contact>()

        coEvery { addContactUseCase.invoke(any()) } returns stream

        viewModel.insertContact(dummyContact)

        assertNull(viewModel.state.value.error)
    }

    @Test
    fun `present error contact already exists error`() {

        val stream = flow<DataState<Unit>> {
            emit(DataState.Error(AddContact.Error.ContactAlreadyExists))
        }

        val dummyContact = mockk<Contact>()

        coEvery { addContactUseCase.invoke(any()) } returns stream

        viewModel.insertContact(dummyContact)

        val expectedResult = TypedMessage.Reference(R.string.error_contact_already_exists)

        assertEquals(expectedResult, viewModel.state.value.error)
    }

    @Test
    fun `present unknown error`() {

        val stream = flow<DataState<Unit>> {
            emit(DataState.Error(ExceptionWrapper.Unknown))
        }

        val dummyContact = mockk<Contact>()

        coEvery { addContactUseCase.invoke(any()) } returns stream

        viewModel.insertContact(dummyContact)

        val expectedResult = TypedMessage.Reference(R.string.error_there_was_an_unexpected_error)

        assertEquals(expectedResult, viewModel.state.value.error)
    }

    @Test
    fun `present contacts list`() {

        val dummyContacts = List(5) {
            Contact(
                "$it",
                "john doe $it",
                "123",
                null,
                false
            )
        }

        coEvery { getContactsUseCase.invoke() } returns DataState.Success(dummyContacts)

        viewModel.getContacts()

        assertEquals(dummyContacts, viewModel.state.value.contactsList)
    }
}