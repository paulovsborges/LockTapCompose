package com.pvsb.domain.useCase.password.addPassword

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.ExceptionWrapper
import com.pvsb.domain.entity.Password
import com.pvsb.domain.repository.PasswordsRepository
import java.util.Calendar

class AddPassword(
    private val passwordsRepository: PasswordsRepository
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
            e.printStackTrace()
            DataState.Error(ExceptionWrapper.Unknown)
        }
    }
}