package domain.useCase.photoVault.deletePhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.deletePhoto.DeletePhotoFromVault
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
class DeletePhotoFromVaultUseCaseTest {

    private lateinit var useCase: DeletePhotoFromVaultUseCase
    private lateinit var repository: PhotoVaultRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        repository = mockk()
        logger = spyk()
        useCase = DeletePhotoFromVault(repository, logger)
    }

    @Test
    fun `should call repository to delete a photo`() = runTest {

        coEvery { repository.deletePhoto(1L) } returns Unit

        useCase(1L)

        coVerify { repository.deletePhoto(any()) }
    }

    @Test
    fun `should return error when something goes wrong`() = runTest {

        coEvery { repository.deletePhoto(1L) } throws IllegalStateException()

        val state = useCase(1L)

        Assertions.assertInstanceOf(DataState.Error::class.java, state)
        verify { logger.e(any()) }
    }
}
