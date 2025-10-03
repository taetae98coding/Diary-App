package io.github.taetae98coding.diary.domain.holiday.repository

import io.github.taetae98coding.diary.core.entity.holiday.Holiday
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.YearMonth

public interface HolidayRepository {
    public suspend fun fetch(yearMonth: YearMonth)
    public fun get(yearMonth: YearMonth): Flow<List<Holiday>>
}
