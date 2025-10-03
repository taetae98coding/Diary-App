package io.github.taetae98coding.diary.feature.memo.detail

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStateHolder
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MemoTagViewModel(
    strategy: AccountMemoTagStrategy,
    stateHolder: MemoTagStateHolderTemplate = MemoTagStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoTagStateHolder by stateHolder
