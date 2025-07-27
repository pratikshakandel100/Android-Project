package com.pratiksha.rojgarihub.presentation.job.list_job

import com.pratiksha.rojgarihub.presentation.job.list_job.model.JobUi


sealed interface ListJobAction {
    data object AddJobClick : ListJobAction
    data object ProfileClick : ListJobAction
    data object LogoutClick : ListJobAction
    data class UpsertJob(val jobUi: JobUi) : ListJobAction
    data class DeleteJob(val jobUi: JobUi) : ListJobAction

    data object RefreshJobs : ListJobAction

}
