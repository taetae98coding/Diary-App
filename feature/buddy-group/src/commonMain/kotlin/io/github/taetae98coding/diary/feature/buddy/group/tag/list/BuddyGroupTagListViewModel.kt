package io.github.taetae98coding.diary.feature.buddy.group.tag.list

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.tag.list.TagListStateHolder
import io.github.taetae98coding.diary.presenter.tag.list.TagListStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupTagListViewModel(
    strategy: BuddyGroupTagListStrategy,
    stateHolder: TagListStateHolderTemplate = TagListStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    TagListStateHolder by stateHolder
