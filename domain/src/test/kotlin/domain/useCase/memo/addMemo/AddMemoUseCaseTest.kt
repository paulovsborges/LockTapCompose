package domain.useCase.memo.addMemo

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.useCase.memo.addMemo.AddMemo
import com.pvsb.domain.useCase.memo.addMemo.AddMemoUseCase
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
class AddMemoUseCaseTest {

    private lateinit var useCase: AddMemoUseCase
    private lateinit var memoRepository: MemoRepository
    private lateinit var logger: Logger

    @BeforeEach
    fun setup() {
        memoRepository = mockk()
        logger = spyk()

        useCase = AddMemo(memoRepository, logger)
    }

    @Test
    fun `should call repository to add a memo`() = runTest {

        val dummyMemos = List(10) {
            Memo(
                "$it",
                "",
                "",
                null,
                false
            )
        }

        coEvery { memoRepository.getAll() } returns dummyMemos

        coEvery { memoRepository.add(any()) } returns Unit

        val state = useCase(AddMemoUseCase.Input("", ""))

        val expectedResult = DataState.Success(Unit)

        Assertions.assertEquals(expectedResult, state)

        coVerify { memoRepository.add(any()) }
    }

    @Test
    fun `should call logger to log any exception`() = runTest {

        coEvery { memoRepository.getAll() } throws IllegalStateException()

        val state = useCase(AddMemoUseCase.Input("", ""))

        val expectedResult = DataState.Error<Unit>(ExceptionWrapper.Unknown)

        Assertions.assertEquals(expectedResult, state)

        verify { logger.e(any()) }
    }
}