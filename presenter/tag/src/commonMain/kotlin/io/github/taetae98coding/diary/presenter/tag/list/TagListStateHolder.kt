package io.github.taetae98coding.diary.presenter.tag.list

import androidx.paging.PagingData
import io.github.taetae98coding.diary.core.entity.tag.Tag
import kotlin.uuid.Uuid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

public interface TagListStateHolder {
    public val pagingData: Flow<PagingData<Tag>>
    public val effect: StateFlow<TagListEffect>

    public fun fetch()
    public fun finish(tagId: Uuid)
    public fun delete(tagId: Uuid)
    public fun restart(tagId: Uuid)
    public fun restore(tagId: Uuid)
    public fun clearEffect()
}
