package io.github.taetae98coding.diary.data.account.repository

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.mapper.toEntity
import io.github.taetae98coding.diary.core.preferences.datasource.SessionPreferencesDataSource
import io.github.taetae98coding.diary.core.preferences.entity.SessionPreferencesEntity
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class SessionRepositoryImplTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val sessionPreferencesDataSource = mockk<SessionPreferencesDataSource>(relaxed = true, relaxUnitFun = true)
        val repository = SessionRepositoryImpl(
            sessionPreferencesDataSource = sessionPreferencesDataSource,
        )

        Given("세션이 null인 경우") {
            every { sessionPreferencesDataSource.get() } returns flowOf(null)

            When("get을 호출하면") {
                val result = repository.get().first()

                Then("null을 반환한다") {
                    result shouldBe null
                }
            }
        }

        Given("세션이 존재하는 경우") {
            val sessionPreferencesEntity = fixture.giveMeOne<SessionPreferencesEntity>()

            every { sessionPreferencesDataSource.get() } returns flowOf(sessionPreferencesEntity)

            When("get을 호출하면") {
                val result = repository.get().first()

                Then("Session으로 변환하여 반환한다") {
                    result shouldBe sessionPreferencesEntity.toEntity()
                }
            }
        }
    }
}
