package com.pvsb.domain.useCase.memo.deleteMemo

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.util.Logger

class DeleteMemo(
    private val memoRepository: MemoRepository,
    private val logger: Logger
) : DeleteMemoUseCase {

    override suspend fun invoke(memoId: String): DataState<Unit> {

     return   try {
            memoRepository.delete(memoId)
            DataState.Success(Unit)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}