package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMe
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import io.github.taetae98coding.diary.domain.memo.exception.MemoTitleBlankException
import io.github.taetae98coding.diary.domain.memo.usecase.CheckMemoDetailUseCase
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

class AddBuddyGroupMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val checkMemoDetailUseCase = mockk<CheckMemoDetailUseCase>(relaxed = true, relaxUnitFun = true)
        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupMemoRepository = mockk<BuddyGroupMemoRepository>(relaxed = true, relaxUnitFun = true)

        val useCase = AddBuddyGroupMemoUseCase(
            checkMemoDetailUseCase = checkMemoDetailUseCase,
            getAccountUseCase = getAccountUseCase,
            buddyGroupMemoRepository = buddyGroupMemoRepository,
        )

        Given("유효한 메모 상세로 추가하는 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<MemoDetail>()
                .set(MemoDetail::title, "제목")
                .sample()
            val primaryTag = fixture.giveMeOne<Uuid>()
            val memoTagIds = fixture.giveMe<Uuid>(3).toSet()
            val account = fixture.giveMeOne<Account.User>()

            coEvery { checkMemoDetailUseCase(detail) } returns Result.success(Unit)
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, detail, primaryTag, memoTagIds)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupMemoRepository.add(
                            account = account,
                            buddyGroupId = buddyGroupId,
                            detail = detail,
                            primaryTag = primaryTag,
                            memoTagIds = memoTagIds,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("메모 제목이 비어있는 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<MemoDetail>()
                .set(MemoDetail::title, "")
                .sample()
            val primaryTag = fixture.giveMeOne<Uuid>()
            val memoTagIds = emptySet<Uuid>()

            coEvery { checkMemoDetailUseCase(detail) } returns Result.failure(MemoTitleBlankException())

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, detail, primaryTag, memoTagIds)

                Then("실패한다") {
                    result.shouldBeFailure<MemoTitleBlankException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupMemoRepository.add(any(), any(), any(), any(), any())
                    }
                }
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<MemoDetail>()
                .set(MemoDetail::title, "제목")
                .sample()
            val primaryTag = fixture.giveMeOne<Uuid>()
            val memoTagIds = emptySet<Uuid>()

            coEvery { checkMemoDetailUseCase(detail) } returns Result.success(Unit)
            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, detail, primaryTag, memoTagIds)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupMemoRepository.add(any(), any(), any(), any(), any())
                    }
                }
            }
        }
    }
}
