package io.github.taetae98coding.diary.core.navigation.parameter

import kotlin.jvm.JvmInline
import kotlin.uuid.Uuid

@JvmInline
public value class MemoId(
    public val value: Uuid,
)
