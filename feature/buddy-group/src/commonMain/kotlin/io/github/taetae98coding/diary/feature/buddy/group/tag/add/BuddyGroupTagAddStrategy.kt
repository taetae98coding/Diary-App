package io.github.taetae98coding.diary.feature.buddy.group.tag.add

import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.domain.buddy.group.usecase.tag.AddBuddyGroupTagUseCase
import io.github.taetae98coding.diary.presenter.tag.add.TagAddStrategy
import org.koin.core.annotation.Factory

@Factory
internal class BuddyGroupTagAddStrategy(
    private val buddyGroupId: BuddyGroupId,
    private val addBuddyGroupTagUseCase: AddBuddyGroupTagUseCase,
) : TagAddStrategy {
    override suspend fun addTag(detail: TagDetail): Result<Unit> {
        return addBuddyGroupTagUseCase(buddyGroupId.value, detail)
    }
}
