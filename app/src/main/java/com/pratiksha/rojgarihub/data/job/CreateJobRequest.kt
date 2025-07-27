package com.pratiksha.rojgarihub.data.job

import kotlinx.serialization.Serializable

@Serializable
data class CreateJobRequest(
    val title: String,
    val description: String,
    val requirements: List<String>,
    val location: String,
    val type: String,
    val salary: String,
    val status: String,
)
