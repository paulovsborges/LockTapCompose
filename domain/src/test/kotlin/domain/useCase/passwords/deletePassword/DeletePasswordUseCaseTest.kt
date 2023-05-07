package domain.useCase.passwords.deletePassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.deletePassword.DeletePassword
import com.pvsb.domain.useCase.password.deletePassword.DeletePasswordUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DeletePasswordUseCaseTest {

    private lateinit var useCase: DeletePasswordUseCase
    private lateinit var passwordsRepository: PasswordsRepository

    @BeforeEach
    fun setup() {
        passwordsRepository = mockk()
        useCase = DeletePassword(passwordsRepository)
    }

    @Test
    fun `should call repository to delete a password`() = runTest {

        coEvery { passwordsRepository.delete(any()) } returns Unit

        val state = useCase("1")

        val expectedResult = DataState.Success(Unit)

        coVerify { passwordsRepository.delete(1) }
        Assertions.assertEquals(expectedResult, state)
    }

    @Test
    fun `should return error when something goes wrong`() = runTest {
        coEvery { passwordsRepository.delete(any()) } throws IllegalStateException()

        val state = useCase("1")

        val expectedResult = DataState.Error<Unit>(ExceptionWrapper.Unknown)

        Assertions.assertEquals(expectedResult, state)
    }

}