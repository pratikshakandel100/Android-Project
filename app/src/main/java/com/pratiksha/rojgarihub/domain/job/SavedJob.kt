package com.pratiksha.rojgarihub.domain.job

data class SavedJob(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val type: String,
    val salary: String
)