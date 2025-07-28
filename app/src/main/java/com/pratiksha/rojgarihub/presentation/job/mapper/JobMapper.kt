package com.pratiksha.rojgarihub.presentation.job.mapper

import android.R.attr.description
import android.R.attr.type
import com.pratiksha.rojgarihub.domain.job.SavedJob
import com.pratiksha.rojgarihub.domain.job.Job
import com.pratiksha.rojgarihub.presentation.job.model.JobUi

fun Job.toJobUi(): JobUi {
    return JobUi(
        id = id,
        title = title,
        description = description,
        requirements = requirements,
        location = location,
        type = type,
        salary = salary,
        status = status!!
    )
}

fun SavedJob.toJobUi(): JobUi {
    return JobUi(
        id = id,
        title = title,
        description = description,
        location = location,
        type = type,
        salary = salary,
        requirements = emptyList(),
        status = ""
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

fun JobUi.toSaveJob(): SavedJob {
    return SavedJob(
        id = id,
        title = title,
        description = description,
        location = location,
        type = type,
        salary = salary
    )
}