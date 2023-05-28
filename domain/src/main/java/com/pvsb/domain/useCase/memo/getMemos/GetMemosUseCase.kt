package com.pvsb.domain.useCase.memo.getMemos

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Memo
import kotlinx.coroutines.flow.Flow

interface GetMemosUseCase {
    suspend operator fun invoke(): DataState<Flow<List<Memo>>>
}
