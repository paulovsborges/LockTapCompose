package com.pvsb.datasource.mapper.memos

import com.pvsb.datasource.mapper.memos.MemoMapper.toEntity
import com.pvsb.datasource.mapper.memos.MemoMapper.toModel
import com.pvsb.domain.entity.Memo
import junit.framework.TestCase
import locktap.locktapdb.MemoEntity
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar

class MemoMapperTest {

    @Test
    fun `from a entity to a model`() {

        val entity = MemoEntity(
            1L,
            "",
            "",
            "4/16/23, 1:46 PM",
            0L
        )

        val actualResult = entity.toModel()


        val expectedDate = Calendar.getInstance()
        expectedDate.time = SimpleDateFormat.getInstance().parse(entity.createdAt)!!

        val expectedResult = Memo(
            "1",
            "",
            "",
            expectedDate.time,
            false
        )

        Assert.assertEquals(expectedResult, actualResult)
    }


    @Test
    fun `from a model to a entity`() {

        val date = Calendar.getInstance()
        date.time = SimpleDateFormat.getInstance().parse("4/16/23, 1:46 PM")!!

        val model = Memo(
            "1",
            "",
            "",
            date.time,
            false
        )

        val actualResult = model.toEntity()

        val expectedResult = MemoEntity(
            1L,
            "",
            "",
            "4/16/23, 1:46 PM",
            0L
        )

        TestCase.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should set date string on entity to empty`() {

        val model = Memo(
            "1",
            "",
            "",
            null,
            false
        )

        val actualResult = model.toEntity()

        val expectedResult = MemoEntity(
            1L,
            "",
            "",
            "",
            0L
        )

        TestCase.assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `should set date to null on model`() {
        val entity = MemoEntity(
            1L,
            "",
            "",
            "4/16/23, ,. ,.fj1:46 PM",
            0L
        )

        val actualResult = entity.toModel()

        val expectedResult = Memo(
            "1",
            "",
            "",
            null,
            false
        )

        TestCase.assertEquals(expectedResult, actualResult)
    }

}