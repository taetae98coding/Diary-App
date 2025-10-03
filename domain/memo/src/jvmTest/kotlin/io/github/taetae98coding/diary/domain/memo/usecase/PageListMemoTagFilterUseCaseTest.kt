package io.github.taetae98coding.diary.domain.memo.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.MemoTagFilter
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.memo.repository.ListMemoTagFilterTagRepository
import io.github.taetae98coding.diary.library.paging.common.notLoading
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PageListMemoTagFilterUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder().plugin(KotlinPlugin()).build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val repository = mockk<ListMemoTagFilterTagRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageListMemoTagFilterUseCase(
            getAccountUseCase = getAccountUseCase,
            listMemoTagFilterTagRepository = repository,
        )

        Given("사용자의 메모 태그 필터 페이징을 조회하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val paging = PagingData.notLoading<MemoTagFilter>()
            every { getAccountUseCase() } returns flow { emit(Result.success(account)) }
            every { repository.page(account) } returns flow { emit(paging) }

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("성공한다") { result.shouldBeSuccess(paging) }
            }
        }
    }
}
