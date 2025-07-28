package com.pratiksha.rojgarihub.presentation.job.list_job

import com.pratiksha.rojgarihub.presentation.job.model.JobUi


sealed interface ListJobAction {
    data object AddJobClick : ListJobAction
    data object SaveJobClick : ListJobAction
    data object LogoutClick : ListJobAction
    data class UpsertJob(val jobUi: JobUi) : ListJobAction
    data class DeleteJob(val jobUi: JobUi) : ListJobAction
    data class SaveJob(val jobUi: JobUi) : ListJobAction

    data object ApplyJobs : ListJobAction
    data object RefreshJobs : ListJobAction

}
