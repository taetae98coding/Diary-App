package io.github.taetae98coding.diary.domain.holiday.repository

import io.github.taetae98coding.diary.core.entity.holiday.SpecialDay
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.YearMonth

public interface SpecialDayRepository {
    public suspend fun fetch(yearMonth: YearMonth)
    public fun get(yearMonth: YearMonth): Flow<List<SpecialDay>>
}
