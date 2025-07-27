package com.pratiksha.rojgarihub.presentation.job.post_job

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratiksha.rojgarihub.R
import com.pratiksha.rojgarihub.ui.JobScaffold
import com.pratiksha.rojgarihub.ui.JobTopAppBar
import com.pratiksha.rojgarihub.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostJobScreenRoot(
    onSuccessfulPostJob: () -> Unit,
    onCancelClick: () -> Unit,
    viewModel: PostJobViewModel = koinViewModel<PostJobViewModel>()
) {
    val context = LocalContext.current
    ObserveAsEvents(flow = viewModel.events) { event ->
        when (event) {
            is PostJobEvent.Error -> {
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            PostJobEvent.PostJobSuccess -> {
                Toast.makeText(context, R.string.post_job_success, Toast.LENGTH_SHORT).show()
                onSuccessfulPostJob()
            }
        }
    }
    PostJobScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                PostJobAction.Cancel -> onCancelClick()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostJobScreen(
    state: PostJobState,
    onAction: (PostJobAction) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    JobScaffold(
        gradientEnabled = false,
        topAppBar = {
            JobTopAppBar(
                showBackButton = true,
                onBackClick = {
                    onAction(PostJobAction.Cancel)
                },
                title = "Post New Job"
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .padding(it)
        ) {

            OutlinedTextField(
                value = state.jobTitle,
                onValueChange = { onAction(PostJobAction.JobTitleChanged(it)) },
                label = { Text("Job Title *") },
                placeholder = { Text("e.g. Senior Software Engineer") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    value = state.jobType,
                    onValueChange = { onAction(PostJobAction.JobTypeChanged(it)) },
                    label = { Text("Job Type *") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                OutlinedTextField(
                    value = state.salaryRange,
                    onValueChange = { onAction(PostJobAction.SalaryRangeChanged(it)) },
                    label = { Text("Salary Range") },
                    placeholder = { Text("e.g. ₹15–25 LPA") },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = state.location,
                onValueChange = { onAction(PostJobAction.LocationChanged(it)) },
                label = { Text("Location *") },
                placeholder = { Text("e.g. Mumbai, India") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = state.jobDescription,
                onValueChange = { onAction(PostJobAction.JobDescriptionChanged(it)) },
                label = { Text("Job Description *") },
                placeholder = { Text("Describe the job role...") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = state.requirements,
                onValueChange = { onAction(PostJobAction.RequirementsChanged(it)) },
                label = { Text("Requirements *") },
                placeholder = { Text("e.g. React, Node.js, 3+ years experience") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 4
            )

            Spacer(Modifier.height(12.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = state.boostJob,
                    onCheckedChange = { onAction(PostJobAction.BoostJobToggled(it)) }
                )
                Text("Boost this job for more visibility")
            }

            Spacer(Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onAction(PostJobAction.Submit) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("+ Post Job", color = Color.White)
                }

                OutlinedButton(onClick = { onAction(PostJobAction.Cancel) }) {
                    Text("Cancel")
                }
            }
        }
    }

}
