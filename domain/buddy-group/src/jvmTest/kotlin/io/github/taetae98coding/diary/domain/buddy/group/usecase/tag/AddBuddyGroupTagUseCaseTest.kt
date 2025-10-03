package io.github.taetae98coding.diary.domain.buddy.group.usecase.tag

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupTagRepository
import io.github.taetae98coding.diary.domain.tag.exception.TagTitleBlankException
import io.github.taetae98coding.diary.domain.tag.usecase.CheckTagDetailUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class AddBuddyGroupTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val checkTagDetailUseCase = mockk<CheckTagDetailUseCase>(relaxed = true, relaxUnitFun = true)
        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupTagRepository = mockk<BuddyGroupTagRepository>(relaxed = true, relaxUnitFun = true)

        val useCase = AddBuddyGroupTagUseCase(
            checkTagDetailUseCase = checkTagDetailUseCase,
            getAccountUseCase = getAccountUseCase,
            buddyGroupTagRepository = buddyGroupTagRepository,
        )

        Given("유효한 태그 상세로 추가하는 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                .set(TagDetail::title, "제목")
                .sample()
            val account = fixture.giveMeOne<Account.User>()

            coEvery { checkTagDetailUseCase(detail) } returns Result.success(Unit)
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupTagRepository.add(
                            account = account,
                            buddyGroupId = buddyGroupId,
                            detail = detail,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("태그 제목이 비어있는 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                .set(TagDetail::title, "")
                .sample()

            coEvery { checkTagDetailUseCase(detail) } returns Result.failure(TagTitleBlankException())

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, detail)

                Then("실패한다") {
                    result.shouldBeFailure<TagTitleBlankException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupTagRepository.add(any(), any(), any())
                    }
                }
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<TagDetail>()
                .set(TagDetail::title, "제목")
                .sample()

            coEvery { checkTagDetailUseCase(detail) } returns Result.success(Unit)
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, detail)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupTagRepository.add(any(), any(), any())
                    }
                }
            }
        }
    }
}
