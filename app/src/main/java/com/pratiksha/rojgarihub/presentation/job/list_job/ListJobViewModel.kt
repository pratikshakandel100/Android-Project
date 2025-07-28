package com.pratiksha.rojgarihub.presentation.job.list_job


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratiksha.rojgarihub.domain.auth.SessionStorage
import com.pratiksha.rojgarihub.domain.job.JobRepository
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.presentation.auth.UserType
import com.pratiksha.rojgarihub.presentation.job.mapper.toJob
import com.pratiksha.rojgarihub.presentation.job.mapper.toJobUi
import com.pratiksha.rojgarihub.ui.asUiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ListJobViewModel(
    private val jobRepository: JobRepository,
    private val sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope
) : ViewModel() {

    var state by mutableStateOf(ListJobState())
        private set

    private var _eventChannel = Channel<ListJobEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val userType =
                if (sessionStorage.getAuthInto()?.userType == "jobseeker") {
                    UserType.JOB_SEEKER
                } else if (sessionStorage.getAuthInto()?.userType == "employee") {
                    UserType.EMPLOYER
                } else {
                    UserType.EMPLOYER
                }
            state = state.copy(userType = userType)
            if (userType == UserType.EMPLOYER) {
                fetchAllEmployerJobs()
            } else {
                fetchAllJobs()
            }
        }
    }

    private suspend fun fetchAllEmployerJobs() {
        val result = jobRepository.fetchAllEmployerJobs()
        when (result) {
            is Result.Error -> ListJobEvent.Error(result.error.asUiText())
            is Result.Success -> {
                state = state.copy(jobs = result.data.map {
                    it.toJobUi()
                })
            }
        }
    }

    private suspend fun fetchAllJobs() {
        val result = jobRepository.fetchAllJobs()
        when (result) {
            is Result.Error -> ListJobEvent.Error(result.error.asUiText())
            is Result.Success -> {
                state = state.copy(jobs = result.data.map {
                    it.toJobUi()
                })
            }
        }
    }

    fun onAction(action: ListJobAction) {
        when (action) {
            is ListJobAction.DeleteJob -> {
                viewModelScope.launch {
                    jobRepository.deleteJobById(action.jobUi.id)
                }
            }

            is ListJobAction.UpsertJob -> {
                viewModelScope.launch {
                    jobRepository.upsertJob(action.jobUi.toJob())
                    refreshJobs() // Refresh after add/edit
                }
            }

            is ListJobAction.SaveJob -> {
                saveJob(action.jobUi.id)
            }

            ListJobAction.RefreshJobs -> {
                refreshJobs()
            }

            ListJobAction.LogoutClick -> {
                logout()
            }

            else -> Unit
        }
    }

    fun saveJob(jobId: String) {
        viewModelScope.launch {
            state = state.copy(isSavingJob = true)
            val result = jobRepository.saveJobById(jobId)
            state = state.copy(isSavingJob = false)
            when (result) {
                is Result.Error -> {
                    _eventChannel.send(ListJobEvent.Error(result.error.asUiText()))
                    state.copy(isSavingJob = false)
                }

                is Result.Success -> {
                    _eventChannel.send(ListJobEvent.SuccessSave)
                }
            }
        }
    }

    fun refreshJobs() {
        viewModelScope.launch {
            state = state.copy(isRefreshing = true)
            val result = jobRepository.fetchAllEmployerJobs()
            state = when (result) {
                is Result.Success -> state.copy(
                    jobs = result.data.map { it.toJobUi() },
                    isRefreshing = false
                )

                is Result.Error -> {
                    _eventChannel.send(ListJobEvent.Error(result.error.asUiText()))
                    state.copy(isRefreshing = false)
                }
            }
        }
    }

    fun logout() {
        applicationScope.launch {
            sessionStorage.setAuthInfo(null)
        }
    }
}
