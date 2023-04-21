package domain.useCase.photoVault.togglePhotoFavorite

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.togglePhotoFavorite.TogglePhotoFavorite
import com.pvsb.domain.useCase.photoVault.togglePhotoFavorite.TogglePhotoFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class TogglePhotoFavoriteUseCaseTest {

    private lateinit var useCase: TogglePhotoFavoriteUseCase
    private lateinit var repository: PhotoVaultRepository

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = TogglePhotoFavorite(repository)
    }

    @Test
    fun `should call repository to toggle photo favorite`() = runTest {

        val photo = Photo(
            1L,
            "",
            false
        )

        coEvery { repository.getPhotoById(1L) } returns photo

        useCase(1L)

        coVerify { repository.addOrReplacePhoto(photo.copy(isFavorite = true)) }
    }

    @Test
    fun `should return error when something goes wrong`() = runTest {

        coEvery { repository.getPhotoById(1L) } returns null

        val state = useCase(1L)

        Assertions.assertInstanceOf(DataState.Error::class.java, state)
    }
}