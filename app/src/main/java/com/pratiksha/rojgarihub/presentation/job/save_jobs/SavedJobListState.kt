package com.pratiksha.rojgarihub.presentation.job.save_jobs

import com.pratiksha.rojgarihub.presentation.job.model.JobUi

data class SavedJobListState(
    val jobs: List<JobUi> = emptyList(),
    val isRefreshing: Boolean = false,
)
