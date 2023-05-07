package domain.useCase.passwords.addPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.addPassword.AddPassword
import com.pvsb.domain.useCase.password.addPassword.AddPasswordUseCase
import com.pvsb.domain.util.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class AddPasswordUseCaseTest {

    private lateinit var useCase: AddPasswordUseCase
    private lateinit var passwordsRepository: PasswordsRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        passwordsRepository = mockk()
        logger = spyk()
        useCase = AddPassword(passwordsRepository, logger)
    }

    @Test
    fun `should call to add a password on repository`() = runTest {

        coEvery { passwordsRepository.getAll() } returns dummyPasswords
        coEvery { passwordsRepository.add(any()) } returns Unit

        val input = AddPasswordUseCase.Input(
            "home wifi",
            "123456",
            "do not share it"
        )

        useCase(input)

        coVerify { passwordsRepository.add(any()) }
    }

    @Test
    fun `should return error`() = runTest {

        coEvery { passwordsRepository.getAll() } throws IllegalStateException()

        val input = AddPasswordUseCase.Input(
            "home wifi",
            "123456",
            "do not share it"
        )

        val state = useCase(input)

        verify { logger.e(any()) }
        coVerify(exactly = 0) { passwordsRepository.add(any()) }
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
