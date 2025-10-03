package io.github.taetae98coding.diary.domain.memo.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.memo.exception.MemoTitleBlankException
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess

class CheckMemoDetailUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()
        val useCase = CheckMemoDetailUseCase()

        Given("유효한 메모 상세인 경우") {
            val detail = fixture.giveMeKotlinBuilder<MemoDetail>()
                .set(MemoDetail::title, "제목")
                .sample()

            When("유즈케이스를 실행하면") {
                val result = useCase(detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
            }
        }

        Given("메모 제목이 비어있는 경우") {
            val detail = fixture.giveMeKotlinBuilder<MemoDetail>()
                .set(MemoDetail::title, "")
                .sample()

            When("유즈케이스를 실행하면") {
                val result = useCase(detail)

                Then("실패한다") {
                    result.shouldBeFailure<MemoTitleBlankException>()
                }
            }
        }
    }
}
