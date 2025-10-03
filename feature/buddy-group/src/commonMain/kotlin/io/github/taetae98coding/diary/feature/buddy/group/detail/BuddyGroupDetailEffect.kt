package io.github.taetae98coding.diary.feature.buddy.group.detail

internal sealed class BuddyGroupDetailEffect {
    data object None : BuddyGroupDetailEffect()
    data object UpdateFinish : BuddyGroupDetailEffect()
    data object FetchFail : BuddyGroupDetailEffect()
    data object UnknownError : BuddyGroupDetailEffect()
}
