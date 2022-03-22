package com.kexan.catfactsandroidapp.dto

import java.sql.Timestamp

data class Fact(
    val id: Long = 0,
    val _id: String = "",
    val user: String = "",
    val text: String = "",
    val type: String = "",
    val updatedAt: String = ""
)