package com.pvsb.datasource.mapper.User

import com.pvsb.datasource.dto.UserDTO
import com.pvsb.domain.entity.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object UserMapper {

    fun User.toDto(): UserDTO {
        return this.run {
            UserDTO(password, hasSeenOnBoardingAlready)
        }
    }

    fun UserDTO.toEntity(): User {
        return this.run {
            User(password, hasSeenOnBoardingAlready)
        }
    }

    fun UserDTO.toJsonString(): String {
        return Json.encodeToString(this)
    }

    fun String.toUserDTO(): UserDTO {
        return Json.decodeFromString<UserDTO>(this)
    }
}
