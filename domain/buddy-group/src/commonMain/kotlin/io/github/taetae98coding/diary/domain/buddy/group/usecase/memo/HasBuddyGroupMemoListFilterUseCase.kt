package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListTagFilterRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class HasBuddyGroupMemoListFilterUseCase internal constructor(
    private val buddyGroupMemoListTagFilterRepository: BuddyGroupMemoListTagFilterRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid): Flow<Result<Boolean>> {
        return flow { emitAll(buddyGroupMemoListTagFilterRepository.hasFilter(buddyGroupId)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
