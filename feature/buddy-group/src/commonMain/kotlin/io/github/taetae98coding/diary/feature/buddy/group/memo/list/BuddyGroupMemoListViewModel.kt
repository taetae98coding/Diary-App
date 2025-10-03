package io.github.taetae98coding.diary.feature.buddy.group.memo.list

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStateHolder
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoListViewModel(
    private val strategy: BuddyGroupMemoListStrategy,
    private val stateHolder: MemoListStateHolderTemplate = MemoListStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoListStateHolder by stateHolder
