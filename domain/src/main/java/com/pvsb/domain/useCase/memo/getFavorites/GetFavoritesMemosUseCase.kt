package com.pvsb.domain.useCase.memo.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.Memo

interface GetFavoritesMemosUseCase {
    suspend operator fun invoke(): DataState<List<Memo>>
}