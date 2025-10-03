package io.github.taetae98coding.diary.domain.tag.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.tag.repository.SelectMemoTagRepository
import io.github.taetae98coding.diary.library.paging.common.notLoading
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PageSelectMemoTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val repository = mockk<SelectMemoTagRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageSelectMemoTagUseCase(
            getAccountUseCase = getAccountUseCase,
            selectMemoTagRepository = repository,
        )

        Given("사용자의 메모 태그 선택 페이징을 조회하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val memoId = fixture.giveMeOne<Uuid>()
            val paging = PagingData.notLoading<SelectMemoTag>()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { repository.page(account, memoId) } returns flowOf(paging)

            When("유즈케이스를 구독하면") {
                val result = useCase(memoId).first()

                Then("성공한다") {
                    result.shouldBeSuccess(paging)
                }

                Then("레포지토리 1회 호출한다.") {
                    verify(exactly = 1) { repository.page(account, memoId) }
                }
            }
        }
    }
}
