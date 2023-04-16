package com.pvsb.datasource.dto

@kotlinx.serialization.Serializable
data class UserDTO(
    val password: String,
    val hasSeenOnBoardingAlready: Boolean
)
