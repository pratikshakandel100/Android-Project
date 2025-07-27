package com.pratiksha.rojgarihub.presentation.job.list_job.model

data class JobUi(
    val id: String,
    val title: String,
    val description: String,
    val requirements: List<String>,
    val location: String,
    val type: String,
    val salary: String,
    val status: String
)
