package com.pvsb.domain.useCase.memo.addMemo

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.util.Logger
import java.util.Calendar

class AddMemo(
    private val memoRepository: MemoRepository,
    private val logger: Logger
) : AddMemoUseCase {

    override suspend fun invoke(input: AddMemoUseCase.Input): DataState<Unit> {

        return try {

            val memos = memoRepository.getAll()

            val memoId = memos.size + 1

            val memo = Memo(
                id = memoId.toString(),
                title = input.title,
                description = input.description,
                createdAt = Calendar.getInstance().time,
                isFavorite = false
            )

            memoRepository.add(memo)
            DataState.Success(Unit)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}