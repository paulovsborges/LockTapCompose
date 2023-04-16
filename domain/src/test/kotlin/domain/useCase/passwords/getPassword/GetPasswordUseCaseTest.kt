package domain.useCase.passwords.getPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.getPassword.GetPassword
import com.pvsb.domain.useCase.password.getPassword.GetPasswordUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetPasswordUseCaseTest {

    private lateinit var useCase: GetPasswordUseCase
    private lateinit var passwordsRepository: PasswordsRepository

    @BeforeEach
    fun setup() {
        passwordsRepository = mockk()
        useCase = GetPassword(passwordsRepository)
    }

    @Test
    fun `should return a password`() = runTest {

        val password = Password(
            "1",
            "",
            "",
            null,
            false,
            null
        )

        coEvery { passwordsRepository.getPassword(any()) } returns password

        val state = useCase("1")

        val expectedResult = DataState.Success(password)

        assertEquals(expectedResult, state)
    }

    @Test
    fun `should return error`() = runTest {

        coEvery { passwordsRepository.getPassword(any()) } returns null

        val state = useCase("1")

        val expectedResult = DataState.Error<Password>(ExceptionWrapper.Unknown)

        assertEquals(expectedResult, state)
    }
}