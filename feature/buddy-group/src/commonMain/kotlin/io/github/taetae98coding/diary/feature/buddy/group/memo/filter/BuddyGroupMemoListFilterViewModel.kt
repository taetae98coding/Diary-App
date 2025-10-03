package io.github.taetae98coding.diary.feature.buddy.group.memo.filter

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterStateHolder
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoListFilterViewModel(
    strategy: BuddyGroupMemoListFilterStrategy,
    stateHolder: MemoListFilterStateHolderTemplate = MemoListFilterStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoListFilterStateHolder by stateHolder
