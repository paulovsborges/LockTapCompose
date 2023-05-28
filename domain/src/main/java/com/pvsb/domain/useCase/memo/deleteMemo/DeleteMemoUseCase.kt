package com.pvsb.domain.useCase.memo.deleteMemo

import com.pvsb.domain.entity.DataState

interface DeleteMemoUseCase {
    suspend operator fun invoke(memoId: String): DataState<Unit>
}