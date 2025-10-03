package io.github.taetae98coding.diary.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import io.github.taetae98coding.diary.compose.core.icon.BuddyGroupImageVector
import io.github.taetae98coding.diary.compose.core.icon.CalendarImageVector
import io.github.taetae98coding.diary.compose.core.icon.MemoImageVector
import io.github.taetae98coding.diary.compose.core.icon.MoreImageVector
import io.github.taetae98coding.diary.compose.core.icon.TagImageVector
import io.github.taetae98coding.diary.core.navigation.buddy.BuddyGroupListNavKey
import io.github.taetae98coding.diary.core.navigation.calendar.CalendarNavKey
import io.github.taetae98coding.diary.core.navigation.memo.MemoListNavKey
import io.github.taetae98coding.diary.core.navigation.more.MoreNavKey
import io.github.taetae98coding.diary.core.navigation.tag.TagListNavKey

internal enum class TopLevelDestination(
    val imageVector: ImageVector,
    val title: String,
    val navKey: NavKey,
) {
    Memo(
        imageVector = MemoImageVector,
        title = "메모",
        navKey = MemoListNavKey,
    ),
    Tag(
        imageVector = TagImageVector,
        title = "태그",
        navKey = TagListNavKey,
    ),
    Calendar(
        imageVector = CalendarImageVector,
        title = "캘린더",
        navKey = CalendarNavKey,
    ),
    BuddyGroup(
        imageVector = BuddyGroupImageVector,
        title = "버디 그룹",
        navKey = BuddyGroupListNavKey,
    ),
    More(
        imageVector = MoreImageVector,
        title = "더보기",
        navKey = MoreNavKey,
    ),
}
