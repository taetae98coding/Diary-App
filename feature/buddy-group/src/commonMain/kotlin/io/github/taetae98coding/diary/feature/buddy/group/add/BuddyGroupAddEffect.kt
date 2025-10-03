package io.github.taetae98coding.diary.feature.buddy.group.add

internal sealed class BuddyGroupAddEffect {
    data object None : BuddyGroupAddEffect()
    data object AddSuccess : BuddyGroupAddEffect()
    data object TitleBlank : BuddyGroupAddEffect()
    data object BuddyEmpty : BuddyGroupAddEffect()
    data object UnknownError : BuddyGroupAddEffect()
}
