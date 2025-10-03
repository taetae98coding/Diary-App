package io.github.taetae98coding.diary.di

import io.githbu.taetae98coding.diary.core.holiday.service.HolidayServiceModule
import io.github.taetae98coding.diary.BuildKonfig
import io.github.taetae98coding.diary.core.coroutines.CoroutinesModule
import io.github.taetae98coding.diary.core.database.DatabaseModule
import io.github.taetae98coding.diary.core.datastore.DataStoreModule
import io.github.taetae98coding.diary.core.holiday.database.HolidayDatabaseModule
import io.github.taetae98coding.diary.core.holiday.preferences.HolidayPreferencesModule
import io.github.taetae98coding.diary.core.ip.service.IpServiceModule
import io.github.taetae98coding.diary.core.ktor.client.KtorClientModule
import io.github.taetae98coding.diary.core.ktor.client.di.DiaryApiUrl
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayApiKey
import io.github.taetae98coding.diary.core.ktor.client.di.HolidayApiUrl
import io.github.taetae98coding.diary.core.ktor.client.di.WeatherApiUrl
import io.github.taetae98coding.diary.core.ktor.client.di.WeatherAppId
import io.github.taetae98coding.diary.core.preferences.PreferencesModule
import io.github.taetae98coding.diary.core.service.ServiceModule
import io.github.taetae98coding.diary.core.weather.service.WeatherServiceModule
import io.github.taetae98coding.diary.core.work.WorkModule
import io.github.taetae98coding.diary.data.account.AccountDataModule
import io.github.taetae98coding.diary.data.buddy.BuddyDataModule
import io.github.taetae98coding.diary.data.buddy.group.BuddyGroupDataModule
import io.github.taetae98coding.diary.data.calendar.CalendarDataModule
import io.github.taetae98coding.diary.data.credentials.CredentialsDataModule
import io.github.taetae98coding.diary.data.holiday.HolidayDataModule
import io.github.taetae98coding.diary.data.location.LocationDataModule
import io.github.taetae98coding.diary.data.memo.MemoDataModule
import io.github.taetae98coding.diary.data.push.PushDataModule
import io.github.taetae98coding.diary.data.sync.SyncDataModule
import io.github.taetae98coding.diary.data.tag.TagDataModule
import io.github.taetae98coding.diary.data.weather.WeatherDataModule
import io.github.taetae98coding.diary.domain.account.AccountDomainModule
import io.github.taetae98coding.diary.domain.buddy.BuddyDomainModule
import io.github.taetae98coding.diary.domain.buddy.group.BuddyGroupDomainModule
import io.github.taetae98coding.diary.domain.calendar.CalendarDomainModule
import io.github.taetae98coding.diary.domain.credentials.CredentialsDomainModule
import io.github.taetae98coding.diary.domain.holiday.HolidayDomainModule
import io.github.taetae98coding.diary.domain.location.LocationDomainModule
import io.github.taetae98coding.diary.domain.memo.MemoDomainModule
import io.github.taetae98coding.diary.domain.push.PushDomainModule
import io.github.taetae98coding.diary.domain.sync.SyncDomainModule
import io.github.taetae98coding.diary.domain.tag.TagDomainModule
import io.github.taetae98coding.diary.domain.weather.WeatherDomainModule
import io.github.taetae98coding.diary.feature.buddy.group.BuddyGroupFeatureModule
import io.github.taetae98coding.diary.feature.calendar.CalendarFeatureModule
import io.github.taetae98coding.diary.feature.login.LoginFeatureModule
import io.github.taetae98coding.diary.feature.memo.MemoFeatureModule
import io.github.taetae98coding.diary.feature.more.MoreFeatureModule
import io.github.taetae98coding.diary.feature.tag.TagFeatureModule
import io.github.taetae98coding.diary.presenter.calendar.CalendarPresenterModule
import kotlin.time.Clock
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(
    includes = [
        CoroutinesModule::class,
        DatabaseModule::class,
        DataStoreModule::class,
        HolidayDatabaseModule::class,
        HolidayPreferencesModule::class,
        HolidayServiceModule::class,
        IpServiceModule::class,
        KtorClientModule::class,
        PreferencesModule::class,
        ServiceModule::class,
        WeatherServiceModule::class,
        WorkModule::class,

        AccountDataModule::class,
        BuddyDataModule::class,
        BuddyGroupDataModule::class,
        CalendarDataModule::class,
        CredentialsDataModule::class,
        HolidayDataModule::class,
        LocationDataModule::class,
        MemoDataModule::class,
        PushDataModule::class,
        SyncDataModule::class,
        TagDataModule::class,
        WeatherDataModule::class,

        AccountDomainModule::class,
        BuddyDomainModule::class,
        BuddyGroupDomainModule::class,
        CalendarDomainModule::class,
        CredentialsDomainModule::class,
        HolidayDomainModule::class,
        LocationDomainModule::class,
        MemoDomainModule::class,
        PushDomainModule::class,
        SyncDomainModule::class,
        TagDomainModule::class,
        WeatherDomainModule::class,

        BuddyGroupFeatureModule::class,
        CalendarFeatureModule::class,
        LoginFeatureModule::class,
        MemoFeatureModule::class,
        MoreFeatureModule::class,
        TagFeatureModule::class,

        CalendarPresenterModule::class,
    ],
)
@ComponentScan("io.github.taetae98coding.diary")
internal class AppModule {
    @Single
    @DiaryApiUrl
    fun providesDiaryApiUrl(): String {
        return BuildKonfig.DIARY_API_URL
    }

    @Single
    @WeatherApiUrl
    fun providesWeatherApiUrl(): String {
        return BuildKonfig.WEATHER_API_URL
    }

    @Single
    @WeatherAppId
    fun providesWeatherAppId(): String {
        return BuildKonfig.WEATHER_API_APP_ID
    }

    @Single
    @HolidayApiUrl
    fun providesHolidayApiUrl(): String {
        return BuildKonfig.HOLIDAY_API_URL
    }

    @Single
    @HolidayApiKey
    fun providesHolidayApiKey(): String {
        return BuildKonfig.HOLIDAY_API_KEY
    }

    @Single
    fun providesClock(): Clock {
        return Clock.System
    }
}
