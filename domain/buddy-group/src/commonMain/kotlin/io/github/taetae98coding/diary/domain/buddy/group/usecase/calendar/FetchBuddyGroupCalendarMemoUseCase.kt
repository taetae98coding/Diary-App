package io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupCalendarMemoRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.datetime.LocalDateRange
import org.koin.core.annotation.Factory

@Factory
public class FetchBuddyGroupCalendarMemoUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupCalendarMemoRepository: BuddyGroupCalendarMemoRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, dateRange: LocalDateRange): Result<Unit> {
        return runCatching {
            when (val account = getAccountUseCase().mapLatest { it.getOrThrow() }.first()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> buddyGroupCalendarMemoRepository.fetch(account, buddyGroupId, dateRange)
            }
        }
    }
}
