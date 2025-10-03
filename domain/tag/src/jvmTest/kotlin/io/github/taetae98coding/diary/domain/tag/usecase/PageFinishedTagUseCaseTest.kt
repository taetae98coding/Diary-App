package io.github.taetae98coding.diary.domain.tag.usecase

import androidx.paging.PagingData
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.tag.repository.FinishedTagRepository
import io.github.taetae98coding.diary.library.paging.common.notLoading
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PageFinishedTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val finishedTagRepository = mockk<FinishedTagRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = PageFinishedTagUseCase(
            getAccountUseCase = getAccountUseCase,
            finishedTagRepository = finishedTagRepository,
        )

        Given("사용자의 완료된 태그를 페이징 조회하는 경우") {
            val account = fixture.giveMeOne<Account>()
            val paging = PagingData.notLoading<Tag>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { finishedTagRepository.page(account) } returns flowOf(paging)

            When("유즈케이스를 구독하면") {
                val result = useCase().first()

                Then("성공한다") { result.shouldBeSuccess(paging) }
                Then("Repository 호출한다") {
                    verify(exactly = 1) { finishedTagRepository.page(account) }
                }
            }
        }
    }
}
