package com.pvsb.domain.useCase.memo.addMemo

import com.pvsb.domain.entity.DataState

interface AddMemoUseCase {

    suspend operator fun invoke(input: Input): DataState<Unit>

    data class Input(
        val title: String,
        val description: String,
    )
}