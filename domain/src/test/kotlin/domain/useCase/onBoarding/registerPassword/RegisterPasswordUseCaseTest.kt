package domain.useCase.onBoarding.registerPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.onBoarding.registerPassword.RegisterPassword
import com.pvsb.domain.useCase.onBoarding.registerPassword.RegisterPasswordUseCase
import com.pvsb.domain.util.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class RegisterPasswordUseCaseTest {

    private lateinit var useCase: RegisterPasswordUseCase
    private lateinit var userRepository: UserRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        logger = spyk()
        useCase = RegisterPassword(userRepository, logger)
    }

    @Test
    fun `register password with success`() = runTest {

        val userData = User("", true)

        coEvery { userRepository.read() } returns userData
        coEvery { userRepository.save(any()) } returns Unit

        val state = useCase("123")

        coVerify { userRepository.save(userData.copy("123")) }
        Assertions.assertEquals(DataState.Success(Unit), state)
    }

    @Test
    fun `invalid password`() = runTest {

        val userData = User("123", true)

        coEvery { userRepository.read() } returns userData

        val state = useCase("1234")

        val expectedState = DataState.Error<Unit>(RegisterPassword.Error.INVALID_PASSWORD)

        coVerify(exactly = 0) { userRepository.save(any()) }
        Assertions.assertEquals(expectedState, state)
    }

    @Test
    fun `no user data on repository`() = runTest {

        coEvery { userRepository.read() } returns null

        val state = useCase("1234")

        val expectedState = DataState.Error<Unit>(ExceptionWrapper.Unknown)

        verify { logger.e(any()) }
        coVerify(exactly = 0) { userRepository.save(any()) }
        Assertions.assertEquals(expectedState, state)
    }
}
