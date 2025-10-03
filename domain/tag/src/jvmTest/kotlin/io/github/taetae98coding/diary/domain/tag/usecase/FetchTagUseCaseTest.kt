package io.github.taetae98coding.diary.domain.tag.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.tag.repository.TagRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class FetchTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val tagRepository = mockk<TagRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = FetchTagUseCase(
            getAccountUseCase = getAccountUseCase,
            tagRepository = tagRepository,
        )

        Given("로그인 사용자로 태그를 가져오는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val tagId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(tagId)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository가 호출된다") {
                    coVerify(exactly = 1) { tagRepository.fetch(account, tagId) }
                }

                clearAllMocks()
            }
        }

        Given("게스트로 태그를 가져오는 경우") {
            val tagId = fixture.giveMeOne<Uuid>()
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(tagId)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository가 호출하지 않는다.") {
                    coVerify(exactly = 0) { tagRepository.fetch(any(), any()) }
                }
            }
        }
    }
}
