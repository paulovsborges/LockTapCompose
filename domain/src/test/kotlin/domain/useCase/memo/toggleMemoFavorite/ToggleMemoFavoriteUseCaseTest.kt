package domain.useCase.memo.toggleMemoFavorite

import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.useCase.memo.toggleMemoFavorite.ToggleMemoFavorite
import com.pvsb.domain.useCase.memo.toggleMemoFavorite.ToggleMemoFavoriteUseCase
import com.pvsb.domain.util.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ToggleMemoFavoriteUseCaseTest {

    private lateinit var useCase: ToggleMemoFavoriteUseCase
    private lateinit var memoRepository: MemoRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {

        memoRepository = mockk()
        logger = spyk()

        useCase = ToggleMemoFavorite(memoRepository, logger)
    }

    @Test
    fun `should call repository to toggle memo favorite`() = runTest {

        val memo = Memo(
            "1",
            "memo 1",
            "",
            null,
            false
        )

        coEvery { memoRepository.get("1") } returns memo

        useCase("1")

        coVerify { memoRepository.add(memo.copy(isFavorite = true)) }
    }

    @Test
    fun `should call to log any exception`() = runTest {

        coEvery { memoRepository.get(any()) } returns null

        useCase("1")

        verify { logger.e(any()) }

    }
}