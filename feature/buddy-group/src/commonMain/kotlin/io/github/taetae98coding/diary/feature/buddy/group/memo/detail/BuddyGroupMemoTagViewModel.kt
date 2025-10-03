package io.github.taetae98coding.diary.feature.buddy.group.memo.detail

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStateHolder
import io.github.taetae98coding.diary.presenter.memo.tag.MemoTagStateHolderTemplate
import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoTagViewModel(
    private val strategy: BuddyGroupMemoTagStrategy,
    private val stateHolder: MemoTagStateHolderTemplate = MemoTagStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoTagStateHolder by stateHolder {
    @JvmInline
    value class BuddyGroupId(
        val value: Uuid,
    )

    @JvmInline
    value class MemoId(
        val value: Uuid,
    )
}
