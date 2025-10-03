package io.github.taetae98coding.diary.core.coroutines

import io.github.taetae98coding.diary.core.coroutines.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
public class CoroutinesModule {
    @Single
    @IoDispatcher
    internal fun providesIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Single
    internal fun providesApplicationScope(): CoroutineScope {
        return applicationScope()
    }
}

internal expect fun applicationScope(): CoroutineScope
