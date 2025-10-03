package io.github.taetae98coding.diary.domain.buddy.group.usecase.memo

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.TagFilter
import io.github.taetae98coding.diary.domain.buddy.group.repository.BuddyGroupMemoListTagFilterRepository
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import org.koin.core.annotation.Factory

@Factory
public class PageBuddyGroupMemoListTagFilterUseCase internal constructor(
    private val buddyGroupMemoListTagFilterRepository: BuddyGroupMemoListTagFilterRepository,
) {
    public operator fun invoke(buddyGroupId: Uuid): Flow<Result<PagingData<TagFilter>>> {
        return flow { emitAll(buddyGroupMemoListTagFilterRepository.page(buddyGroupId)) }
            .mapLatest { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}
