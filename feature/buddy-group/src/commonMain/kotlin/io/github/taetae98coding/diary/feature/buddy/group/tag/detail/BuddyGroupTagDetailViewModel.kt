package io.github.taetae98coding.diary.feature.buddy.group.tag.detail

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailStateHolder
import io.github.taetae98coding.diary.presenter.tag.detail.TagDetailStateHolderTemplate
import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupTagDetailViewModel(
    private val strategy: BuddyGroupTagDetailStrategy,
    private val stateHolder: TagDetailStateHolderTemplate = TagDetailStateHolderTemplate(strategy),
) : ViewModel(stateHolder),
    TagDetailStateHolder by stateHolder {
    @JvmInline
    value class BuddyGroupId(
        val value: Uuid,
    )

    @JvmInline
    value class TagId(
        val value: Uuid,
    )
}
