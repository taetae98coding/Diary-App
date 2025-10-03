package io.github.taetae98coding.diary.domain.holiday.usecase

import io.github.taetae98coding.diary.core.entity.holiday.SpecialDay
import io.github.taetae98coding.diary.domain.holiday.repository.SpecialDayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.YearMonth
import org.koin.core.annotation.Factory

@Factory
public class GetSpecialDayUseCase internal constructor(
    private val specialDayRepository: SpecialDayRepository,
) {
    public operator fun invoke(yearMonth: YearMonth): Flow<Result<List<SpecialDay>>> {
        return flow { emitAll(specialDayRepository.get(yearMonth)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
