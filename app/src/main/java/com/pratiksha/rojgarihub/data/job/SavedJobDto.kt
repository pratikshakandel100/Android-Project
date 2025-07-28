package com.pratiksha.rojgarihub.data.job

import kotlinx.serialization.Serializable

@Serializable
data class SaveJobDto(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val type: String,
    val salary: String,
)