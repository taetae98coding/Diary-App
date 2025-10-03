package io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar

import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarTagFilterRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class RemoveBuddyGroupMemoCalendarTagFilterUseCase internal constructor(
    private val buddyGroupMemoCalendarTagFilterRepository: BuddyGroupMemoCalendarTagFilterRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, tagId: Uuid): Result<Unit> {
        return runCatching { buddyGroupMemoCalendarTagFilterRepository.upsert(buddyGroupId, tagId, false) }
    }
}
