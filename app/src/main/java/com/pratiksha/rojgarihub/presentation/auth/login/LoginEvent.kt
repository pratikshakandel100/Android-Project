package com.pratiksha.rojgarihub.presentation.auth.login

import com.pratiksha.rojgarihub.ui.UiText

sealed interface LoginEvent {
    data class Error(val error: UiText) : LoginEvent
    data object LoginSuccess : LoginEvent
}