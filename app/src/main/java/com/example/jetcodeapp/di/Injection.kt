package com.example.jetcodeapp.di

import com.example.jetcodeapp.data.CodeRepository

object Injection {
    fun provideRepository(): CodeRepository {
        return CodeRepository.getInstance()
    }
}