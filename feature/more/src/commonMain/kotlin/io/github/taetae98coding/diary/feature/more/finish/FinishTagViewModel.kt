package io.github.taetae98coding.diary.feature.more.finish

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagStateHolder
import io.github.taetae98coding.diary.presenter.tag.finish.FinishedTagStateHolderTemplate
import org.koin.core.annotation.Factory

@Factory
internal class FinishTagViewModel(
    strategy: AccountFinishedTagStrategy,
    stateHolder: FinishedTagStateHolderTemplate = FinishedTagStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    FinishedTagStateHolder by stateHolder
