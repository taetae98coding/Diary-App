package io.github.taetae98coding.diary.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.taetae98coding.diary.core.datastore.di.HolidayDataStore
import io.github.taetae98coding.diary.core.datastore.di.SessionDataStore
import io.github.taetae98coding.diary.core.datastore.di.SpecialDayDataStore
import io.github.taetae98coding.diary.library.koin.datastore.deviceProtectedDataStore
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Module
@ComponentScan
public class DataStoreModule : KoinComponent {
    @Single
    @SessionDataStore
    internal fun providesSessionDataStore(): DataStore<Preferences> {
        return deviceProtectedDataStore("session.preferences_pb")
    }

    @Single
    @HolidayDataStore
    internal fun providesHolidayDataStore(): DataStore<Preferences> {
        return deviceProtectedDataStore("holiday.preferences_pb")
    }

    @Single
    @SpecialDayDataStore
    internal fun providesSpecialDataStore(): DataStore<Preferences> {
        return deviceProtectedDataStore("specialDay.preferences_pb")
    }
}
