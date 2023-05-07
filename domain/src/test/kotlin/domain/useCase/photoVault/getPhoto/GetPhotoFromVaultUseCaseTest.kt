package domain.useCase.photoVault.getPhoto

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Photo
import com.pvsb.domain.repository.PhotoVaultRepository
import com.pvsb.domain.useCase.photoVault.getPhoto.GetPhotoFromVault
import com.pvsb.domain.useCase.photoVault.getPhoto.GetPhotoFromVaultUseCase
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
class GetPhotoFromVaultUseCaseTest {

    private lateinit var useCase: GetPhotoFromVaultUseCase
    private lateinit var repository: PhotoVaultRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        repository = mockk()
        logger = spyk()
        useCase = GetPhotoFromVault(repository, logger)
    }

    @Test
    fun `should return a photo from vault`() = runTest {

        val photo = Photo(
            1L,
            "",
            false
        )

        coEvery { repository.getPhotoById(1L) } returns photo

        val state = useCase(1L)

        Assertions.assertEquals(photo, (state as DataState.Success).data)
    }

    @Test
    fun `should return error when something goes wrong`() = runTest {

        coEvery { repository.getPhotoById(1L) } returns null

        val state = useCase(1L)

        Assertions.assertInstanceOf(DataState.Error::class.java, state)
        verify { logger.e(any()) }
    }
}
