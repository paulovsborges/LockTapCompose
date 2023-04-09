package domain.useCase.addContact

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.TypedMessage
import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.addContact.AddContact
import com.pvsb.domain.useCase.addContact.AddContactUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddContactUseCaseTest {

    private lateinit var useCase: AddContactUseCase
    private lateinit var contactsRepository: ContactsRepository

    @Before
    fun setup() {
        contactsRepository = mockk()
        useCase = AddContact(contactsRepository)
    }

    private var state: DataState? = null

    @Test
    fun `should call repository to add contact`() = runTest {

        val contact = Contact(
            "",
            "john doe",
            "1235",
            null,
            false
        )

        coEvery { contactsRepository.getContacts() } returns dummyContacts
        coEvery { contactsRepository.addContact(any()) } returns Unit

        useCase(AddContactUseCase.Input(contact)).collect()

        coVerify { contactsRepository.addContact(contact.copy(contactId = "5")) }
    }

    @Test
    fun `should return that contact number already exists on repository`() = runTest {

        val contact = Contact(
            "",
            "john doe",
            "123",
            null,
            false
        )

        coEvery { contactsRepository.getContacts() } returns dummyContacts
        coEvery { contactsRepository.addContact(any()) } returns Unit

        useCase(AddContactUseCase.Input(contact)).collect {
            state = it
        }

        val expectedResult: DataState = DataState.Error(
            TypedMessage.StringMessage("The contact already exists")
        )

        Assert.assertEquals(expectedResult, state)
    }

    companion object {
        val dummyContacts = List(5) {
            Contact(
                "$it",
                "john doe $it",
                "123",
                null,
                false
            )
        }
    }
}
