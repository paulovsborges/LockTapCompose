package domain.useCase.skipOnBoarding

import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoarding
import com.pvsb.domain.useCase.skipOnBoarding.SkipOnBoardingUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class SkipOnBoardingUseCaseTest {
    private lateinit var useCase: SkipOnBoardingUseCase
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        userRepository = mockk()
        useCase = SkipOnBoarding(userRepository)
    }

    @Test
    fun `save that user has seen on boarding`() = runTest {
        coEvery { userRepository.read() } returns dummyUser
        coEvery { userRepository.save(any()) } returns Unit

        useCase()

        coVerify { userRepository.save(dummyUser.copy(hasSeenOnBoardingAlready = true)) }
    }

    @Test
    fun `user has already seen on boarding`() = runTest {

        coEvery { userRepository.read() } returns dummyUser.copy(hasSeenOnBoardingAlready = true)

        useCase()

        coVerify(exactly = 0) { userRepository.save(any()) }
    }

    companion object {
        val dummyUser = User("123", false)
    }
}