package com.pvsb.datasource.mapper.photoVault

import com.pvsb.datasource.mapper.photoVault.PhotoMapper.toEntity
import com.pvsb.datasource.mapper.photoVault.PhotoMapper.toModel
import com.pvsb.domain.entity.Photo
import locktap.locktapdb.PhotoVaultEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoMapperTest {

    @Test
    fun `from a entity to a model`() {

        val entity = PhotoVaultEntity(
            1L, "", 0L
        )

        val actualResult = entity.toModel()

        val expectedResult = Photo(
            1L, "", false
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `from a model to a entity`() {

        val model = Photo(
            1L, "", true
        )

        val actualResult = model.toEntity()

        val expectedResult = PhotoVaultEntity(
            1L, "", 1L
        )

        assertEquals(expectedResult, actualResult)
    }
}