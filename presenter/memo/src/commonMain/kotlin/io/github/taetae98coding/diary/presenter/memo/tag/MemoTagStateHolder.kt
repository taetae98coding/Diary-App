package io.github.taetae98coding.diary.presenter.memo.tag

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.memo.PrimaryMemoTag
import io.github.taetae98coding.diary.core.entity.tag.SelectMemoTag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface MemoTagStateHolder {
    public val primaryMemoTagPagingData: Flow<PagingData<PrimaryMemoTag>>
    public val selectMemoTagPagingData: Flow<PagingData<SelectMemoTag>>
    public val memoTagEffect: StateFlow<MemoTagEffect>

    public fun fetchMemoTag()
    public fun selectPrimaryTag(tagId: Uuid)
    public fun removePrimaryTag()
    public fun selectMemoTag(tagId: Uuid)
    public fun unselectMemoTag(tagId: Uuid)
    public fun clearMemoTagEffect()
}
