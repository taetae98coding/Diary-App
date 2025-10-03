package io.github.taetae98coding.diary.domain.buddy.group.usecase

import io.github.taetae98coding.diary.core.entity.buddy.BuddyGroup
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class GetBuddyGroupUseCase internal constructor(
    private val buddyGroupRepository: BuddyGroupRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid): Flow<Result<BuddyGroup?>> {
        return flow { emitAll(buddyGroupRepository.get(buddyGroupId)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
