package com.pratiksha.rojgarihub.presentation.auth.register

import com.pratiksha.rojgarihub.ui.UiText

sealed interface RegisterEvent {
    data class Error(val error: UiText) : RegisterEvent
    data object RegistrationSuccess : RegisterEvent
}
