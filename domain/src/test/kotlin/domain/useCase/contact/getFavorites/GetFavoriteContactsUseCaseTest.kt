package domain.useCase.contact.getFavorites

import com.pvsb.domain.entity.Contact
import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.ContactsRepository
import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContacts
import com.pvsb.domain.useCase.contact.getFavorites.GetFavoriteContactsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetFavoriteContactsUseCaseTest {

    private lateinit var useCase: GetFavoriteContactsUseCase
    private lateinit var contactsRepository: ContactsRepository

    @BeforeEach
    fun setup() {
        contactsRepository = mockk()
        useCase = GetFavoriteContacts(contactsRepository)
    }

    @Test
    fun `should return a list of favorite contacts`() = runTest {

        val contactsFromRepository = listOf(
            Contact(
                "1", "john doe 1", "", null, false
            ),
            Contact(
                "2", "john doe 2", "", null, false
            ),
            Contact(
                "3", "john doe 3", "", null, false
            ),
            Contact(
                "4", "john doe 4", "", null, false
            ),
            Contact(
                "5", "john doe 5", "", null, true
            ),
            Contact(
                "6", "john doe 6", "", null, true
            )
        )

        coEvery { contactsRepository.getContacts() } returns contactsFromRepository

        val state = useCase()

        val expectedResult = DataState.Success(
            listOf(
                Contact(
                    "5", "john doe 5", "", null, true
                ),
                Contact(
                    "6", "john doe 6", "", null, true
                )
            )
        )

        Assertions.assertEquals(expectedResult, state)
    }

    @Test
    fun `should return an empty list when there is no favorite contacts on repository`() = runTest {

        val contactsFromRepository = listOf(
            Contact(
                "1", "john doe 1", "", null, false
            ),
            Contact(
                "2", "john doe 2", "", null, false
            ),
            Contact(
                "3", "john doe 3", "", null, false
            ),
            Contact(
                "4", "john doe 4", "", null, false
            )
        )

        coEvery { contactsRepository.getContacts() } returns contactsFromRepository

        val state = useCase()

        val expectedResult = DataState.Success(emptyList<Contact>())

        Assertions.assertEquals(expectedResult, state)
    }

    @Test
    fun `should return error if something goes wrong`() = runTest {

        coEvery { contactsRepository.getContacts() } throws IllegalStateException()

        val state = useCase()

        val expectedResult = DataState.Error<Contact>(ExceptionWrapper.Unknown)

        Assertions.assertEquals(expectedResult, state)
    }
}