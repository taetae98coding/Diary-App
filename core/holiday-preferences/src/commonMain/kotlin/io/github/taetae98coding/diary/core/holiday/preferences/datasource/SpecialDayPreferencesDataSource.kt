package io.github.taetae98coding.diary.core.holiday.preferences.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import io.github.taetae98coding.diary.core.datastore.di.SpecialDayDataStore
import kotlin.time.Clock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.todayIn
import kotlinx.datetime.yearMonth
import org.koin.core.annotation.Single

@Single
public class SpecialDayPreferencesDataSource internal constructor(
    @param:SpecialDayDataStore
    private val dataStore: DataStore<Preferences>,
    private val clock: Clock,
) {
    private val dirtyFlowMap = mutableMapOf<YearMonth, MutableStateFlow<Boolean>>()

    public fun isDirty(yearMonth: YearMonth): Flow<Boolean> {
        return if (isPast(yearMonth)) {
            dataStore.data.mapLatest { it[yearMonthKey(yearMonth)] }
                .mapLatest { it ?: false }
        } else {
            dirtyFlowMap.getOrPut(yearMonth) { MutableStateFlow(false) }
        }
    }

    public suspend fun setDirty(yearMonth: YearMonth) {
        if (isPast(yearMonth)) {
            dataStore.edit { it[yearMonthKey(yearMonth)] = true }
        } else {
            dirtyFlowMap.getOrPut(yearMonth) { MutableStateFlow(false) }
                .emit(true)
        }
    }

    private fun isPast(yearMonth: YearMonth): Boolean {
        val today = clock.todayIn(TimeZone.currentSystemDefault())
        return yearMonth < today.yearMonth
    }

    private fun yearMonthKey(yearMonth: YearMonth): Preferences.Key<Boolean> {
        return booleanPreferencesKey(yearMonth.toString())
    }
}
