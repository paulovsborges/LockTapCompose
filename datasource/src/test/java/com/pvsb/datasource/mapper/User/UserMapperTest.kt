package com.pvsb.datasource.mapper.User

import com.pvsb.datasource.dto.UserDTO
import com.pvsb.datasource.mapper.User.UserMapper.toDto
import com.pvsb.datasource.mapper.User.UserMapper.toEntity
import com.pvsb.datasource.mapper.User.UserMapper.toJsonString
import com.pvsb.datasource.mapper.User.UserMapper.toUserDTO
import com.pvsb.domain.entity.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    @Test
    fun `map a entity to a dto`() {

        val entity = User(
            "123", true
        )

        val actualResult = entity.toDto()

        val expectedResult = UserDTO(
            "123", true
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `map a dto to a entity`() {

        val dto = UserDTO(
            "123", true
        )

        val actualResult = dto.toEntity()

        val expectedResult = User(
            "123", true
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `map a dto to a json string`() {
        val dto = UserDTO(
            "123", true
        )

        val actualResult = dto.toJsonString()

        val expectedResult = """{"password":"123","hasSeenOnBoardingAlready":true}""".trimIndent()

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `map a json string to a dto`() {

        val jsonString = """{"password":"123","hasSeenOnBoardingAlready":true}""".trimIndent()

        val actualResult = jsonString.toUserDTO()

        val expectedResult = UserDTO(
            "123", true
        )

        assertEquals(expectedResult, actualResult)
    }
}
