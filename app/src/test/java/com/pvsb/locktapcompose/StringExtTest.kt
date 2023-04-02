package com.pvsb.locktapcompose

import com.pvsb.locktapcompose.presentation.utils.capitalizeFullName
import com.pvsb.locktapcompose.presentation.utils.getFirstLettersFromFullName
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

class StringExtTest {

    @RunWith(Parameterized::class)
    class ParameterizedTestCapitalizeFullName(
        private val data: DataForParameterizedTestCapitalizedString
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data() = listOf(
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        "paulo borges",
                        "Paulo Borges"
                    )
                ),
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        "paulo",
                        "Paulo"
                    )
                )
            )
        }

        @Test
        fun `should capitalize input string`() {
            val actualResult = capitalizeFullName(data.input)
            assertEquals(data.output, actualResult)
        }
    }

    @RunWith(Parameterized::class)
    class ParameterizedTestGetFirstLettersFromFullName(
        private val data: DataForParameterizedTestCapitalizedString
    ) {

        companion object {
            @JvmStatic
            @Parameterized.Parameters
            fun data() = listOf(
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        "paulo borges",
                        "PB"
                    )
                ),
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        "paulo",
                        "P"
                    )
                ),
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        "john doe john doe da silva sauro",
                        "JS"
                    )
                ),
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        "name with end space ",
                        "NS"
                    )
                ),
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        " name with start space",
                        "NS"
                    )
                ),
                arrayOf(
                    DataForParameterizedTestCapitalizedString(
                        " name with end and start spaces ",
                        "NS"
                    )
                )
            )
        }

        @Test
        fun `should get the first letter from first and last word in the input string`() {
            val actualResult = getFirstLettersFromFullName(data.input)
            assertEquals(data.output, actualResult)
        }
    }

    data class DataForParameterizedTestCapitalizedString(
        val input: String,
        val output: String
    )
}
