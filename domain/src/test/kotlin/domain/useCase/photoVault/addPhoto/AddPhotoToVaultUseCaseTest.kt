package domain.useCase.photoVault.addPhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVault
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
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
class AddPhotoToVaultUseCaseTest {

    private lateinit var useCase: AddPhotoToVaultUseCase
    private lateinit var photoVaultRepository: PhotoVaultRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        photoVaultRepository = mockk()
        logger = spyk()
        useCase = AddPhotoToVault(photoVaultRepository, logger)
    }

    @Test
    fun `should call repository to add`() = runTest {

        coEvery { photoVaultRepository.getAllPhotos() } returns dummyPhotos
        coEvery { photoVaultRepository.addOrReplacePhoto(any()) } returns Unit

        useCase("")

        coVerify { photoVaultRepository.addOrReplacePhoto(any()) }
    }

    @Test
    fun `should generate the id of the photo by the highest id`() = runTest {

        coEvery { photoVaultRepository.getAllPhotos() } returns dummyPhotos
        coEvery { photoVaultRepository.addOrReplacePhoto(any()) } returns Unit

        useCase("")

        coVerify {
            photoVaultRepository.addOrReplacePhoto(
                Photo(
                    6L,
                    "",
                    false
                )
            )
        }
    }

    @Test
    fun `should return error state when something goes wrong`() = runTest {

        coEvery { photoVaultRepository.getAllPhotos() } returns dummyPhotos
        coEvery { photoVaultRepository.addOrReplacePhoto(any()) } throws IllegalStateException()

        val state = useCase("")

        Assertions.assertInstanceOf(DataState.Error::class.java, state)
        verify { logger.e(any()) }
    }

    companion object {
        val dummyPhotos = List(5) {
            Photo(
                (it + 1).toLong(),
                "",
                false
            )
        }
    }
}
