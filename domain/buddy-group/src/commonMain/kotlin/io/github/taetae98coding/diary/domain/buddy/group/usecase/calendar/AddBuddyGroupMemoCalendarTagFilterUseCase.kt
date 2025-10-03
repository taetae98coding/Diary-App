package io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar

import io.github.taetae98coding.diary.core.entity.account.Account
import io.github.taetae98coding.diary.domain.account.exception.NotLoginException
import io.github.taetae98coding.diary.domain.account.usecase.GetAccountUseCase
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarTagFilterRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
public class AddBuddyGroupMemoCalendarTagFilterUseCase internal constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val buddyGroupMemoCalendarTagFilterRepository: BuddyGroupMemoCalendarTagFilterRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, tagId: Uuid): Result<Unit> {
        return runCatching {
            when (getAccountUseCase().first().getOrThrow()) {
                is Account.Guest -> throw NotLoginException()
                is Account.User -> buddyGroupMemoCalendarTagFilterRepository.upsert(buddyGroupId, tagId, true)
            }
        }
    }
}
