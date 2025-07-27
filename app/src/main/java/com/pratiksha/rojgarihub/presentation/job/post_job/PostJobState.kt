package com.pratiksha.rojgarihub.presentation.job.post_job


data class PostJobState(
    val jobTitle: String = "",
    val jobType: String = "Full-time",
    val location: String = "",
    val salaryRange: String = "",
    val jobDescription: String = "",
    val requirements: String = "",
    val boostJob: Boolean = false,
    val errorMessage: String? = null
)
