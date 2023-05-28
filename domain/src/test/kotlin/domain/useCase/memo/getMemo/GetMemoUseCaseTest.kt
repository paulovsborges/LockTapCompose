package domain.useCase.memo.getMemo

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.useCase.memo.getMemo.GetMemo
import com.pvsb.domain.useCase.memo.getMemo.GetMemoUseCase
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
class GetMemoUseCaseTest {

    private lateinit var useCase: GetMemoUseCase

    private lateinit var memoRepository: MemoRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {

        memoRepository = mockk()
        logger = spyk()

        useCase = GetMemo(memoRepository, logger)
    }

    @Test
    fun `should return a memo by the given id`() = runTest {

        val memo = Memo(
            "1",
            "memo 1",
            "",
            null,
            false
        )

        coEvery { memoRepository.get("1") } returns memo

        val result = useCase("1")

        val expectedResult = DataState.Success(memo)
        Assertions.assertEquals(expectedResult, result)
    }

    @Test
    fun `should log any exception`() = runTest {

        coEvery { memoRepository.get("1") } returns null

        useCase("1")

        verify { logger.e(any()) }
    }
}