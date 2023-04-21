package domain.useCase.passwords.togglePasswordFavorite

import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavorite
import com.pvsb.domain.useCase.password.togglePasswordFavorite.TogglePasswordFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class TogglePasswordFavoriteUseCaseTest {

    private lateinit var useCase: TogglePasswordFavoriteUseCase
    private lateinit var passwordsRepository: PasswordsRepository

    @BeforeEach
    fun setup() {
        passwordsRepository = mockk()
        useCase = TogglePasswordFavorite(passwordsRepository)
    }

    @Test
    fun `should add password to favorites`() = runTest {

        coEvery { passwordsRepository.getPassword(any()) } returns Password(
            "1",
            "",
            "",
            null,
            false,
            null
        )

        useCase("1")

        val expectedResult = Password(
            "1",
            "",
            "",
            null,
            true,
            null
        )

        coVerify { passwordsRepository.add(expectedResult) }
    }

    @Test
    fun `should remove password from Favorites`() = runTest {

        coEvery { passwordsRepository.getPassword(any()) } returns Password(
            "1",
            "",
            "",
            null,
            true,
            null
        )

        useCase("1")

        val expectedResult = Password(
            "1",
            "",
            "",
            null,
            false,
            null
        )

        coVerify { passwordsRepository.add(expectedResult) }
    }

    @Test
    fun `should not call to add password when there is no password for the given id`() = runTest {

        coEvery { passwordsRepository.getPassword(any()) } returns null

        useCase("1")

        coVerify(exactly = 0) { passwordsRepository.add(any()) }
    }
}
