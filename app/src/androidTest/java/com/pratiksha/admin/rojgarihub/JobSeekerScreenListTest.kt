package com.pratiksha.admin.rojgarihub


import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pratiksha.rojgarihub.MainActivity
import com.pratiksha.rojgarihub.presentation.auth.UserType
import com.pratiksha.rojgarihub.presentation.job.list_job.JobListScreen
import com.pratiksha.rojgarihub.presentation.job.list_job.ListJobAction
import com.pratiksha.rojgarihub.presentation.job.list_job.ListJobState
import com.pratiksha.rojgarihub.presentation.job.model.JobUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class JobSeekerJobListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_job_seeker_ui_elements_and_interaction() {
        // State values
        val jobClicked = mutableListOf<String>()
        val savedJobClicked = mutableListOf<String>()
        val applied = mutableListOf<Boolean>()
        val menuClicked = mutableListOf<String>()

        val testJobs = listOf(
            JobUi(
                id = "1",
                title = "Frontend Developer",
                description = "Build UI in React",
                requirements = listOf("React", "Tailwind"),
                location = "Kathmandu",
                type = "Remote",
                salary = "800 USD",
                status = "active"
            ),
            JobUi(
                id = "2",
                title = "Android Developer",
                description = "Jetpack Compose work",
                requirements = listOf("Kotlin", "Compose"),
                location = "Pokhara",
                type = "Full-time",
                salary = "1200 USD",
                status = "active"
            )
        )

        composeTestRule.setContent {
            JobListScreen(
                state = ListJobState(
                    jobs = testJobs,
                    userType = UserType.JOB_SEEKER
                ),
                onAction = {
                    when (it) {
                        is ListJobAction.ApplyJobs -> applied.add(true)
                        is ListJobAction.SaveJob -> savedJobClicked.add(it.jobUi.id)
                        is ListJobAction.UpsertJob -> jobClicked.add(it.jobUi.id)
                        is ListJobAction.SaveJobClick -> menuClicked.add("Saved")
                        is ListJobAction.LogoutClick -> menuClicked.add("Logout")
                        else -> {}
                    }
                }
            )
        }

        // Assert job titles are visible
        composeTestRule.onNodeWithText("Frontend Developer").assertIsDisplayed()
        composeTestRule.onNodeWithText("Android Developer").assertIsDisplayed()

        // Click Save on 1st job
        composeTestRule.onAllNodesWithText("Save")[0].performClick()

        // Click Apply on 2nd job
        composeTestRule.onAllNodesWithText("Apply")[1].performClick()

        // Open menu
        composeTestRule.onNodeWithContentDescription("More options").performClick()

        // Click "My Saved Jobs"
        composeTestRule.onNodeWithText("My Saved Jobs").performClick()

        // Click "Logout"
        composeTestRule.onNodeWithContentDescription("More options").performClick()
        composeTestRule.onNodeWithText("Logout").performClick()

        // Assertions
        composeTestRule.runOnIdle {
            assertTrue(savedJobClicked.contains("1"))
            assertTrue(applied.isEmpty())
            assertEquals(listOf("Saved", "Logout"), menuClicked)
        }
    }
}
