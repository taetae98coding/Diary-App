package io.github.taetae98coding.diary.feature.buddy.group.memo.add

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.core.navigation.parameter.PrimaryTag
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStateHolder
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoAddViewModel(
    primaryTag: PrimaryTag,
    strategy: BuddyGroupMemoAddStrategy,
    stateHolder: MemoAddStateHolderTemplate = MemoAddStateHolderTemplate(
        primaryTag = primaryTag,
        strategy = strategy,
    ),
) : ViewModel(stateHolder),
    MemoAddStateHolder by stateHolder
