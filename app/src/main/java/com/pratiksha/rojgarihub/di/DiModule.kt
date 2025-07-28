package com.pratiksha.rojgarihub.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.pratiksha.rojgarihub.RojgariHubApp
import com.pratiksha.rojgarihub.data.auth.AuthRepoImpl
import com.pratiksha.rojgarihub.data.auth.EncryptedSessionStorage
import com.pratiksha.rojgarihub.data.job.JobRepoImpl
import com.pratiksha.rojgarihub.domain.auth.AuthRepository
import com.pratiksha.rojgarihub.networking.HttpClientFactory
import com.pratiksha.rojgarihub.domain.auth.SessionStorage
import com.pratiksha.rojgarihub.domain.job.JobRepository
import com.pratiksha.rojgarihub.presentation.auth.login.LoginViewModel
import com.pratiksha.rojgarihub.presentation.auth.register.RegisterViewModel
import com.pratiksha.rojgarihub.presentation.job.list_job.ListJobViewModel
import com.pratiksha.rojgarihub.presentation.job.post_job.PostJobViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


val diModule = module {

    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    single<SharedPreferences> {
        EncryptedSharedPreferences(
            context = androidApplication(),
            fileName = "rojgarihub_pref",
            masterKey = MasterKey(androidApplication()),
            prefKeyEncryptionScheme = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            prefValueEncryptionScheme = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<CoroutineScope> {
        (androidApplication() as RojgariHubApp).applicationScope
    }

    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::PostJobViewModel)
    viewModelOf(::ListJobViewModel)

    singleOf(::AuthRepoImpl).bind<AuthRepository>()
    singleOf(::JobRepoImpl).bind<JobRepository>()

}