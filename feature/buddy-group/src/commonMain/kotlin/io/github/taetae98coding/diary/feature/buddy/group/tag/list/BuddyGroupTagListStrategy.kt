package io.github.taetae98coding.diary.feature.buddy.group.tag.list

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.DeleteBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.FinishBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.PageBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.RestartBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.RestoreBuddyGroupTagUseCase
import io.github.taetae98coding.diary.presenter.tag.list.TagListStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupTagListStrategy(
    private val buddyGroupId: Uuid,
    private val pageBuddyGroupTagUseCase: PageBuddyGroupTagUseCase,
    private val finishBuddyGroupTagUseCase: FinishBuddyGroupTagUseCase,
    private val deleteBuddyGroupTagUseCase: DeleteBuddyGroupTagUseCase,
    private val restartBuddyGroupTagUseCase: RestartBuddyGroupTagUseCase,
    private val restoreBuddyGroupTagUseCase: RestoreBuddyGroupTagUseCase,
) : TagListStrategy {
    override fun pageTag(): Flow<Result<PagingData<Tag>>> {
        return pageBuddyGroupTagUseCase(buddyGroupId)
    }

    override suspend fun finishTag(tagId: Uuid): Result<Unit> {
        return finishBuddyGroupTagUseCase(buddyGroupId, tagId)
    }

    override suspend fun deleteTag(tagId: Uuid): Result<Unit> {
        return deleteBuddyGroupTagUseCase(buddyGroupId, tagId)
    }

    override suspend fun restartTag(tagId: Uuid): Result<Unit> {
        return restartBuddyGroupTagUseCase(buddyGroupId, tagId)
    }

    override suspend fun restoreTag(tagId: Uuid): Result<Unit> {
        return restoreBuddyGroupTagUseCase(buddyGroupId, tagId)
    }
}
