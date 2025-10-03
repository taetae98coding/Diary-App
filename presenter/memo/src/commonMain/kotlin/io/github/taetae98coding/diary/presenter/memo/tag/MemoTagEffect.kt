package io.github.taetae98coding.diary.presenter.memo.tag

public sealed class MemoTagEffect {
    public data object None : MemoTagEffect()
    public data object FetchError : MemoTagEffect()
    public data object UnknownError : MemoTagEffect()
}
