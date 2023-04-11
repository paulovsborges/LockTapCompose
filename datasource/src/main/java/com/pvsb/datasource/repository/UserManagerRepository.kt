package com.pvsb.datasource.repository

import com.pvsb.datasource.local.dataStore.LocalDataManager
import com.pvsb.datasource.mapper.User.UserMapper.toDto
import com.pvsb.datasource.mapper.User.UserMapper.toEntity
import com.pvsb.datasource.mapper.User.UserMapper.toJsonString
import com.pvsb.datasource.mapper.User.UserMapper.toUserDTO
import com.pvsb.domain.entity.User
import com.pvsb.domain.repository.UserRepository
import kotlinx.coroutines.flow.first

class UserManagerRepository(
    private val localDataManager: LocalDataManager
) : UserRepository {

    companion object {
        const val DATA_KEY = "USER_DATA_KEY"
    }

    override suspend fun save(user: User) {
        val encodedUser = user.toDto().toJsonString()
        localDataManager.write(DATA_KEY, encodedUser)
    }

    override suspend fun read(): User {
        return localDataManager.read(DATA_KEY).first()?.toUserDTO()?.toEntity()
            ?: throw NullPointerException("No user data on repository")
    }
}