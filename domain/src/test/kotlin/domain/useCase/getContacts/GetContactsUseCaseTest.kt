package domain.useCase.getContacts

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.getContacts.GetContacts
import com.pvsb.domain.useCase.getContacts.GetContactsUseCase
import domain.useCase.utils.DummyContactData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetContactsUseCaseTest {

    private lateinit var useCase: GetContactsUseCase
    private lateinit var contactsRepository: ContactsRepository

    @BeforeEach
    fun setup() {
        contactsRepository = mockk()
        useCase = GetContacts(contactsRepository)
    }

    @Test
    fun `return a list of contacts`() = runTest {

        coEvery { contactsRepository.getContacts() } returns DummyContactData.dummyContacts

        val result = useCase()

        val expectedResult = DataState.Success(DummyContactData.dummyContacts)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `unexpected error`() = runTest {

        coEvery { contactsRepository.getContacts() } throws IllegalStateException()

        val result = useCase()

        val expectedResult: DataState<List<Contact>> = DataState.Error(ExceptionWrapper.Unknown)

        assertEquals(expectedResult, result)
    }
}
