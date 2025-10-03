package io.github.taetae98coding.diary.presenter.tag.detail

public sealed class TagDetailEffect {
    public data object None : TagDetailEffect()
    public data object DeleteFinish : TagDetailEffect()
    public data object UpdateFinish : TagDetailEffect()
    public data object FetchError : TagDetailEffect()
    public data object UnknownError : TagDetailEffect()
}
