package io.github.taetae98coding.diary.compose.calendar.item

import kotlin.jvm.JvmInline

@JvmInline
public value class PrimitiveCalendarItemKey(
    public val value: Any,
) : CalendarItemKey {
    override val composeKey: Any
        get() = value
}
