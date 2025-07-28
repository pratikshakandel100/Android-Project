package com.pratiksha.rojgarihub.data.job.mapper

import com.pratiksha.rojgarihub.data.job.JobDto
import com.pratiksha.rojgarihub.data.job.SaveJobDto
import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.domain.job.SavedJob

fun JobDto.toJob(): Job {
    return Job(
        id = id,
        title = title,
        description = description,
        requirements = requirements,
        location = location,
        type = type,
        salary = salary,
        status = status
    )
}

fun Job.toJobDto(): JobDto {
    return JobDto(
        id = id,
        title = title,
        description = description,
        requirements = requirements!!,
        location = location,
        type = type,
        salary = salary,
        status = status!!
    )
}

fun SaveJobDto.toSaveJob(): SavedJob {
    return SavedJob(
        id = id,
        title = title,
        description = description,
        location = location,
        type = type,
        salary = salary
    )
}