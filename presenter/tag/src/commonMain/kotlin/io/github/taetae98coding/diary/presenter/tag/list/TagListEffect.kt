package io.github.taetae98coding.diary.presenter.tag.list

import kotlin.uuid.Uuid

public sealed class TagListEffect {
    public data object None : TagListEffect()

    public data class FinishSuccess(
        val tagId: Uuid,
    ) : TagListEffect()

    public data class DeleteSuccess(
        val tagId: Uuid,
    ) : TagListEffect()

    public data object FetchError : TagListEffect()
    public data object UnknownError : TagListEffect()
}
