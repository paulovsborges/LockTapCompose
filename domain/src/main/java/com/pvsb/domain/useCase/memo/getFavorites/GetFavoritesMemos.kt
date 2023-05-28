package com.pvsb.domain.useCase.memo.getFavorites

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Memo
import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.util.Logger

class GetFavoritesMemos(
    private val memoRepository: MemoRepository,
    private val logger: Logger
) : GetFavoritesMemosUseCase {

    override suspend fun invoke(): DataState<List<Memo>> {
        return try {
            val memos = memoRepository.getAll()
            val favorites = memos.filter { it.isFavorite }
            DataState.Success(favorites)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}