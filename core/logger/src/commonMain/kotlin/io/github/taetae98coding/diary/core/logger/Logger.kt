package io.github.taetae98coding.diary.core.logger

import kotlin.reflect.KClass

public data object Logger {
    private val logSenderMap = mutableMapOf<KClass<*>, MutableList<LogSender<Any>>>()

    public fun add(kClass: KClass<Any>, logSender: LogSender<Any>): Logger {
        logSenderMap.getOrPut(kClass) { mutableListOf() }.add(logSender)
        return this
    }

    public fun log(data: Any) {
        logSenderMap.forEach { (kClass, logSenderList) ->
            if (kClass.isInstance(data)) {
                logSenderList.forEach { logSender ->
                    logSender.log(data)
                }
            }
        }
    }
}
