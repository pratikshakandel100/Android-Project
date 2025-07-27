package com.pratiksha.rojgarihub.domain.job

data class Job(
    val id: String,
    val title: String,
    val description: String,
    val requirements: List<String>,
    val location: String,
    val type: String,
    val salary: String,
    val status: String
)