package com.pvsb.datasource.mapper.password

import com.pvsb.datasource.mapper.password.PasswordMapper.toEntity
import com.pvsb.datasource.mapper.password.PasswordMapper.toModel
import com.pvsb.domain.entity.Password
import junit.framework.TestCase.assertEquals
import locktap.locktapdb.PasswordsEntity
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar

class PasswordMapperTest {

    @Test
    fun `from a entity to a model`() {

        val entity = PasswordsEntity(
            1L,
            "",
            "",
            "4/16/23, 1:46 PM",
            0L,
            null
        )

        val actualResult = entity.toModel()

        val expectedDate = Calendar.getInstance()
        expectedDate.time = SimpleDateFormat.getInstance().parse(entity.createdAt)!!

        val expectedResult = Password(
            "1",
            "",
            "",
            expectedDate.time,
            false,
            null
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `from a model to a entity`() {

        val date = Calendar.getInstance()
        date.time = SimpleDateFormat.getInstance().parse("4/16/23, 1:46 PM")!!

        val model = Password(
            "1",
            "",
            "",
            date.time,
            false,
            null
        )

        val actualResult = model.toEntity()

        val expectedResult = PasswordsEntity(
            1L,
            "",
            "",
            "4/16/23, 1:46 PM",
            0L,
            null
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should set date string on entity to empty`() {

        val model = Password(
            "1",
            "",
            "",
            null,
            false,
            null
        )

        val actualResult = model.toEntity()

        val expectedResult = PasswordsEntity(
            1L,
            "",
            "",
            "",
            0L,
            null
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should set date to null on model`() {
        val entity = PasswordsEntity(
            1L,
            "",
            "",
            "4/16/23, ,. ,.fj1:46 PM",
            0L,
            null
        )

        val actualResult = entity.toModel()

        val expectedResult = Password(
            "1",
            "",
            "",
            null,
            false,
            null
        )

        assertEquals(expectedResult, actualResult)
    }
}
