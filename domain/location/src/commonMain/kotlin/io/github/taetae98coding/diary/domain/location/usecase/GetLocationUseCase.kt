package io.github.taetae98coding.diary.domain.location.usecase

import io.github.taetae98coding.diary.core.entity.location.Location
import io.github.taetae98coding.diary.domain.location.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class GetLocationUseCase internal constructor(
    private val locationRepository: LocationRepository,
) {
    public operator fun invoke(): Flow<Result<Location>> {
        return flow { emitAll(locationRepository.get()) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
