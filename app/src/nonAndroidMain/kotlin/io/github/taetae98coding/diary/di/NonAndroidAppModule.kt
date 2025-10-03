package io.github.taetae98coding.diary.di

import io.github.taetae98coding.diary.core.work.WorkNonAndroidModule
import org.koin.core.annotation.Module

@Module(
    includes = [
        WorkNonAndroidModule::class,
    ],
)
internal class NonAndroidAppModule
