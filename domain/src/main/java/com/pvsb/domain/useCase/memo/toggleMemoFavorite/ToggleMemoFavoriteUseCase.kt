package com.pvsb.domain.useCase.memo.toggleMemoFavorite

interface ToggleMemoFavoriteUseCase {
    suspend operator fun invoke(memoId: String)
}
