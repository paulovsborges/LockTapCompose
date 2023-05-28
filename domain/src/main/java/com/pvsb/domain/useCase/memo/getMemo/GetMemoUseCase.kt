package com.pvsb.domain.useCase.memo.getMemo

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Memo

interface GetMemoUseCase {

    suspend operator fun invoke(memoId: String): DataState<Memo>
}
