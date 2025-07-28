package com.pratiksha.rojgarihub.data.job

import kotlinx.serialization.Serializable

@Serializable
data class SaveJobResponse(
    val savedJobs: List<SavedJob>
)

@Serializable
data class SavedJob(
    val job: SaveJobDto
)

