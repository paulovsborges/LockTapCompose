package com.pvsb.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date

fun capitalizeFullName(fullName: String): String {

    if (fullName.isEmpty()) return fullName

    var capitalizedName = ""
    val splitString = fullName.trim().split(" ")

    splitString.forEach {

        val stringToManipulate = it.removeRange(0, 1)

        val firstCapitalizedChar = it.first().uppercase()

        capitalizedName += "$firstCapitalizedChar$stringToManipulate "
    }

    return capitalizedName.trimEnd()
}

fun getFirstLettersFromFullName(fullName: String): String {

    if (fullName.isEmpty()) return fullName

    var letters = ""
    val maxLettersSizeCount = 2

    val splitString = fullName.trim().split(" ")

    val firstLetter = splitString.first().first()
    letters += firstLetter

    if (splitString.size > 1) {
        val secondLetter = splitString.last().first()
        letters += secondLetter
    }

    return letters.uppercase().take(maxLettersSizeCount)
}

fun Date.formatToStringDate(): String {
    return SimpleDateFormat.getInstance().format(this)
}