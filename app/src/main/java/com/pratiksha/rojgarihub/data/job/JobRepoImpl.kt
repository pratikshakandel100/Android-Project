package com.pratiksha.rojgarihub.data.job

import com.pratiksha.rojgarihub.constant.ApiEndpoints
import com.pratiksha.rojgarihub.data.job.mapper.toJob
import com.pratiksha.rojgarihub.data.job.mapper.toSaveJob
import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.domain.job.JobRepository
import com.pratiksha.rojgarihub.domain.job.SavedJob
import com.pratiksha.rojgarihub.domain.util.DataError
import com.pratiksha.rojgarihub.domain.util.EmptyResult
import com.pratiksha.rojgarihub.domain.util.Result
import com.pratiksha.rojgarihub.domain.util.mapToResult
import com.pratiksha.rojgarihub.networking.delete
import com.pratiksha.rojgarihub.networking.get
import com.pratiksha.rojgarihub.networking.post
import com.pratiksha.rojgarihub.networking.put
import io.ktor.client.HttpClient

class JobRepoImpl(
    private val httpClient: HttpClient
) : JobRepository {
    override suspend fun fetchAllEmployerJobs(): Result<List<Job>, DataError> {
        return httpClient.get<JobResponse>(ApiEndpoints.EMPLOYER_JOB)
            .mapToResult { jobResponse ->
                jobResponse.jobs.map {
                    it.toJob()
                }
            }
    }

    override suspend fun fetchAllJobs(): Result<List<Job>, DataError> {
        return httpClient.get<JobResponse>(ApiEndpoints.JOB_ENDPOINT)
            .mapToResult { jobResponse ->
                jobResponse.jobs.map {
                    it.toJob()
                }
            }
    }

    override suspend fun upsertJob(job: Job): EmptyResult<DataError> {
        return if (job.id.isEmpty()) {
            httpClient.post(
                route = ApiEndpoints.JOB_ENDPOINT,
                body = CreateJobRequest(
                    title = job.title,
                    description = job.description,
                    requirements = job.requirements!!,
                    salary = job.salary,
                    status = job.status!!,
                    location = job.location,
                    type = job.type
                )
            )
        } else {
            httpClient.put(
                route = ApiEndpoints.JOB_ENDPOINT + "/${job.id}",
                body = UpsertJobRequest(
                    id = job.id,
                    title = job.title,
                    description = job.description,
                    requirements = job.requirements!!,
                    salary = job.salary,
                    status = job.status!!,
                    location = job.location,
                    type = job.type
                )
            )
        }
    }

    override suspend fun deleteJobById(id: String): EmptyResult<DataError.Network> {
        return httpClient.delete(route = ApiEndpoints.JOB_ENDPOINT + "/$id")
    }

    override suspend fun saveJobById(id: String): EmptyResult<DataError.Network> {
        return httpClient.post<SaveJobRequest, Unit>(
            route = ApiEndpoints.JOB_SEEKER_SAVE_JOB_ENDPOINT,
            body = SaveJobRequest(id)
        )
    }

    override suspend fun applyJob(id: String): EmptyResult<DataError> {
        return httpClient.post(route = ApiEndpoints.JOB_SEEKER_SAVE_JOB_ENDPOINT, body = id)
    }

    override suspend fun getAllSavedJob(): Result<List<SavedJob>, DataError.Network> {
        return httpClient.get<SaveJobResponse>(route = ApiEndpoints.JOB_SEEKER_GET_ALL_SAVE_JOB_ENDPOINT)
            .mapToResult { jobResponse ->
                jobResponse.savedJobs.map {
                    it.job.toSaveJob()
                }
            }
    }

    override suspend fun removeSavedJobById(id: String): EmptyResult<DataError.Network> {
        return httpClient.delete(route = ApiEndpoints.JOB_SEEKER_REMOVE_SAVED_JOB_ENDPOINT + "/$id")
    }

}