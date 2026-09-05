package io.github.shadowrz.projectkafka.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.Configurator
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import ch.qos.logback.core.spi.ContextAwareBase

class KafkaConfigurator : ContextAwareBase(), Configurator {
    override fun configure(loggerContext: LoggerContext): Configurator.ExecutionStatus {
        val ca = ConsoleAppender<ILoggingEvent>()
        ca.setContext(context)
        ca.setName("console")
        val encoder = LayoutWrappingEncoder<ILoggingEvent>()
        encoder.setContext(context)

        val layout = PatternLayout()
        layout.pattern = "%black(%date{yyyy-MM-dd HH:mm:ss.SSSZ}) %boldMagenta([%thread]) %highlight(%-5level) %black(%logger{35}:) %msg%n"

        layout.setContext(context)
        layout.start()
        encoder.setLayout(layout)

        ca.setEncoder(encoder)
        ca.start()

        val rootLogger: Logger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME)
        rootLogger.level = Level.INFO
        rootLogger.addAppender(ca)

        val sqliteLogger = loggerContext.getLogger("org.sqlite.core.NativeDB")
        sqliteLogger.level = Level.INFO

        // Don't invoke any next configurations
        return Configurator.ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY
    }
}
