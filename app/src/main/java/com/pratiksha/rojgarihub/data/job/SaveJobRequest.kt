package com.pratiksha.rojgarihub.data.job

import kotlinx.serialization.Serializable

@Serializable
data class SaveJobRequest(
    val jobId: String
)
