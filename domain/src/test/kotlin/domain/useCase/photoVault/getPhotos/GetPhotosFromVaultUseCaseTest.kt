package domain.useCase.photoVault.getPhotos

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.getPhotos.GetPhotosFromVault
import com.pvsb.domain.useCase.photoVault.getPhotos.GetPhotosFromVaultUseCase
import com.pvsb.domain.util.Logger
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GetPhotosFromVaultUseCaseTest {

    private lateinit var useCase: GetPhotosFromVaultUseCase
    private lateinit var repository: PhotoVaultRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        repository = mockk()
        logger = spyk()
        useCase = GetPhotosFromVault(repository, logger)
    }

    @Test
    fun `should return photos from vault`() = runTest {

        val dummyPhotos = List(20) {
            Photo(
                (it + 1).toLong(),
                "",
                it % 2 == 0
            )
        }

        coEvery { repository.getAllPhotos() } returns dummyPhotos

        val state = useCase()

        Assertions.assertEquals(dummyPhotos, (state as DataState.Success).data)
    }

    @Test
    fun `should return when something goes wrong`() = runTest {

        coEvery { repository.getAllPhotos() } throws IllegalStateException()

        val state = useCase()

        Assertions.assertInstanceOf(DataState.Error::class.java, state)
        verify { logger.e(any()) }
    }
}
