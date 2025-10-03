package io.github.taetae98coding.diary.presenter.tag.add

import io.github.taetae98coding.diary.core.entity.tag.TagDetail
import kotlinx.coroutines.flow.StateFlow

public interface TagAddStateHolder {
    public val floatingUiState: StateFlow<TagAddFloatingUiState>
    public val effect: StateFlow<TagAddEffect>

    public fun add(detail: TagDetail)
    public fun clearEffect()
}
