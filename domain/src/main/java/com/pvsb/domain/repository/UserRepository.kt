package com.pvsb.domain.repository

import com.pvsb.domain.entity.User

interface UserRepository {
    suspend fun save(user: User)
    suspend fun read(): User?
}