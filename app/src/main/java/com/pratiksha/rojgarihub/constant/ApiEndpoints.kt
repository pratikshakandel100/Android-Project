package com.pratiksha.rojgarihub.constant
object ApiEndpoints {
    const val ROJGARI_HUB_API_BASE_URL = "http://10.0.2.2:5000"

    const val AUTH_ENDPOINT = "/api/auth"
    const val JOB_ENDPOINT = "/api/jobs"
    const val SAVE_JOB_ENDPOINT = "/api/saved-jobs"

    const val EMPLOYER_JOB = "$JOB_ENDPOINT/employee/my-jobs"
    const val JOB_SEEKER_GET_ALL_SAVE_JOB_ENDPOINT = "$SAVE_JOB_ENDPOINT/my-saved-jobs"
    const val JOB_SEEKER_SAVE_JOB_ENDPOINT = "$SAVE_JOB_ENDPOINT/save"
    const val JOB_SEEKER_REMOVE_SAVED_JOB_ENDPOINT ="$SAVE_JOB_ENDPOINT/unsave"

    // Auth Endpoints
    const val EMPLOYER_LOGIN_ENDPOINT = "$AUTH_ENDPOINT/employee/login"
    const val EMPLOYER_REGISTER_ENDPOINT = "$AUTH_ENDPOINT/employee/register"

    const val JOB_SEEKER_LOGIN_ENDPOINT = "$AUTH_ENDPOINT/job-seeker/login"
    const val JOB_SEEKER_REGISTER_ENDPOINT = "$AUTH_ENDPOINT/job-seeker/register"

}
