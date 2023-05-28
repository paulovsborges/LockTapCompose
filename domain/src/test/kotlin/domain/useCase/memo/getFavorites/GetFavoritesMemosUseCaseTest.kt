package domain.useCase.memo.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.useCase.memo.getFavorites.GetFavoritesMemos
import com.pvsb.domain.useCase.memo.getFavorites.GetFavoritesMemosUseCase
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
class GetFavoritesMemosUseCaseTest {

    private lateinit var useCase: GetFavoritesMemosUseCase

    private lateinit var memoRepository: MemoRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        memoRepository = mockk()
        logger = spyk()

        useCase = GetFavoritesMemos(memoRepository, logger)
    }

    @Test
    fun `should return a list of favorite memos`() = runTest {

        val memos = listOf(
            Memo(
                "1",
                "memo 1",
                "",
                null,
                false
            ),
            Memo(
                "2",
                "memo 2",
                "",
                null,
                false
            ),
            Memo(
                "3",
                "memo 3",
                "",
                null,
                false
            ),
            Memo(
                "4",
                "memo 4",
                "",
                null,
                true
            )
        )

        coEvery { memoRepository.getAll() } returns memos

        val result = useCase()

        val expectedResult = DataState.Success(
            listOf(
                Memo(
                    "4",
                    "memo 4",
                    "",
                    null,
                    true
                )
            )
        )

        Assertions.assertEquals(expectedResult, result)
    }

    @Test
    fun `should call to log any exception`() = runTest {

        coEvery { memoRepository.getAll() } throws IllegalStateException()

        useCase()

        verify { logger.e(any()) }
    }
}