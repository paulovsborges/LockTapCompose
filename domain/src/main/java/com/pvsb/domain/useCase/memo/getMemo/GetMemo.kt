package com.pvsb.domain.useCase.memo.getMemo

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.util.Logger

class GetMemo(
    private val memoRepository: MemoRepository,
    private val logger: Logger
) : GetMemoUseCase {

    override suspend fun invoke(memoId: String): DataState<Memo> {
        return try {

            val memo = memoRepository.get(
                memoId
            ) ?: throw NullPointerException("There is no memo for id $memoId on bd")

            DataState.Success(memo)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
