package io.github.taetae98coding.diary.feature.buddy.group.tag.add

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.tag.add.TagAddStateHolder
import io.github.taetae98coding.diary.presenter.tag.add.TagAddStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupTagAddViewModel(
    strategy: BuddyGroupTagAddStrategy,
    stateHolder: TagAddStateHolderTemplate = TagAddStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    TagAddStateHolder by stateHolder
