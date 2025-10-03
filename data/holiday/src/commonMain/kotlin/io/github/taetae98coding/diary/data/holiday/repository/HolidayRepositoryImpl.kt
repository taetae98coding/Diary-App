package io.github.taetae98coding.diary.data.holiday.repository

import io.githbu.taetae98coding.diary.core.holiday.service.datasource.HolidayRemoteDataSource
import io.githbu.taetae98coding.diary.core.holiday.service.entity.HolidayRemoteEntity
import io.github.taetae98coding.diary.core.entity.holiday.Holiday
import io.github.taetae98coding.diary.core.holiday.database.datasource.HolidayLocalDataSource
import io.github.taetae98coding.diary.core.holiday.database.entity.HolidayLocalEntity
import io.github.taetae98coding.diary.core.holiday.preferences.datasource.HolidayPreferencesDataSource
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.mapper.toLocal
import io.github.taetae98coding.diary.domain.holiday.repository.HolidayRepository
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.combine
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import io.github.taetae98coding.diary.library.kotlinx.datetime.isOverlaps
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.YearMonth
import kotlinx.datetime.plus
import org.koin.core.annotation.Factory

@Factory
internal class HolidayRepositoryImpl(
    private val holidayPreferencesDataSource: HolidayPreferencesDataSource,
    private val holidayLocalDataSource: HolidayLocalDataSource,
    private val holidayRemoteDataSource: HolidayRemoteDataSource,
) : HolidayRepository {
    override suspend fun fetch(yearMonth: YearMonth) {
        if (!holidayPreferencesDataSource.isDirty(yearMonth).first()) {
            holidayLocalDataSource.submit(yearMonth, holidayRemoteDataSource.get(yearMonth).map(HolidayRemoteEntity::toLocal))
            holidayPreferencesDataSource.setDirty(yearMonth)
        }
    }

    override fun get(yearMonth: YearMonth): Flow<List<Holiday>> {
        return IntRange(-1, 1).map { yearMonth.plus(it, DateTimeUnit.MONTH) }
            .map { holidayLocalDataSource.get(it) }
            .combine { it.toList() }
            .mapLatest { it.flatten() }
            .mapLatest { it.distinct() }
            .mapCollectionLatest(HolidayLocalEntity::toEntity)
            .mapLatest { it.compact() }
            .mapLatest { list -> list.filter { it.dateRange.isOverlaps(yearMonth.days) } }
    }

    private fun List<Holiday>.compact(): List<Holiday> {
        return groupBy { it.name }.values
            .map { list -> list.sortedBy { it.dateRange.start } }
            .map { list ->
                list.fold(emptyList<Holiday>()) { acc, holiday ->
                    if (acc.isEmpty()) {
                        listOf(holiday)
                    } else if (acc.last().dateRange.endInclusive.plus(1, DateTimeUnit.DAY) == holiday.dateRange.start) {
                        acc.dropLast(1) + acc.last().copy(dateRange = acc.last().dateRange.start..holiday.dateRange.endInclusive)
                    } else {
                        acc + holiday
                    }
                }
            }
            .flatten()
    }
}
