package io.github.taetae98coding.diary.presenter.tag.detail

import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import kotlinx.coroutines.flow.StateFlow

public interface TagDetailStateHolder {
    public val detail: StateFlow<TagDetail?>
    public val finishUiState: StateFlow<TagDetailFinishUiState>
    public val floatingUiState: StateFlow<TagDetailFloatingUiState>
    public val effect: StateFlow<TagDetailEffect>
    public fun fetch()
    public fun finish()
    public fun restart()
    public fun delete()
    public fun update(detail: TagDetail)
    public fun clearEffect()
}
