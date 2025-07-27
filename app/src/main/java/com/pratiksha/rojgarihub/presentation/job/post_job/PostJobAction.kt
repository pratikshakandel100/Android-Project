package com.pratiksha.rojgarihub.presentation.job.post_job

sealed interface PostJobAction {
    data class JobTitleChanged(val value: String) : PostJobAction
    data class JobTypeChanged(val value: String) : PostJobAction
    data class LocationChanged(val value: String) : PostJobAction
    data class SalaryRangeChanged(val value: String) : PostJobAction
    data class JobDescriptionChanged(val value: String) : PostJobAction
    data class RequirementsChanged(val value: String) : PostJobAction
    data class BoostJobToggled(val checked: Boolean) : PostJobAction
    object Submit : PostJobAction
    object Cancel : PostJobAction
}
