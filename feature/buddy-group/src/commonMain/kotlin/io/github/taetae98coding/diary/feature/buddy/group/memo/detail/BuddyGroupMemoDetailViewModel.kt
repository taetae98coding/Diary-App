package io.github.taetae98coding.diary.feature.buddy.group.memo.detail

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailStateHolder
import io.github.taetae98coding.diary.presenter.memo.detail.MemoDetailStateHolderTemplate
import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoDetailViewModel(
    private val strategy: BuddyGroupMemoDetailStrategy,
    private val stateHolder: MemoDetailStateHolderTemplate = MemoDetailStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoDetailStateHolder by stateHolder {
    @JvmInline
    value class BuddyGroupId(
        val value: Uuid,
    )

    @JvmInline
    value class MemoId(
        val value: Uuid,
    )
}
