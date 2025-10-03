package io.github.taetae98coding.diary.domain.tag.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.tag.repository.TagMemoRepository
import io.github.taetae98coding.diary.library.paging.common.notLoading
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PageTagMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val tagMemoRepository = mockk<TagMemoRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageTagMemoUseCase(
            tagMemoRepository = tagMemoRepository,
        )

        Given("태그별 메모 페이징을 조회하는 경우") {
            val tagId = fixture.giveMeOne<Uuid>()
            val paging = PagingData.notLoading<Memo>()
            every { tagMemoRepository.page(tagId) } returns flowOf(paging)

            When("유즈케이스를 구독하면") {
                val result = useCase(tagId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(paging)
                }

                Then("레포지토리 호출한다") {
                    verify(exactly = 1) { tagMemoRepository.page(tagId) }
                }
            }
        }
    }
}
