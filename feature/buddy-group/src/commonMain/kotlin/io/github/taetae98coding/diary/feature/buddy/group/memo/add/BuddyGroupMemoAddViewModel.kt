package io.github.taetae98coding.diary.feature.buddy.group.memo.add

import androidx.lifecycle.ViewModel
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStateHolder
import io.github.taetae98coding.diary.presenter.memo.add.MemoAddStateHolderTemplate
import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupMemoAddViewModel(
    private val primaryTag: PrimaryTag,
    private val strategy: BuddyGroupMemoAddStrategy,
    private val stateHolder: MemoAddStateHolderTemplate = MemoAddStateHolderTemplate(
        primaryTag = primaryTag.value,
        strategy = strategy,
    ),
) : ViewModel(stateHolder),
    MemoAddStateHolder by stateHolder {
    @JvmInline
    value class BuddyGroupId(
        val value: Uuid,
    )

    @JvmInline
    value class PrimaryTag(
        val value: Uuid?,
    )
}
