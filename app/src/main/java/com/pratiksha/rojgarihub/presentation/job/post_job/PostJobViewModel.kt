package com.pratiksha.rojgarihub.presentation.job.post_job

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.domain.job.JobRepository
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.presentation.auth.login.LoginEvent
import com.pratiksha.rojgarihub.presentation.job.mapper.toJob
import com.pratiksha.rojgarihub.presentation.job.model.JobUi
import com.pratiksha.rojgarihub.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PostJobViewModel(
    private val jobRepository: JobRepository
) : ViewModel() {

    var state by mutableStateOf(PostJobState())
        private set

    private var _eventChannel = Channel<PostJobEvent>()
    val events = _eventChannel.receiveAsFlow()

    fun onAction(action: PostJobAction) {
        when (action) {
            is PostJobAction.JobTitleChanged -> state = state.copy(jobTitle = action.value)
            is PostJobAction.JobTypeChanged -> state = state.copy(jobType = action.value)
            is PostJobAction.LocationChanged -> state = state.copy(location = action.value)
            is PostJobAction.SalaryRangeChanged -> state = state.copy(salaryRange = action.value)
            is PostJobAction.JobDescriptionChanged -> state =
                state.copy(jobDescription = action.value)

            is PostJobAction.RequirementsChanged -> state = state.copy(requirements = action.value)
            is PostJobAction.BoostJobToggled -> state = state.copy(boostJob = action.checked)
            PostJobAction.Submit -> validateAndSubmit()
            PostJobAction.Cancel -> state = PostJobState() // Reset form
        }
    }

    private fun validateAndSubmit() {
        if (state.jobTitle.isBlank() || state.location.isBlank() || state.jobDescription.isBlank() || state.requirements.isBlank()) {
            state = state.copy(errorMessage = "Please fill all required fields.")
            return
        }
        viewModelScope.launch {
            val response = jobRepository.upsertJob(job = JobUi(
                id = "",
                title = state.jobTitle,
                description = state.jobDescription,
                requirements = state.requirements.split(","),
                location = state.location,
                type = state.jobType,
                salary = state.salaryRange,
                status = ""
            ).toJob())
            println(response)
            when(response){
                is Result.Error -> {
                    _eventChannel.send(PostJobEvent.Error(response.error.asUiText()))
                }
                is Result.Success -> {
                    _eventChannel.send(PostJobEvent.PostJobSuccess)
                }
            }
        }
        state = state.copy(errorMessage = null)
    }
}
