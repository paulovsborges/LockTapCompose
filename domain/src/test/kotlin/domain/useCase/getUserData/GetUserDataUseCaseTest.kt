package domain.useCase.getUserData

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.getUserData.GetUserData
import com.pvsb.domain.useCase.getUserData.GetUserDataUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetUserDataUseCaseTest {

    private lateinit var useCase: GetUserDataUseCase
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        useCase = GetUserData(userRepository)
    }

    @Test
    fun `should return user data`() = runTest {

        coEvery { userRepository.read() } returns dummyUser

        val actualResult = useCase()

        val expectedResult: DataState<User> = DataState.Success(dummyUser)

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should return error`() = runTest {

        coEvery { userRepository.read() } throws NullPointerException("User data is null")

        val actualResult = useCase()

        val expectedResult: DataState<User> = DataState.Error(ExceptionWrapper.Unknown)

        assertEquals(expectedResult, actualResult)
    }

    companion object {
        val dummyUser = User("123", true)
    }
}