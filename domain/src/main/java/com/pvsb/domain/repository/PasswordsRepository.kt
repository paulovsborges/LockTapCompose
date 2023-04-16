package com.pvsb.domain.repository

import com.pvsb.domain.entity.Password

interface PasswordsRepository {
    suspend fun add(password: Password)
    suspend fun getAll(): List<Password>
    suspend fun delete(passwordId: Int)
    suspend fun getPassword(passwordId: Int) : Password?
}
