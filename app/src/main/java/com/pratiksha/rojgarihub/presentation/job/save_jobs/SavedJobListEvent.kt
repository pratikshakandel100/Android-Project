package com.pratiksha.rojgarihub.presentation.job.save_jobs

import com.pratiksha.rojgarihub.ui.UiText

sealed interface SavedJobListEvent {
    data class Error(val error: UiText) : SavedJobListEvent
    data object SuccessRemoved : SavedJobListEvent
    data object SuccessApply : SavedJobListEvent
}