package com.pratiksha.rojgarihub.domain.job

import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.EmptyResult
import com.pratiksha.rojgarihub.domain.util.Result

interface JobRepository {
    suspend fun fetchJobs(): Result<List<Job>, DataError>
    suspend fun upsertJob(job: Job): EmptyResult<DataError>
    suspend fun deleteJobById(id: String): EmptyResult<DataError>
}