package com.pratiksha.admin.rojgarihub

import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.presentation.job.list_job.ListJobViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllJobsTest {

    private lateinit var viewModel: ListJobViewModel
    private lateinit var fakeJobRepository: FakeJobRepository
    private lateinit var fakeSessionStorage: FakeSessionStorage
    private lateinit var fakeApplicationScope: CoroutineScope

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        fakeJobRepository = FakeJobRepository()
        fakeSessionStorage = FakeSessionStorage()
        fakeApplicationScope = CoroutineScope(UnconfinedTestDispatcher())

        viewModel = ListJobViewModel(
            jobRepository = fakeJobRepository,
            sessionStorage = fakeSessionStorage,
            applicationScope = fakeApplicationScope
        )
    }

    @Test
    fun `getAllJobs returns job list successfully`() = runTest {
        // Arrange
        val mockJobs = listOf(
            Job(
                id = "1",
                title = "Backend Developer",
                description = "Node.js API Dev",
                requirements = listOf("Node.js", "MongoDB"),
                location = "Remote",
                type = "Full-time",
                salary = "$2000",
                status = "active"
            ),
            Job(
                id = "2",
                title = "Frontend Developer",
                description = "React UI Dev",
                requirements = listOf("React", "TypeScript"),
                location = "Kathmandu",
                type = "Part-time",
                salary = "$1000",
                status = "active"
            )
        )
        fakeJobRepository.setFakeJobs(mockJobs)

        // Act
        viewModel.refreshJobs()

        // Assert
        val jobs = viewModel.state.jobs
        assertEquals(2, jobs.size)
        assertEquals("Backend Developer", jobs[0].title)
        assertEquals("Frontend Developer", jobs[1].title)
    }
}
