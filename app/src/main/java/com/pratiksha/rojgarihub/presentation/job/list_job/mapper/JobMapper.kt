package com.pratiksha.rojgarihub.presentation.job.list_job.mapper

import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.presentation.job.list_job.model.JobUi

fun Job.toJobUi(): JobUi {
    return JobUi(
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

fun JobUi.toJob(): Job {
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