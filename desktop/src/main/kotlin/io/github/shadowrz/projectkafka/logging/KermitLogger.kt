package io.github.shadowrz.projectkafka.logging

import co.touchlab.kermit.BaseLogger
import co.touchlab.kermit.Severity
import org.slf4j.Marker
import org.slf4j.event.Level
import org.slf4j.helpers.AbstractLogger

// https://github.com/psh/KermitExt/tree/main/slf4j-over-kermit/src/jvmMain
class KermitLogger(
    private val loggerName: String,
    private val logger: BaseLogger,
) : AbstractLogger() {
    override fun getName(): String = "ProjectKafka/SLF4J"

    override fun isTraceEnabled() = logger.config.minSeverity <= Severity.Verbose

    override fun isTraceEnabled(marker: Marker?) = logger.config.minSeverity <= Severity.Verbose

    override fun isDebugEnabled() = logger.config.minSeverity <= Severity.Debug

    override fun isDebugEnabled(marker: Marker?) = logger.config.minSeverity <= Severity.Debug

    override fun isInfoEnabled() = logger.config.minSeverity <= Severity.Info

    override fun isInfoEnabled(marker: Marker?) = logger.config.minSeverity <= Severity.Info

    override fun isWarnEnabled() = logger.config.minSeverity <= Severity.Warn

    override fun isWarnEnabled(marker: Marker?) = logger.config.minSeverity <= Severity.Warn

    override fun isErrorEnabled() = logger.config.minSeverity <= Severity.Error

    override fun isErrorEnabled(marker: Marker?) = logger.config.minSeverity <= Severity.Error

    override fun getFullyQualifiedCallerName(): String? = null

    @Suppress("detekt:SpreadOperator")
    override fun handleNormalizedLoggingCall(
        level: Level?,
        marker: Marker?,
        messagePattern: String?,
        arguments: Array<out Any>?,
        throwable: Throwable?,
    ) {
        val severity = when (level) {
            Level.ERROR -> Severity.Error
            Level.WARN -> Severity.Warn
            Level.INFO -> Severity.Info
            Level.DEBUG -> Severity.Debug
            else -> Severity.Verbose
        }

        val formatted = if (messagePattern != null && arguments != null) {
            String.format(messagePattern, *(arguments.toList().toTypedArray()))
        } else {
            null
        }

        messagePattern.let {
            logger.log(
                severity,
                marker?.toString() ?: loggerName,
                throwable,
                formatted ?: (messagePattern ?: ""),
            )
        }
    }
}
