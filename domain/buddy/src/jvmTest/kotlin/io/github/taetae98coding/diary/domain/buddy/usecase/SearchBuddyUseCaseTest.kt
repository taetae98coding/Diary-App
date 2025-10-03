package io.github.taetae98coding.diary.domain.buddy.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMe
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.buddy.Buddy
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.repository.BuddyRepository
import io.github.taetae98coding.diary.library.paging.common.notLoading
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class SearchBuddyUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyRepository = mockk<BuddyRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = SearchBuddyUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyRepository = buddyRepository,
        )

        Given("검색어가 빈 문자열인 경우") {
            val query = ""

            When("유즈케이스를 실행하면") {
                val result = useCase(query).first()

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Search 호출하지 않는다") {
                    verify(exactly = 0) { buddyRepository.search(any(), any()) }
                }
            }
        }

        Given("검색어가 존재하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val query = fixture.giveMeOne<String>()
            val paging = PagingData.notLoading<Buddy>(fixture.giveMe(5))

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyRepository.search(account, query) } returns flowOf(paging)

            When("유즈케이스를 실행하면") {
                val result = useCase(query).first()

                Then("Repository 결과를 그대로 방출한다") {
                    result.shouldBeSuccess(paging)
                }
                Then("Search 호출한다.") {
                    verify(exactly = 1) { buddyRepository.search(account, query) }
                }
            }
        }
    }
}
