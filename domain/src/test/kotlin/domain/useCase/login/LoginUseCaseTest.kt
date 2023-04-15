package domain.useCase.login

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.login.Login
import com.pvsb.domain.useCase.login.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    private lateinit var useCase: LoginUseCase
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        useCase = Login(userRepository)
    }

    @Test
    fun `login with success`() = runTest {

        val userData = User("123", true)

        coEvery { userRepository.read() } returns userData

        val state = useCase("123")

        val expectedState = DataState.Success(Unit)

        Assertions.assertEquals(expectedState, state)
    }

    @Test
    fun `invalid credentials`() = runTest {

        val userData = User("123", true)

        coEvery { userRepository.read() } returns userData

        val state = useCase("1234")

        val expectedState = DataState.Error<Unit>(Login.Error.INVALID_PASSWORD)

        Assertions.assertEquals(expectedState, state)
    }

    @Test
    fun `no user data on repository`() = runTest {

        coEvery { userRepository.read() } returns null

        val state = useCase("123")

        val expectedState = DataState.Error<Unit>(ExceptionWrapper.Unknown)

        Assertions.assertEquals(expectedState, state)
    }
}