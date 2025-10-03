package io.github.taetae98coding.diary.domain.tag.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.tag.exception.TagTitleBlankException
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess

class CheckTagDetailUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val useCase = CheckTagDetailUseCase()

        Given("유효한 태그 상세인 경우") {
            val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                .set(TagDetail::title, "title")
                .sample()

            When("유즈케이스를 실행하면") {
                val result = useCase(detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
            }
        }

        Given("태그 제목이 비어있는 경우") {
            val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                .set(TagDetail::title, "")
                .sample()

            When("유즈케이스를 실행하면") {
                val result = useCase(detail)

                Then("실패한다") {
                    result.shouldBeFailure<TagTitleBlankException>()
                }
            }
        }
    }
}
