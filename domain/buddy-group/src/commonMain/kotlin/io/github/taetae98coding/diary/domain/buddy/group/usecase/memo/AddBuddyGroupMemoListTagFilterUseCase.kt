package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListTagFilterRepository
import kotlin.uuid.Uuid
import org.koin.core.annotation.Factory

@Factory
public class AddBuddyGroupMemoListTagFilterUseCase internal constructor(
    private val buddyGroupMemoListTagFilterRepository: BuddyGroupMemoListTagFilterRepository,
) {
    public suspend operator fun invoke(buddyGroupId: Uuid, tagId: Uuid): Result<Unit> {
        return runCatching { buddyGroupMemoListTagFilterRepository.upsert(buddyGroupId, tagId, true) }
    }
}
