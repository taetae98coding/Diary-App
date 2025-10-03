package io.github.taetae98coding.diary.core.ktor.client.di

import org.koin.core.annotation.Named

@Target(
    allowedTargets = [
        AnnotationTarget.FIELD,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.FUNCTION,
    ],
)
@Named
public annotation class DiaryClient
