package io.github.taetae98coding.diary.presenter.memo.list

import kotlin.uuid.Uuid

public sealed class MemoListEffect {
    public data object None : MemoListEffect()

    public data class FinishSuccess(
        val memoId: Uuid,
    ) : MemoListEffect()

    public data class DeleteSuccess(
        val memoId: Uuid,
    ) : MemoListEffect()

    public data object FetchError : MemoListEffect()
    public data object UnknownError : MemoListEffect()
}
