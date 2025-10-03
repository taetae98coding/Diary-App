package io.github.taetae98coding.diary.feature.memo.list

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStateHolder
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MemoListViewModel(
    strategy: AccountMemoListStrategy,
    stateHolder: MemoListStateHolderTemplate = MemoListStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoListStateHolder by stateHolder
