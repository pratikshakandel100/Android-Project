package com.pratiksha.rojgarihub.presentation.job.list_job

import com.pratiksha.rojgarihub.ui.UiText

sealed interface ListJobEvent {
    data class Error(val error: UiText) : ListJobEvent
    data object Success : ListJobEvent
    data class NavigateToUpsertNote(val noteId: String) : ListJobEvent
}