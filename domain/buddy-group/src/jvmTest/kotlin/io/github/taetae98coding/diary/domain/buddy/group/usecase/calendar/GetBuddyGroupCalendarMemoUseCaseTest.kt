package io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMe
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.core.entity.memo.CalendarMemo
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateRange

class GetBuddyGroupCalendarMemoUseCaseTest : BehaviorSpec() {
    init {
        val fixture = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .build()

        val getAccountUseCase = mockk<GetAccountUseCase>(relaxed = true, relaxUnitFun = true)
        val buddyGroupMemoCalendarRepository = mockk<BuddyGroupMemoCalendarRepository>(relaxed = true, relaxUnitFun = true)
        val useCase = GetBuddyGroupCalendarMemoUseCase(
            getAccountUseCase = getAccountUseCase,
            buddyGroupMemoCalendarRepository = buddyGroupMemoCalendarRepository,
        )

        Given("로그인된 계정으로 그룹의 기간 메모를 조회하는 경우") {
            val account = fixture.giveMeOne<Account.User>()
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val dateRange = LocalDateRange(
                start = fixture.giveMeOne<kotlinx.datetime.LocalDate>(),
                endInclusive = fixture.giveMeOne<kotlinx.datetime.LocalDate>(),
            )
            val memo = fixture.giveMe<CalendarMemo>(10)

            every { getAccountUseCase() } returns flowOf(Result.success(account))
            every { buddyGroupMemoCalendarRepository.get(buddyGroupId, dateRange) } returns flowOf(memo)

            When("유즈케이스를 구독하면") {
                val result = useCase(
                    buddyGroupId = buddyGroupId,
                    dateRange = dateRange,
                ).first()

                Then("Repository 결과를 그대로 방출한다") {
                    result.shouldBeSuccess(memo)
                }
                Then("Repository 호출한다") {
                    verify(exactly = 1) {
                        buddyGroupMemoCalendarRepository.get(buddyGroupId, dateRange)
                    }
                }

                clearAllMocks()
            }
        }

        Given("로그인하지 않은 경우") {
            val buddyGroupId = fixture.giveMeOne<Uuid>()
            val dateRange = LocalDateRange(
                start = fixture.giveMeOne<kotlinx.datetime.LocalDate>(),
                endInclusive = fixture.giveMeOne<kotlinx.datetime.LocalDate>(),
            )

            every { getAccountUseCase() } returns flowOf(Result.success(Account.Guest))

            When("유즈케이스를 구독하면") {
                val result = useCase(
                    buddyGroupId = buddyGroupId,
                    dateRange = dateRange,
                ).first()

                Then("실패한다") {
                    result.shouldBeFailure<NotLoginException>()
                }
                Then("Repository 호출하지 않는다") {
                    verify(exactly = 0) {
                        buddyGroupMemoCalendarRepository.get(any(), any())
                    }
                }
            }
        }
    }
}
