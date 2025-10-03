package io.github.taetae98coding.diary.data.credentials.repository

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.mapper.toPreferences
import io.github.taetae98coding.diary.core.preferences.datasource.SessionPreferencesDataSource
import io.github.taetae98coding.diary.core.service.datasource.SessionRemoteDataSource
import io.github.taetae98coding.diary.core.service.entity.DiaryRemoteEntity
import io.github.taetae98coding.diary.core.service.entity.account.SessionRemoteEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class CredentialsRepositoryImplTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val sessionPreferencesDataSource = mockk<SessionPreferencesDataSource>(relaxed = true, relaxUnitFun = true)
        val sessionRemoteDataSource = mockk<SessionRemoteDataSource>(relaxed = true, relaxUnitFun = true)
        val repository = CredentialsRepositoryImpl(
            sessionPreferencesDataSource = sessionPreferencesDataSource,
            sessionRemoteDataSource = sessionRemoteDataSource,
        )

        Given("idToken이 주어진 경우") {
            val idToken = fixture.giveMeOne<String>()
            val sessionRemoteEntity = fixture.giveMeOne<SessionRemoteEntity>()
            val response = fixture.giveMeKotlinBuilder<DiaryRemoteEntity<SessionRemoteEntity>>()
                .set(DiaryRemoteEntity<SessionRemoteEntity>::code, DiaryRemoteEntity.SUCCESS)
                .set(DiaryRemoteEntity<SessionRemoteEntity>::body, sessionRemoteEntity)
                .sample()

            coEvery { sessionRemoteDataSource.getGoogleSession(idToken) } returns response

            When("updateGoogleSession을 호출하면") {
                repository.updateGoogleSession(idToken)

                Then("sessionRemoteDataSource.getGoogleSession을 호출한다") {
                    coVerify(exactly = 1) { sessionRemoteDataSource.getGoogleSession(idToken) }
                }
                Then("sessionPreferencesDataSource.update를 호출한다") {
                    coVerify(exactly = 1) { sessionPreferencesDataSource.update(sessionRemoteEntity.toPreferences()) }
                }
            }
        }
    }
}
