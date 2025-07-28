package com.pratiksha.admin.rojgarihub

import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.domain.job.JobRepository
import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.EmptyResult
import com.pratiksha.rojgarihub.domain.util.Result
import kotlin.collections.toMutableList

class FakeJobRepository : JobRepository {

    private var jobs: MutableList<Job> = mutableListOf()
    private var shouldFail = false

    fun setFakeJobs(fakeJobs: List<Job>) {
        jobs = fakeJobs.toMutableList()
    }

    fun setShouldFail(value: Boolean) {
        shouldFail = value
    }

    override suspend fun fetchJobs(): Result<List<Job>, DataError> {
        return if (shouldFail) {
            Result.Error(DataError.Network.UNKNOWN)
        } else {
            Result.Success(jobs)
        }
    }

    override suspend fun upsertJob(job: Job): EmptyResult<DataError> {
        return if (shouldFail) {
            Result.Error(DataError.Network.SERVER_ERROR)
        } else {
            jobs.removeIf { it.id == job.id }
            jobs.add(job)
            Result.Success(Unit)
        }
    }

    override suspend fun deleteJobById(id: String): EmptyResult<DataError> {
        return if (shouldFail) {
            Result.Error(DataError.Network.UNAUTHORIZED)
        } else {
            jobs.removeIf { it.id == id }
            Result.Success(Unit)
        }
    }
}
