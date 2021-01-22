package com.example.ynov_lyon_bde.domain.di

import com.example.ynov_lyon_bde.data.repository.TestRepository
import com.example.ynov_lyon_bde.domain.services.BdeApiService
import com.example.ynov_lyon_bde.domain.services.RedirectService
import com.example.ynov_lyon_bde.domain.viewmodel.AuthenticationViewModel
import com.example.ynov_lyon_bde.domain.viewmodel.SignInViewModel
import com.example.ynov_lyon_bde.domain.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dependencyInjectionModule = module {
    single { BdeApiService() }
    single { RedirectService() }

    single { TestRepository(get<BdeApiService>())}

    viewModel { SignUpViewModel() }
    viewModel { SignInViewModel() }
    viewModel { AuthenticationViewModel() }
}


