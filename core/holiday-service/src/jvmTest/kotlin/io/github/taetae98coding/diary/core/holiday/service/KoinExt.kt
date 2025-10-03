package io.github.taetae98coding.diary.core.holiday.service

import io.githbu.taetae98coding.diary.core.holiday.service.HolidayServiceModule
import io.github.taetae98coding.diary.core.coroutines.di.IoDispatcher
import io.github.taetae98coding.diary.core.ktor.client.KtorClientModule
import io.github.taetae98coding.diary.core.ktor.client.di.ApiEngine
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayApiKey
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayApiUrl
import io.ktor.client.engine.HttpClientEngine
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module

fun fakeHolidayClientModule(
    engine: HttpClientEngine,
): Module {
    return module {
        includes(
            HolidayServiceModule().module,
            KtorClientModule().module,
            module {
                single(qualifier = StringQualifier(ApiEngine::class.qualifiedName.orEmpty())) { engine }
                single(qualifier = StringQualifier(HolidayApiUrl::class.qualifiedName.orEmpty())) { "" }
                single(qualifier = StringQualifier(HolidayApiKey::class.qualifiedName.orEmpty())) { "" }
                single<CoroutineDispatcher>(qualifier = StringQualifier(IoDispatcher::class.qualifiedName.orEmpty())) { UnconfinedTestDispatcher() }
            },
        )
    }
}
