package com.example.maps.di

import android.content.ContentResolver
import android.content.Context
import com.example.maps.data.service.AuthServiceImpl
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.definition
import org.koin.test.verify.injectedParameters
import org.koin.test.verify.verify

class AppModuleTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkKoinModule() {
        appModule.verify(
            injections = injectedParameters(
                definition<ContentResolver>(Context::class),
                definition<AuthServiceImpl>(Context::class),
                definition<AuthServiceImpl>(FirebaseAuthUIAuthenticationResult::class)
            )
        )
    }
}