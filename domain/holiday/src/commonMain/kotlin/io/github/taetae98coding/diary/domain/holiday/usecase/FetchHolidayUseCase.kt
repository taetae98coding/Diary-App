package io.github.taetae98coding.diary.domain.holiday.usecase

import io.github.taetae98coding.diary.domain.holiday.repository.HolidayRepository
import kotlinx.datetime.YearMonth
import org.koin.core.annotation.Factory

@Factory
public class FetchHolidayUseCase internal constructor(
    private val holidayRepository: HolidayRepository,
) {
    public suspend operator fun invoke(yearMonth: YearMonth): Result<Unit> {
        return runCatching { holidayRepository.fetch(yearMonth) }
    }
}
