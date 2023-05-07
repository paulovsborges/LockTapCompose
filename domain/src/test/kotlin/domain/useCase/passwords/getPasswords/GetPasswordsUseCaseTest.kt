package domain.useCase.passwords.getPasswords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.getPasswords.GetPasswords
import com.pvsb.domain.useCase.password.getPasswords.GetPasswordsUseCase
import com.pvsb.domain.util.Logger
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetPasswordsUseCaseTest {

    private lateinit var useCase: GetPasswordsUseCase
    private lateinit var passwordsRepository: PasswordsRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        passwordsRepository = mockk()
        logger = spyk()
        useCase = GetPasswords(passwordsRepository, logger)
    }

    @Test
    fun `should return a list of passwords`() = runTest {

        val stream = flow { emit(dummyPasswords) }

        coEvery { passwordsRepository.getAllAsFlow() } returns stream

        val state = useCase()

        val expectedResult = DataState.Success(stream)

        assertEquals(expectedResult, state)
    }

    @Test
    fun `should return error`() = runTest {

        coEvery { passwordsRepository.getAll() } throws IllegalStateException()

        val state = useCase()

        assertEquals(DataState.Error<Unit>(ExceptionWrapper.Unknown), state)
        verify { logger.e(any()) }
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
