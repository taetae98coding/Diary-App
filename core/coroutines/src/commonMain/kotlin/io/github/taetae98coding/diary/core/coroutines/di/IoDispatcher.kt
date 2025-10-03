package io.github.taetae98coding.diary.core.coroutines.di

import org.koin.core.annotation.Named

@Target(
    allowedTargets = [
        AnnotationTarget.FIELD,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.FUNCTION,
    ],
)
@Named
public annotation class IoDispatcher
