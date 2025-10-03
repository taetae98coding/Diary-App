package io.github.taetae98coding.diary.feature.buddy.group.memo.finish

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.core.navigation.parameter.BuddyGroupId
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoStateHolder
import io.github.taetae98coding.diary.presenter.memo.finish.FinishedMemoStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupFinishMemoViewModel(
    strategy: BuddyGroupFinishMemoStrategy,
    stateHolder: FinishedMemoStateHolderTemplate = FinishedMemoStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    FinishedMemoStateHolder by stateHolder
