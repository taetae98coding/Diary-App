package io.github.taetae98coding.diary.domain.holiday.usecase

import io.github.taetae98coding.diary.domain.holiday.repository.SpecialDayRepository
import kotlinx.datetime.YearMonth
import org.koin.core.annotation.Factory

@Factory
public class FetchSpecialDayUseCase internal constructor(
    private val specialDayRepository: SpecialDayRepository,
) {
    public suspend operator fun invoke(yearMonth: YearMonth): Result<Unit> {
        return runCatching { specialDayRepository.fetch(yearMonth) }
    }
}
