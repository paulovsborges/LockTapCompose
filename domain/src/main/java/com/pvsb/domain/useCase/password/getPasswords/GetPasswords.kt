package com.pvsb.domain.useCase.password.getPasswords

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.util.Logger
import kotlinx.coroutines.flow.Flow

class GetPasswords(
    private val passwordsRepository: PasswordsRepository,
    private val logger: Logger
) : GetPasswordsUseCase {

    override suspend fun invoke(): DataState<Flow<List<Password>>> {
        return try {
            DataState.Success(passwordsRepository.getAllAsFlow())
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
