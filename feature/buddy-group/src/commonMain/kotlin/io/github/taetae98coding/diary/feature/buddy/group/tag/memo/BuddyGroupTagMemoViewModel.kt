package io.github.taetae98coding.diary.feature.buddy.group.tag.memo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.taetae98coding.diary.domain.tag.usecase.GetTagUseCase
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStateHolder
import io.github.taetae98coding.diary.presenter.memo.list.MemoListStateHolderTemplate
import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class BuddyGroupTagMemoViewModel(
    tagId: TagId,
    getTagUseCase: GetTagUseCase,
    private val strategy: BuddyGroupTagMemoListStrategy,
    private val stateHolder: MemoListStateHolderTemplate = MemoListStateHolderTemplate(strategy = strategy),
) : ViewModel(stateHolder),
    MemoListStateHolder by stateHolder {
    val title = getTagUseCase(tagId.value).mapLatest { it.getOrNull() }
        .mapLatest { it?.detail?.title.orEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "",
        )

    @JvmInline
    value class BuddyGroupId(
        val value: Uuid,
    )

    @JvmInline
    value class TagId(
        val value: Uuid,
    )
}
