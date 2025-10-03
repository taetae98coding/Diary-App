package io.github.taetae98coding.diary.domain.tag.usecase

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.sync.usecase.RequestDiaryBackupUseCase
import io.github.taetae98coding.diary.domain.tag.repository.AccountTagRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.coroutines.flow.flowOf

class AddTagUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()
        val epochMilliseconds = 123L
        val clock = mockk<Clock>(relaxed = true, relaxUnitFun = true) {
            every { now() } returns Instant.fromEpochMilliseconds(epochMilliseconds)
        }
        val checkTagDetailUseCase = mockk<CheckTagDetailUseCase>(relaxed = true, relaxUnitFun = true)
        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val requestDiaryBackupUseCase = mockk<RequestDiaryBackupUseCase>(relaxed = true, relaxUnitFun = true)
        val accountTagRepository = mockk<AccountTagRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = AddTagUseCase(
            clock = clock,
            checkTagDetailUseCase = checkTagDetailUseCase,
            getAccountUseCase = getAccountUseCase,
            requestDiaryBackupUseCase = requestDiaryBackupUseCase,
            accountTagRepository = accountTagRepository,
        )

        Given("유효한 태그 상세로 추가하는 경우") {
            val detail = fixture.giveMeOne<TagDetail>()
            val account = fixture.giveMeOne<Account>()

            coEvery { checkTagDetailUseCase(detail) } returns Result.success(Unit)
            every { getAccountUseCase() } returns flowOf(Result.success(account))

            When("유즈케이스를 실행하면") {
                val result = useCase(detail)

                Then("성공한다") {
                    result.shouldBeSuccess()
                }
                Then("Repository 호출한다") {
                    coVerify(exactly = 1) {
                        accountTagRepository.add(
                            account = account,
                            tag = withArg {
                                it.detail shouldBe detail
                                it.isFinished.shouldBeFalse()
                                it.isDeleted.shouldBeFalse()
                                it.updatedAt shouldBe epochMilliseconds
                                it.createdAt shouldBe epochMilliseconds
                            },
                        )
                    }
                }

                clearAllMocks()
            }
        }

        Given("태그 상세가 유효하지 않은 경우") {
            val exception = Exception()
            val detail = fixture.giveMeOne<TagDetail>()
            coEvery { checkTagDetailUseCase(detail) } returns Result.failure(exception)

            When("유즈케이스를 실행하면") {
                val result = useCase(detail)

                Then("실패한다") {
                    result.shouldBeFailure(exception)
                }
                Then("Repository 호출하지 않는다") {
                    coVerify(exactly = 0) {
                        accountTagRepository.add(any(), any())
                    }
                }
            }
        }
    }
}
