package io.github.taetae98coding.diary.presenter.tag.add

public sealed class TagAddEffect {
    public data object None : TagAddEffect()
    public data object AddSuccess : TagAddEffect()
    public data object TitleBlank : TagAddEffect()
    public data object UnknownError : TagAddEffect()
}
