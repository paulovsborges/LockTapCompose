package com.pvsb.domain.useCase.memo.getMemos

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.useCase.memo.getMemos.GetMemosUseCase
import com.pvsb.domain.util.Logger
import kotlinx.coroutines.flow.Flow

class GetMemos(
    private val memoRepository: MemoRepository,
    private val logger: Logger
) : GetMemosUseCase {

    override suspend fun invoke(): DataState<Flow<List<Memo>>> {
        return try {
            DataState.Success(memoRepository.getAllAsFlow())
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
