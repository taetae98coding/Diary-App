package io.github.taetae98coding.diary.domain.holiday.usecase

import io.github.taetae98coding.diary.core.entity.holiday.Holiday
import io.github.taetae98coding.diary.domain.holiday.repository.HolidayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.YearMonth
import org.koin.core.annotation.Factory

@Factory
public class GetHolidayUseCase internal constructor(
    private val holidayRepository: HolidayRepository,
) {
    public operator fun invoke(yearMonth: YearMonth): Flow<Result<List<Holiday>>> {
        return flow { emitAll(holidayRepository.get(yearMonth)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
