package io.github.taetae98coding.diary.data.holiday.repository

import io.githbu.taetae98coding.diary.core.holiday.service.datasource.SpecialDayRemoteDataSource
import io.github.taetae98coding.diary.core.entity.holiday.SpecialDay
import io.github.taetae98coding.diary.core.holiday.database.datasource.SpecialDayLocalDataSource
import io.github.taetae98coding.diary.core.holiday.database.entity.SpecialDayLocalEntity
import io.github.taetae98coding.diary.core.holiday.preferences.datasource.SpecialDayPreferencesDataSource
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.domain.holiday.repository.SpecialDayRepository
import io.github.taetae98coding.diary.library.kotlinx.coroutines.core.flow.mapCollectionLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.YearMonth
import org.koin.core.annotation.Factory

@Factory
internal class SpecialDayRepositoryImpl(
    private val specialDayPreferencesDataSource: SpecialDayPreferencesDataSource,
    private val specialDayLocalDataSource: SpecialDayLocalDataSource,
    private val specialDayRemoteDataSource: SpecialDayRemoteDataSource,
) : SpecialDayRepository {
    override suspend fun fetch(yearMonth: YearMonth) {
        // TODO 공휴일 API 복구 시 적용
//        if (!specialDayPreferencesDataSource.isDirty(yearMonth).first()) {
//            specialDayLocalDataSource.submit(yearMonth, specialDayRemoteDataSource.get(yearMonth).map(HolidayRemoteEntity::toLocalSpecialDayEntity))
//            specialDayPreferencesDataSource.setDirty(yearMonth)
//        }
    }

    override fun get(yearMonth: YearMonth): Flow<List<SpecialDay>> {
        return specialDayLocalDataSource.get(yearMonth).mapCollectionLatest(SpecialDayLocalEntity::toEntity)
    }
}
