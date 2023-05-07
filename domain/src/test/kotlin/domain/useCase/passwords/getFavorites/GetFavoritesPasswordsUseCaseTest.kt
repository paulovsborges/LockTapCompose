package domain.useCase.passwords.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswords
import com.pvsb.domain.useCase.password.getFavorites.GetFavoritesPasswordsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetFavoritesPasswordsUseCaseTest {

    private lateinit var useCase: GetFavoritesPasswordsUseCase
    private lateinit var passwordsRepository: PasswordsRepository

    @BeforeEach
    fun setup() {
        passwordsRepository = mockk()
        useCase = GetFavoritesPasswords(passwordsRepository)
    }

    @Test
    fun `should return a list of favorite passwords`() = runTest {

        val passwords = listOf(
            Password(
                "1", "Home wifi", "123456", null, true, null
            ),
            Password(
                "2", "Home wifi", "123456", null, true, null
            ),
            Password(
                "3", "Home wifi", "123456", null, false, null
            ),
            Password(
                "4", "Home wifi", "123456", null, false, null
            ),
            Password(
                "5", "Home wifi", "123456", null, false, null
            ),
            Password(
                "6", "Home wifi", "123456", null, false, null
            ),
        )

        coEvery { passwordsRepository.getAll() } returns passwords

        val state = useCase()

        val expectedResult = DataState.Success(
            listOf(
                Password(
                    "1", "Home wifi", "123456", null, true, null
                ),
                Password(
                    "2", "Home wifi", "123456", null, true, null
                )
            )
        )

        Assertions.assertEquals(expectedResult, state)
    }

    @Test
    fun `should return error when something goes wrong`() = runTest {

        coEvery { passwordsRepository.getAll() } throws IllegalStateException()

        val state = useCase()

        val expectedResult = DataState.Error<List<Password>>(ExceptionWrapper.Unknown)

        Assertions.assertEquals(expectedResult, state)
    }
}
