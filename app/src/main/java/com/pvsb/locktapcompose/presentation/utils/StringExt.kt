package com.pvsb.locktapcompose.presentation.utils

fun capitalizeFullName(fullName: String): String {

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