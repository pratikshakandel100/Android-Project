@file:OptIn(ExperimentalMaterial3Api::class)

package com.pratiksha.rojgarihub.presentation.job.save_jobs


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pratiksha.rojgarihub.R
import com.pratiksha.rojgarihub.presentation.job.components.SavedJobDetailCard
import com.pratiksha.rojgarihub.ui.JobScaffold
import com.pratiksha.rojgarihub.ui.JobTopAppBar
import com.pratiksha.rojgarihub.ui.LogoIcon
import com.pratiksha.rojgarihub.ui.ObserveAsEvents
import com.pratiksha.rojgarihub.ui.theme.RojgariHubTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedJobListScreenRoot(
    onBackClick: () -> Unit,
    viewModel: SavedJobListViewModel = koinViewModel<SavedJobListViewModel>()
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is SavedJobListEvent.SuccessRemoved -> {
                Toast.makeText(context, "Removed from save job", Toast.LENGTH_SHORT).show()
            }

            is SavedJobListEvent.SuccessApply -> {
                Toast.makeText(context, "Application Submitted", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }

    }
    SavedJobListScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                is SaveJobListAction.BackClick -> {
                    onBackClick()
                }

                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun SavedJobListScreen(
    state: SavedJobListState,
    onAction: (SaveJobListAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
    JobScaffold(
        topAppBar = {
            JobTopAppBar(
                showBackButton = true,
                title = stringResource(R.string.app_name),
                scrollBehavior = scrollBehavior,
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                },
                onBackClick = {
                    onAction(SaveJobListAction.BackClick)
                },
                modifier = Modifier
            )
        },
    ) { paddingValues ->
        val refreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

        SwipeRefresh(
            state = refreshState,
            onRefresh = { onAction(SaveJobListAction.RefreshJobs) }
        ) {
            if (!state.jobs.isEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .padding(horizontal = 16.dp),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = state.jobs,
                        key = { it.id }
                    ) {
                        SavedJobDetailCard(
                            job = it,
                            onRemovedSaveJobClick = {
                                onAction(SaveJobListAction.RemoveSavedJob(it))
                            },
                            onApplyJob = {
                                onAction(SaveJobListAction.ApplyJobs)
                            },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .padding(horizontal = 16.dp),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier,
                                text = stringResource(R.string.empty_save_jobs),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun JobListScreenPreview() {
    RojgariHubTheme {
        SavedJobListScreen(
            state = SavedJobListState(),
            onAction = {}
        )
    }
}

