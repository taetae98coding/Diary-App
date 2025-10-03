package io.github.taetae98coding.diary.feature.buddy.group.tag.detail

import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.DeleteBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.FinishBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.RestartBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.UpdateBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.FetchTagUseCase
import io.github.taetae98coding.diary.domain.tag.usecase.GetTagUseCase
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailStrategy
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupTagDetailStrategy(
    private val buddyGroupId: BuddyGroupTagDetailViewModel.BuddyGroupId,
    private val tagId: BuddyGroupTagDetailViewModel.TagId,
    private val fetchTagUseCase: FetchTagUseCase,
    private val getTagUseCase: GetTagUseCase,
    private val finishBuddyGroupTagUseCase: FinishBuddyGroupTagUseCase,
    private val restartBuddyGroupTagUseCase: RestartBuddyGroupTagUseCase,
    private val deleteBuddyGroupTagUseCase: DeleteBuddyGroupTagUseCase,
    private val updateBuddyGroupTagUseCase: UpdateBuddyGroupTagUseCase,
) : TagDetailStrategy {
    override fun getTag(): Flow<Result<Tag?>> {
        return getTagUseCase(tagId.value)
    }

    override suspend fun fetchTag(): Result<Unit> {
        return fetchTagUseCase(tagId.value)
    }

    override suspend fun finishTag(): Result<Unit> {
        return finishBuddyGroupTagUseCase(buddyGroupId.value, tagId.value)
    }

    override suspend fun restartTag(): Result<Unit> {
        return restartBuddyGroupTagUseCase(buddyGroupId.value, tagId.value)
    }

    override suspend fun deleteTag(): Result<Unit> {
        return deleteBuddyGroupTagUseCase(buddyGroupId.value, tagId.value)
    }

    override suspend fun updateTag(detail: TagDetail): Result<Unit> {
        return updateBuddyGroupTagUseCase(buddyGroupId.value, tagId.value, detail)
    }
}
