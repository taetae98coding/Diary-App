package io.github.taetae98coding.diary.domain.buddy.group.usecase.calendar

import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoCalendarTagFilterRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class HasBuddyGroupCalendarFilterUseCase internal constructor(
    private val buddyGroupMemoCalendarTagFilterRepository: BuddyGroupMemoCalendarTagFilterRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid): Flow<Result<Boolean>> {
        return flow { emitAll(buddyGroupMemoCalendarTagFilterRepository.hasFilter(buddyGroupId)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}

