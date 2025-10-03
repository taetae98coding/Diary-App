package io.github.taetae98coding.diary.feature.memo.add

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStateHolder
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStateHolderTemplate
import kotlin.uuid.Uuid
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class MemoAddViewModel(
    private val primaryTag: Uuid?,
    private val strategy: AccountMemoAddStrategy,
    private val stateHolder: MemoAddStateHolderTemplate = MemoAddStateHolderTemplate(
        primaryTag = primaryTag,
        strategy = strategy,
    ),
) : ViewModel(stateHolder),
    MemoAddStateHolder by stateHolder
