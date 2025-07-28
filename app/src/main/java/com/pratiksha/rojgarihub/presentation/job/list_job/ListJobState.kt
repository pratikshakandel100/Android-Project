package com.pratiksha.rojgarihub.presentation.job.list_job

import com.pratiksha.rojgarihub.presentation.auth.UserType
import com.pratiksha.rojgarihub.presentation.job.model.JobUi

data class ListJobState(
    val jobs: List<JobUi> = emptyList(),
    val isRefreshing: Boolean = false,
    val isSavingJob: Boolean = false,
    val userType: UserType = UserType.EMPLOYER
)
