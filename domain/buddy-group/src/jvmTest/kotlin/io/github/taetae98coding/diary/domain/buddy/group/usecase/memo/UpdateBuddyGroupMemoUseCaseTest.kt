package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeKotlinBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.Memo
import io.github.taetae98coding.diary.core.entity.memo.MemoDetail
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoRepository
import io.github.taetae98coding.diary.domain.memo.usecase.GetMemoUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.flowOf

class UpdateBuddyGroupMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val getMemoUseCase = mockk<GetMemoUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupMemoRepository = mockk<BuddyGroupMemoRepository>(relaxed = true, relaxUnitFun = true)

        val useCase = UpdateBuddyGroupMemoUseCase(
            getAccountUseCase = getAccountUseCase,
            getMemoUseCase = getMemoUseCase,
            buddyGroupMemoRepository = buddyGroupMemoRepository,
        )

        Given("로그인된 계정으로 메모를 수정하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val memoId = fixture.giveMeOne<Uuid>()
            val memo = fixture.giveMeOne<Memo>()
            val detail = fixture.giveMeKotlinBuilder<MemoDetail>()
                .set(MemoDetail::title, "제목")
                .sample()

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { getMemoUseCase(memoId) } returns flowOf(Result.success(memo))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, memoId, detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository를 호출한다") {
                    coVerify(exactly = 1) {
                        buddyGroupMemoRepository.update(
                            account = account,
                            buddyGroupId = buddyGroupId,
                            memoId = memoId,
                            detail = detail,
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val memoId = fixture.giveMeOne<Uuid>()
            val detail = fixture.giveMeKotlinBuilder<MemoDetail>()
                .set(MemoDetail::title, "제목")
                .sample()

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 실행하면") {
                val result = useCase(buddyGroupId, memoId, detail)

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository를 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        buddyGroupMemoRepository.update(any(), any(), any(), any())
                    }
                }
            }
        }
    }
}
