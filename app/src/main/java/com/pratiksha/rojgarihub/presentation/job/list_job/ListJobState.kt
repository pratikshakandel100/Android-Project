package com.pratiksha.rojgarihub.presentation.job.list_job

import com.pratiksha.rojgarihub.presentation.job.list_job.model.JobUi

data class ListJobState(
    val jobs: List<JobUi> = emptyList(),
    val isRefreshing: Boolean = false
)
