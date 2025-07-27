package com.pratiksha.rojgarihub.presentation.job.list_job.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.presentation.job.list_job.mapper.toJobUi
import com.pratiksha.rojgarihub.presentation.job.list_job.model.JobUi
import com.pratiksha.rojgarihub.ui.CashIcon
import com.pratiksha.rojgarihub.ui.JobDialog
import com.pratiksha.rojgarihub.ui.TimeIcon
import com.pratiksha.rojgarihub.ui.theme.RojgariHubTheme

@Composable
fun JobDetailCard(
    job: JobUi,
    onEditClick: () -> Unit,
    onDeleteClick: (JobUi) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier =
                Modifier
                    .background(Color.White.copy(alpha = 0.8f))
                    .padding(16.dp)
        ) {

            // Job title and status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = job.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Blue
                )
                if (job.status.lowercase() == "active") {
                    Box(
                        modifier = Modifier
                            .background(Color.Green.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Active", color = Color.Black, fontSize = 12.sp)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .background(
                                Color.DarkGray.copy(alpha = 0.3f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Inactive", color = Color.Black, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Job meta info row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(job.location, color = Color.Black, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(12.dp))

                Icon(
                    TimeIcon,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(job.type, color = Color.Black, fontSize = 14.sp)

                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    CashIcon,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(job.salary, color = Color.Black, fontSize = 14.sp)

            }

            Spacer(modifier = Modifier.height(12.dp))

            // Description
            Text(
                text = job.description,
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Requirements (Styled tags)
            FlowRow() {
                job.requirements.forEach {
                    Text(
                        text = it,
                        color = Color(0xFF2563EB),
                        modifier = Modifier
                            .background(Color.Blue.copy(alpha = 0.06f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Action Button
                TextButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text("Edit")
                }

                // Action Button
                TextButton(
                    onClick = {
                        showDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text("Delete")
                }
            }
            if (showDialog) {
                JobDialog(
                    title = "Delete Job?",
                    description = "Are you sure to delete `${job.title}` job?",
                    onDismiss = {
                        showDialog = false
                    },
                    primaryActionButton = {
                        TextButton(
                            onClick = {
                                onDeleteClick(job)
                                showDialog = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text("Delete")
                        }
                    },
                    secondaryActionButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Text("Cancel")
                        }
                    },
                    modifier = Modifier,
                )
            }
        }
    }
}


@Preview
@Composable
private fun JobDetailCardPreview() {
    RojgariHubTheme {
        JobDetailCard(
            job = Job(
                title = "Ok Job",
                description = "This is Info",
                requirements = listOf("ok1", "ok3"),
                salary = "5000",
                status = "active",
                id = "1",
                type = "fulltime",
                location = "Nepal"
            ).toJobUi(),
            onDeleteClick = {},
            onEditClick = {}
        )
    }
}