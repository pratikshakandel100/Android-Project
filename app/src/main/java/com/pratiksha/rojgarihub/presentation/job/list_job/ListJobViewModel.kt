package com.pratiksha.rojgarihub.presentation.job.list_job


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratiksha.rojgarihub.domain.auth.SessionStorage
import com.pratiksha.rojgarihub.domain.job.JobRepository
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.presentation.job.list_job.mapper.toJob
import com.pratiksha.rojgarihub.presentation.job.list_job.mapper.toJobUi
import com.pratiksha.rojgarihub.ui.asUiText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ListJobViewModel(
    private val jobRepository: JobRepository,
    private  val  sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope
) : ViewModel() {

    var state by mutableStateOf(ListJobState())
        private set

    private var _eventChannel = Channel<ListJobEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val result = jobRepository.fetchJobs()
            when (result) {
                is Result.Error -> ListJobEvent.Error(result.error.asUiText())
                is Result.Success -> {
                    state = state.copy(jobs = result.data.map {
                        it.toJobUi()
                    })
                }
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

            ListJobAction.RefreshJobs -> {
                refreshJobs()
            }
            ListJobAction.LogoutClick -> {
                logout()
            }
            else -> Unit
        }
    }

    fun refreshJobs() {
        viewModelScope.launch {
            state = state.copy(isRefreshing = true)
            val result = jobRepository.fetchJobs()
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

    fun logout(){
        applicationScope.launch {
            sessionStorage.setAuthInfo(null)
        }
    }
}
