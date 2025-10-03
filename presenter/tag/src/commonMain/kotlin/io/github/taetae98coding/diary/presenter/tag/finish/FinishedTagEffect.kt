package io.github.taetae98coding.diary.presenter.tag.finish

import kotlin.uuid.Uuid

public sealed class FinishedTagEffect {
    public data object None : FinishedTagEffect()

    public data class DeleteSuccess(
        val tagId: Uuid,
    ) : FinishedTagEffect()

    public data object UnknownError : FinishedTagEffect()
}
