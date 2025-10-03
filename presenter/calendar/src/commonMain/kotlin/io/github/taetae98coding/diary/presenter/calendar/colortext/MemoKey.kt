package io.github.taetae98coding.diary.presenter.calendar.colortext

import io.github.taetae98coding.diary.compose.calendar.item.CalendarItemKey
import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

@JvmInline
public value class MemoKey(
    public val memoId: Uuid,
) : CalendarItemKey {

    override val composeKey: Any
        get() = memoId.toString()
}
