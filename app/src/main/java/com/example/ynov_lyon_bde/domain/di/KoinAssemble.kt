package com.example.ynov_lyon_bde.domain.di

import com.example.ynov_lyon_bde.domain.services.request.BdeApiService
import com.example.ynov_lyon_bde.domain.services.RedirectService
import com.example.ynov_lyon_bde.domain.services.request.ErrorManager
import com.example.ynov_lyon_bde.domain.viewmodel.signIn.SignInViewModel
import com.example.ynov_lyon_bde.domain.viewmodel.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dependencyInjectionModule = module {
    single { BdeApiService() }
    single { RedirectService() }
    single { ErrorManager() }
    viewModel { SignInViewModel() }
    viewModel { SignUpViewModel() }
}


