package io.github.taetae98coding.diary.core.database.transaction

public interface DatabaseTransactor {
    public suspend fun immediate(action: suspend () -> Unit)
}
