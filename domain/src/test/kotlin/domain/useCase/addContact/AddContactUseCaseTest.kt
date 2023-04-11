package domain.useCase.addContact

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.addContact.AddContact
import com.pvsb.domain.useCase.addContact.AddContactUseCase
import domain.utils.DummyContactData.dummyContacts
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class AddContactUseCaseTest {

    private lateinit var useCase: AddContactUseCase
    private lateinit var contactsRepository: ContactsRepository

    private var state: DataState<Unit>? = null

    @BeforeEach
    fun setup() {
        contactsRepository = mockk()
        useCase = AddContact(contactsRepository)
    }

    @AfterEach
    fun tearDown() {
        state = null
    }

    @Test
    fun `call repository to add contact`() = runTest {

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
    fun `contact number already exists on repository`() = runTest {

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

        val expectedResult: DataState<Unit> = DataState.Error(
            AddContact.Error.ContactAlreadyExists
        )

        assertEquals(expectedResult, state)
    }

    @Test
    fun `unknown exception`() = runTest {

        coEvery { contactsRepository.getContacts() } throws IllegalStateException()

        useCase(AddContactUseCase.Input(mockk())).collect {
            state = it
        }

        val expectedResult: DataState<Unit> = DataState.Error(ExceptionWrapper.Unknown)

        assertEquals(expectedResult, state)
    }

    @Test
    fun `should not return contact already exists error when the contact has an id`() = runTest {

        val contact = Contact(
            "146",
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

        val expectedResult: DataState<Unit> = DataState.Success(Unit)

        assertEquals(expectedResult, state)
    }
    @Test
    fun `should not return contact already exists er asd asdror when the contact has an id`() = runTest {

        val contact = Contact(
            "146",
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

        val expectedResult: DataState<Unit> = DataState.Success(Unit)

        assertEquals(expectedResult, state)
    }
}
