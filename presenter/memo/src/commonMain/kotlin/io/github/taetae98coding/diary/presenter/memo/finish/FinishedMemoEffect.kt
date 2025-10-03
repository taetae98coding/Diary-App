package io.github.taetae98coding.diary.presenter.memo.finish

import kotlin.uuid.Uuid

public sealed class FinishedMemoEffect {
    public data object None : FinishedMemoEffect()

    public data class DeleteSuccess(
        val memoId: Uuid,
    ) : FinishedMemoEffect()

    public data object UnknownError : FinishedMemoEffect()
}
