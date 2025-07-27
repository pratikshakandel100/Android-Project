@file:OptIn(ExperimentalMaterial3Api::class)

package com.pratiksha.rojgarihub.presentation.job.list_job


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.pratiksha.rojgarihub.presentation.auth.login.LoginEvent
import com.pratiksha.rojgarihub.presentation.job.list_job.components.JobDetailCard
import com.pratiksha.rojgarihub.presentation.job.list_job.utils.DropdownItem
import com.pratiksha.rojgarihub.ui.JobDialog
import com.pratiksha.rojgarihub.ui.JobFloatingActionButton
import com.pratiksha.rojgarihub.ui.JobScaffold
import com.pratiksha.rojgarihub.ui.JobTopAppBar
import com.pratiksha.rojgarihub.ui.LogoIcon
import com.pratiksha.rojgarihub.ui.LogoutIcon
import com.pratiksha.rojgarihub.ui.ObserveAsEvents
import com.pratiksha.rojgarihub.ui.PersonIcon
import com.pratiksha.rojgarihub.ui.theme.RojgariHubTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun JobListScreenRoot(
    onAddJobClick: () -> Unit,
    onEditJobClick: (String) -> Unit,
    onLogOutClick: () -> Unit,
    viewModel: ListJobViewModel = koinViewModel<ListJobViewModel>()
) {
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ListJobEvent.NavigateToUpsertNote -> onEditJobClick(event.noteId)
            else -> Unit
        }

    }
    JobListScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                ListJobAction.AddJobClick -> onAddJobClick()
                ListJobAction.LogoutClick -> onLogOutClick()
                ListJobAction.ProfileClick -> {
                    Toast.makeText(context, "Not Available yet", Toast.LENGTH_SHORT).show()
                }

                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun JobListScreen(
    state: ListJobState,
    onAction: (ListJobAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(state = topAppBarState)
    JobScaffold(
        topAppBar = {
            JobTopAppBar(
                showBackButton = false,
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
                menuItems = listOf(
                    DropdownItem(icon = PersonIcon, title = stringResource(R.string.profile)),
                    DropdownItem(icon = LogoutIcon, title = stringResource(R.string.logout))
                ),
                onMenuItemClick = { index ->
                    when (index) {
                        0 -> onAction(ListJobAction.ProfileClick)
                        1 -> onAction(ListJobAction.LogoutClick)
                    }
                },
                modifier = Modifier
            )
        },
        floatingActionButton = {
            JobFloatingActionButton(
                icon = Icons.Default.Add,
                onClick = {
                    onAction(ListJobAction.AddJobClick)
                }
            )
        }
    ) { paddingValues ->
        val refreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

        SwipeRefresh(
            state = refreshState,
            onRefresh = { onAction(ListJobAction.RefreshJobs) }
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
                        JobDetailCard(
                            job = it,
                            onEditClick = { onAction(ListJobAction.UpsertJob(it)) },
                            onDeleteClick = {
                                onAction(ListJobAction.DeleteJob(it))
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
                                text = stringResource(R.string.empty_jobs),
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
        JobListScreen(
            state = ListJobState(),
            onAction = {}
        )
    }
}

