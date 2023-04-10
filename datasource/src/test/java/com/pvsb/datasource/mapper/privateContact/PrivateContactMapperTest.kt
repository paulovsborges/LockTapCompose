package com.pvsb.datasource.mapper.privateContact

import com.pvsb.datasource.mapper.privateContact.PrivateContactMapper.toEntity
import com.pvsb.datasource.mapper.privateContact.PrivateContactMapper.toModel
import com.pvsb.domain.entity.Contact
import locktap.locktapdb.PrivateContactEntity
import org.junit.Assert
import org.junit.Test

class PrivateContactMapperTest {

    @Test
    fun `map a model to a entity`() {

        val model = Contact(
            "1",
            "john",
            "123",
            null,
            true
        )

        val actual = model.toEntity()

        val expectedResult = PrivateContactEntity(
            1L,
            "john",
            "123",
            null,
            1L
        )

        Assert.assertEquals(expectedResult, actual)
    }

    @Test
    fun `map a entity to a model`() {

        val entity = PrivateContactEntity(
            1L,
            "john",
            "123",
            null,
            1L
        )

        val actual = entity.toModel()

        val expectedResult = Contact(
            "1",
            "john",
            "123",
            null,
            true
        )

        Assert.assertEquals(expectedResult, actual)
    }
}
