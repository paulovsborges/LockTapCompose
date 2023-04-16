package com.pvsb.domain.useCase.getUserData

import com.pvsb.domain.entity.DataState
import com.pvsb.domain.entity.User

interface GetUserDataUseCase {
    suspend operator fun invoke(): DataState<User>
}
