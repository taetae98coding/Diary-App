package io.github.taetae98coding.diary.domain.calendar.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.domain.calendar.repository.MemoCalendarTagFilterRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.uuid.Uuid

class AddMemoCalendarTagFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()
        val memoCalendarTagFilterRepository = mockk<MemoCalendarTagFilterRepository>(relaxed = true, relaxUnitFun = true)
        val addUseCase = AddMemoCalendarTagFilterUseCase(
            memoCalendarTagFilterRepository = memoCalendarTagFilterRepository,
        )

        Given("태그 필터를 추가하는 경우") {
            val tagId = fixture.giveMeOne<Uuid>()

            When("UseCase를 실행하면") {
                val result = addUseCase(tagId)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository 호출한다") {
                    coVerify(exactly = 1) { memoCalendarTagFilterRepository.upsert(tagId, true) }
                }
            }
        }
    }
}
