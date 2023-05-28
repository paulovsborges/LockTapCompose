package domain.useCase.memo.deleteMemo

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.useCase.memo.deleteMemo.DeleteMemo
import com.pvsb.domain.useCase.memo.deleteMemo.DeleteMemoUseCase
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
class DeleteMemoUseCaseTest {

    private lateinit var useCase: DeleteMemoUseCase

    private lateinit var memoRepository: MemoRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        memoRepository = mockk()
        logger = spyk()

        useCase = DeleteMemo(memoRepository, logger)
    }

    @Test
    fun `should call repository to delete a memo`() = runTest {
        coEvery { memoRepository.delete(any()) } returns Unit

        val result = useCase("1")

        val expectedResult = DataState.Success(Unit)

        Assertions.assertEquals(expectedResult, result)
        coVerify { memoRepository.delete("1") }
    }

    @Test
    fun `should call logger to log any exception`() = runTest {

        coEvery { memoRepository.delete(any()) } throws IllegalStateException()

        useCase("")

        verify { logger.e(any()) }

    }
}