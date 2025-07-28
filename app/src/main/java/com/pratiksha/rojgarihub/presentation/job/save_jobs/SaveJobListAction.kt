package com.pratiksha.rojgarihub.presentation.job.save_jobs

import com.pratiksha.rojgarihub.presentation.job.model.JobUi


sealed interface SaveJobListAction {
    data class RemoveSavedJob(val jobUi: JobUi) : SaveJobListAction
    data object ApplyJobs : SaveJobListAction
    data object RefreshJobs : SaveJobListAction

    data object BackClick: SaveJobListAction

}
