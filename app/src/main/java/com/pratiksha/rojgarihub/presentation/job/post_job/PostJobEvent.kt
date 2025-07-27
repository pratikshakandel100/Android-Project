package com.pratiksha.rojgarihub.presentation.job.post_job

import com.pratiksha.rojgarihub.ui.UiText

sealed interface PostJobEvent {
    data class Error(val error: UiText) : PostJobEvent
    data object PostJobSuccess : PostJobEvent
}