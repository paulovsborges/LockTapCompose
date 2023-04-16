package domain.useCase.contact.deleteContact

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.contact.deleteContact.DeleteContact
import com.pvsb.domain.useCase.contact.deleteContact.DeleteContactUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DeleteContactUseCaseTest {

    private lateinit var useCase: DeleteContactUseCase
    private lateinit var contactsRepository: ContactsRepository

    @BeforeEach
    fun setup() {
        contactsRepository = mockk()
        useCase = DeleteContact(contactsRepository)
    }

    @Test
    fun `call to delete contact`() = runTest {

        coEvery { contactsRepository.deleteContact(any()) } returns Unit

        val actualResult = useCase(1)

        val expectedResult = DataState.Success(Unit)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `unexpected error`() = runTest {

        coEvery { contactsRepository.deleteContact(any()) } throws IllegalStateException()

        val actualResult = useCase(1)

        val expectedResult = DataState.Error<Unit>(ExceptionWrapper.Unknown)

        assertEquals(expectedResult, actualResult)
    }
}
