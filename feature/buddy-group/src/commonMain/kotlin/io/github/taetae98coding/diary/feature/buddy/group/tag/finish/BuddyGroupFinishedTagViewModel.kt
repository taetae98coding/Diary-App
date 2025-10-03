package io.github.taetae98coding.diary.feature.buddy.group.tag.finish

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagStateHolder
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupFinishedTagViewModel(
    strategy: BuddyGroupFinishedTagStrategy,
    stateHolder: FinishedTagStateHolderTemplate = FinishedTagStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    FinishedTagStateHolder by stateHolder
