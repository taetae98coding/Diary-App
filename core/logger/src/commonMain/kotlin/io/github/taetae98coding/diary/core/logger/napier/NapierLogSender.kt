package io.github.taetae98coding.diary.core.logger.napier

import io.github.aakira.napier.Napier
import io.github.taetae98coding.diary.core.logger.LogSender

public class NapierLogSender : LogSender<Any> {
    override fun log(data: Any) {
        Napier.d(
            message = data.toString(),
            tag = "Napier",
        )
    }
}
