package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.memo.repository.MemoRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val memoRepository = mockk<MemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetMemoUseCase(
            memoRepository = memoRepository,
        )

        Given("메모가 존재하는 경우") {
            val memoId = fixture.giveMeOne<Uuid>()
            val memo = fixture.giveMeOne<Memo>()
            every { memoRepository.get(memoId) } returns flowOf(memo)

            When("유즈케이스를 구독하면") {
                val result = useCase(memoId).first()

                Then("메모를 방출한다") {
                    result.shouldBeSuccess(memo)
                }
            }
        }

        Given("메모가 존재하지 않는 경우") {
            val memoId = fixture.giveMeOne<Uuid>()
            every { memoRepository.get(memoId) } returns flowOf(null)

            When("유즈케이스를 구독하면") {
                val result = useCase(memoId).first()

                Then("null 을 방출한다") {
                    result.shouldBeSuccess(null)
                }
            }
        }
    }
}
