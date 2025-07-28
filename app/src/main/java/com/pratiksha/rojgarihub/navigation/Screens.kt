package com.pratiksha.rojgarihub.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screens {

    //auth navGraph
    @Serializable
    data object AuthGraph : Screens

    @Serializable
    data object LogIn : Screens

    @Serializable
    data object Register : Screens


    //Job navGraph
    @Serializable
    data object JobGraph : Screens

    @Serializable
    data object AddJob : Screens

    @Serializable
    data object JobList : Screens

    @Serializable
    data object SavedJobList : Screens

    @Serializable
    data class UpsertJob(val jobId: String) : Screens

}
