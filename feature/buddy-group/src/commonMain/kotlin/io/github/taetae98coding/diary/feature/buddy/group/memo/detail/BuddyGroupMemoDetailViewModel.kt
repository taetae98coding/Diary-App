package io.github.taetae98coding.diary.feature.buddy.group.memo.detail

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailStateHolder
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailStateHolderTemplate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoDetailViewModel(
    strategy: BuddyGroupMemoDetailStrategy,
    stateHolder: MemoDetailStateHolderTemplate = MemoDetailStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoDetailStateHolder by stateHolder
