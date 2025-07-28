package com.pratiksha.rojgarihub.presentation.job.save_jobs


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pratiksha.rojgarihub.domain.job.JobRepository
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.presentation.job.mapper.toJobUi
import com.pratiksha.rojgarihub.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SavedJobListViewModel(
    private val jobRepository: JobRepository
) : ViewModel() {

    var state by mutableStateOf(SavedJobListState())
        private set

    private var _eventChannel = Channel<SavedJobListEvent>()
    val events = _eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            val result = jobRepository.getAllSavedJob()
            when (result) {
                is Result.Error -> SavedJobListEvent.Error(result.error.asUiText())
                is Result.Success -> {
                    state = state.copy(jobs = result.data.map {
                        it.toJobUi()
                    })
                }
            }
        }
    }

    fun onAction(action: SaveJobListAction) {
        when (action) {
            is SaveJobListAction.RemoveSavedJob -> {
                viewModelScope.launch {
                    jobRepository.removeSavedJobById(action.jobUi.id)
                }
            }

            SaveJobListAction.RefreshJobs -> {
                refreshJobs()
            }

            else -> Unit
        }
    }

    fun refreshJobs() {
        viewModelScope.launch {
            state = state.copy(isRefreshing = true)
            val result = jobRepository.getAllSavedJob()
            state = when (result) {
                is Result.Success -> state.copy(
                    jobs = result.data.map { it.toJobUi() },
                    isRefreshing = false
                )

                is Result.Error -> {
                    _eventChannel.send(SavedJobListEvent.Error(result.error.asUiText()))
                    state.copy(isRefreshing = false)
                }
            }
        }
    }

}
