package com.pratiksha.rojgarihub.domain.job

import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.EmptyResult
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.domain.job.SavedJob

interface JobRepository {
    suspend fun fetchAllJobs(): Result<List<Job>, DataError>
    suspend fun fetchAllEmployerJobs(): Result<List<Job>, DataError>
    suspend fun upsertJob(job: Job): EmptyResult<DataError>
    suspend fun deleteJobById(id: String): EmptyResult<DataError>

    suspend fun getAllSavedJob(): Result<List<SavedJob>,DataError.Network>
    suspend fun removeSavedJobById(id: String): EmptyResult<DataError.Network>
    suspend fun saveJobById(id: String): EmptyResult<DataError.Network>
    suspend fun applyJob(id: String): EmptyResult<DataError>
}