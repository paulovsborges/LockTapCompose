package domain.useCase.photoVault.addPhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVault
import com.pvsb.domain.useCase.photoVault.addPhoto.AddPhotoToVaultUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class AddPhotoToVaultUseCaseTest {

    private lateinit var useCase: AddPhotoToVaultUseCase
    private lateinit var photoVaultRepository: PhotoVaultRepository

    @BeforeEach
    fun setup() {
        photoVaultRepository = mockk()
        useCase = AddPhotoToVault(photoVaultRepository)
    }

    @Test
    fun `should call repository to add`() = runTest {

        coEvery { photoVaultRepository.getAllPhotos() } returns dummyPhotos
        coEvery { photoVaultRepository.addOrReplacePhoto(any()) } returns Unit

        val input = AddPhotoToVaultUseCase.Input(
            "",
            false
        )

        useCase(input)

        coVerify { photoVaultRepository.addOrReplacePhoto(any()) }
    }

    @Test
    fun `should generate the id of the photo by the highest id`() = runTest {

        coEvery { photoVaultRepository.getAllPhotos() } returns dummyPhotos
        coEvery { photoVaultRepository.addOrReplacePhoto(any()) } returns Unit

        val input = AddPhotoToVaultUseCase.Input(
            "",
            false
        )

        useCase(input)

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

        val input = AddPhotoToVaultUseCase.Input(
            "",
            false
        )

        val state = useCase(input)

        Assertions.assertInstanceOf(DataState.Error::class.java, state)
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