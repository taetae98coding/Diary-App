package io.github.taetae98coding.diary.domain.memo.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMe
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.MemoSearchRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class SearchMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val memoSearchRepository = mockk<MemoSearchRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = SearchMemoUseCase(
            getAccountUseCase = getAccountUseCase,
            memoSearchRepository = memoSearchRepository,
        )

        Given("사용자의 메모를 키워드로 페이징 검색하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val keyword = "테스트"
            val paging = PagingData.from(fixture.giveMe<Memo>(3))
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { memoSearchRepository.search(account, keyword) } returns flowOf(paging)

            When("유즈케이스를 구독하면") {
                val result = useCase(keyword).first()

                Then("성공한다") { result.shouldBeSuccess(paging) }
                Then("Repository 호출한다") {
                    verify(exactly = 1) { memoSearchRepository.search(account, keyword) }
                }
            }
        }
    }
}



