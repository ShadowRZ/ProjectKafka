package io.github.shadowrz.projectkafka.logging

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Severity.Assert
import co.touchlab.kermit.Severity.Debug
import co.touchlab.kermit.Severity.Error
import co.touchlab.kermit.Severity.Info
import co.touchlab.kermit.Severity.Verbose
import co.touchlab.kermit.Severity.Warn
import org.slf4j.LoggerFactory
import org.slf4j.event.Level.DEBUG
import org.slf4j.event.Level.ERROR
import org.slf4j.event.Level.INFO
import org.slf4j.event.Level.TRACE
import org.slf4j.event.Level.WARN
import org.slf4j.spi.LoggingEventBuilder

class Slf4jLogWriter : LogWriter() {
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        LoggerFactory.getLogger(tag).withSeverity(severity).withThrowable(throwable).log(message)
    }

    private fun org.slf4j.Logger.withSeverity(severity: Severity) =
        atLevel(
            when (severity) {
                Verbose -> TRACE
                Debug -> DEBUG
                Info -> INFO
                Warn -> WARN
                Error -> ERROR
                Assert -> ERROR
            }
        )

    private fun LoggingEventBuilder.withThrowable(throwable: Throwable?): LoggingEventBuilder =
        if (throwable != null) {
            setCause(throwable)
        } else this
}
