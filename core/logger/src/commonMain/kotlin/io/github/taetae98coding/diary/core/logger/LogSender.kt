package io.github.taetae98coding.diary.core.logger

public interface LogSender<T : Any> {
    public fun log(data: T)
}
