package com.pratiksha.rojgarihub.constant
object ApiEndpoints {
    const val ROJGARI_HUB_API_BASE_URL = "http://10.0.2.2:5000"

    const val AUTH_ENDPOINT = "/api/auth"
    const val JOB_ENDPOINT = "/api/jobs"

    const val EMPLOYEE_JOB = "$JOB_ENDPOINT/employee/my-jobs"

    // Auth Endpoints
    const val LOGIN_ENDPOINT = "$AUTH_ENDPOINT/employee/login"
    const val REGISTER_ENDPOINT = "$AUTH_ENDPOINT/employee/register"

}
