package com.pratiksha.rojgarihub.data.job

import kotlinx.serialization.Serializable

@Serializable
data class JobResponse(
    val jobs: List<JobDto>
)