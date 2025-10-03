package io.github.taetae98coding.diary.feature.buddy.group.tag.finish

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.Tag
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.DeleteBuddyGroupTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.PageBuddyGroupFinishedTagUseCase
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.RestoreBuddyGroupTagUseCase
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagStrategy
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupFinishedTagStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val pageBuddyGroupFinishedTagUseCase: PageBuddyGroupFinishedTagUseCase,
    private val deleteBuddyGroupTagUseCase: DeleteBuddyGroupTagUseCase,
    private val restoreBuddyGroupTagUseCase: RestoreBuddyGroupTagUseCase,
) : FinishedTagStrategy {
    override fun page(): Flow<Result<PagingData<Tag>>> {
        return pageBuddyGroupFinishedTagUseCase(buddyGroupId.value)
    }

    override suspend fun delete(tagId: Uuid): Result<Unit> {
        return deleteBuddyGroupTagUseCase(buddyGroupId.value, tagId)
    }

    override suspend fun restore(tagId: Uuid): Result<Unit> {
        return restoreBuddyGroupTagUseCase(buddyGroupId.value, tagId)
    }
}
