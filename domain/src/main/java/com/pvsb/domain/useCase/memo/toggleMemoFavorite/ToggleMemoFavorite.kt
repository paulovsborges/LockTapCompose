package com.pvsb.domain.useCase.memo.toggleMemoFavorite

import com.pvsb.domain.repository.MemoRepository
import com.pvsb.domain.util.Logger

class ToggleMemoFavorite(
    private val memoRepository: MemoRepository,
    private val logger: Logger
) : ToggleMemoFavoriteUseCase {

    override suspend fun invoke(memoId: String) {
        try {
            val password = memoRepository.get(
                memoId
            ) ?: throw NullPointerException("There is no memo for id $memoId on repository")

            val updatedPassword = password.copy(isFavorite = !password.isFavorite)

            memoRepository.add(updatedPassword)
        } catch (e: Exception) {
            logger.e(e)
        }
    }
}
