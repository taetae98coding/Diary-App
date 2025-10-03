package io.github.taetae98coding.diary.domain.tag.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.domain.tag.repository.TagRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GetTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val tagRepository = mockk<TagRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetTagUseCase(
            tagRepository = tagRepository,
        )

        Given("태그가 존재하는 경우") {
            val tagId = fixture.giveMeOne<Uuid>()
            val tag = fixture.giveMeOne<Tag>()
            every { tagRepository.get(tagId) } returns flowOf(tag)

            When("유즈케이스를 구독하면") {
                val result = useCase(tagId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(tag)
                }
            }
        }

        Given("태그가 존재하지 않는 경우") {
            val tagId = fixture.giveMeOne<Uuid>()
            every { tagRepository.get(tagId) } returns flowOf(null)

            When("유즈케이스를 구독하면") {
                val result = useCase(tagId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(null)
                }
            }
        }
    }
}
