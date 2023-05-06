package domain.useCase.photoVault.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.getFavorites.GetFavoritesPhotos
import com.pvsb.domain.useCase.photoVault.getFavorites.GetFavoritesPhotosUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetFavoritesPhotosUseCaseTest {

    private lateinit var useCase: GetFavoritesPhotosUseCase
    private lateinit var photoVaultRepository: PhotoVaultRepository

    @BeforeEach
    fun setup() {
        photoVaultRepository = mockk()
        useCase = GetFavoritesPhotos(photoVaultRepository)
    }

    @Test
    fun `should return a list of favorite photos`() = runTest {

        val photos = listOf(
            Photo(1L, "", false),
            Photo(2L, "", true),
            Photo(3L, "", true),
            Photo(4L, "", false),
        )

        coEvery { photoVaultRepository.getAllPhotos() } returns photos

        val state = useCase()

        val expectedResult = DataState.Success(
            listOf(
                Photo(2L, "", true),
                Photo(3L, "", true)
            )
        )

        Assertions.assertEquals(expectedResult, state)
    }

    @Test
    fun `should return error when something goes wrong`() = runTest {

        coEvery { photoVaultRepository.getAllPhotos() } throws IllegalStateException()

        val state = useCase()

        val expectedResult = DataState.Error<List<Password>>(ExceptionWrapper.Unknown)

        Assertions.assertEquals(expectedResult, state)
    }

}