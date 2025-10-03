package io.github.taetae98coding.diary.core.ip.service

import io.github.taetae98coding.diary.core.ktor.client.KtorClientModule
import io.github.taetae98coding.diary.core.ktor.client.di.ApiEngine
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import org.koin.ksp.generated.module

fun fakeIpClientModule(
    engine: HttpClientEngine,
): Module {
    return module {
        includes(
            IpServiceModule().module,
            KtorClientModule().module,
            module {
                single(qualifier = StringQualifier(ApiEngine::class.qualifiedName.orEmpty())) { engine }
            },
        )
    }
}
