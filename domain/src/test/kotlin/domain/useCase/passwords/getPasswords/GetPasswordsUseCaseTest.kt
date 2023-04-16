package domain.useCase.passwords.getPasswords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.addPassword.AddPassword
import com.pvsb.domain.useCase.password.addPassword.AddPasswordUseCase
import com.pvsb.domain.useCase.password.getPasswords.GetPasswords
import com.pvsb.domain.useCase.password.getPasswords.GetPasswordsUseCase
import domain.useCase.passwords.addPassword.AddPasswordUseCaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetPasswordsUseCaseTest {

    private lateinit var useCase: GetPasswordsUseCase
    private lateinit var passwordsRepository: PasswordsRepository

    @BeforeEach
    fun setup() {
        passwordsRepository = mockk()
        useCase = GetPasswords(passwordsRepository)
    }

    @Test
    fun `should return a list of passwords`() = runTest {

        coEvery { passwordsRepository.getAll() } returns dummyPasswords

        val state = useCase()

        val expectedResult = DataState.Success(dummyPasswords)

        assertEquals(expectedResult, state)
    }

    @Test
    fun `should return error`() = runTest {

        coEvery { passwordsRepository.getAll() } throws IllegalStateException()

        val state = useCase()

        assertEquals(DataState.Error<Unit>(ExceptionWrapper.Unknown), state)
    }

    companion object {
        val dummyPasswords = List(5) {
            Password(
                "$it",
                "",
                "",
                null,
                false,
                null
            )
        }
    }
}