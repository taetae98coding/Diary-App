package io.github.taetae98coding.diary.feature.memo.filter

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterStateHolder
import io.github.taetae98coding.diary.presenter.memo.filter.MemoListFilterStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MemoListFilterViewModel(
    strategy: AccountMemoListFilterStrategy,
    stateHolder: MemoListFilterStateHolderTemplate = MemoListFilterStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoListFilterStateHolder by stateHolder
