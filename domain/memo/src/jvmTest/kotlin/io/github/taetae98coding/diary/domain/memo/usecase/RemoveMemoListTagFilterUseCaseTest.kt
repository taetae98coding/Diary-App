package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.domain.memo.repository.MemoListTagFilterRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.uuid.Uuid

class RemoveMemoListTagFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val repository = mockk<MemoListTagFilterRepository>(relaxed = true, relaxUnitFun = true)
        val removeUseCase = RemoveMemoListTagFilterUseCase(repository)

        Given("태그 필터를 제거하는 경우") {
            val tagId = fixture.giveMeOne<Uuid>()

            When("유즈케이스를 실행하면") {
                val result = removeUseCase(tagId)

                Then("성공한다") { result.shouldBeSuccess() }
                Then("Repository 호출한다") { coVerify(exactly = 1) { repository.upsert(tagId, false) } }
            }
        }
    }
}
