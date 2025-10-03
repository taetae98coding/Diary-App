package io.github.taetae98coding.diary.feature.more.finish

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoStateHolder
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoStateHolderTemplate
import org.koin.core.annotation.Factory

@Factory
internal class FinishMemoViewModel(
    strategy: AccountFinishMemoStrategy,
    stateHolder: FinishedMemoStateHolderTemplate = FinishedMemoStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    FinishedMemoStateHolder by stateHolder
