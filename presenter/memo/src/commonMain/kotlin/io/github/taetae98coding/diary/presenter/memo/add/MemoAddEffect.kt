package io.github.taetae98coding.diary.presenter.memo.add

public sealed class MemoAddEffect {
    public data object None : MemoAddEffect()
    public data object AddSuccess : MemoAddEffect()
    public data object TitleBlank : MemoAddEffect()
    public data object UnknownError : MemoAddEffect()
}
