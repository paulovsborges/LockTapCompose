package com.pvsb.domain.useCase.password.addPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import com.pvsb.domain.util.Logger
import java.util.Calendar

class AddPassword(
    private val passwordsRepository: PasswordsRepository,
    private val logger: Logger
) : AddPasswordUseCase {

    override suspend fun invoke(input: AddPasswordUseCase.Input): DataState<Unit> {
        return try {

            val passwordId = passwordsRepository.getAll().size

            val password = Password(
                passwordId.toString(),
                input.title,
                input.password,
                Calendar.getInstance().time,
                false,
                input.additionalInfo
            )

            passwordsRepository.add(password)
            DataState.Success(Unit)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }

    override suspend fun invoke(password: Password): DataState<Unit> {
        return try {
            passwordsRepository.add(password)
            DataState.Success(Unit)
        } catch (e: Exception) {
            logger.e(e)
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}
