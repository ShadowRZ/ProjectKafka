package io.github.shadowrz.projectkafka.logging

import co.touchlab.kermit.Logger
import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.helpers.NOPMDCAdapter
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

// https://github.com/psh/KermitExt/tree/main/slf4j-over-kermit/src/jvmMain
class KafkaKermitServiceProvider : SLF4JServiceProvider {
    private val markerFactory = BasicMarkerFactory()
    private val mdcAdapter = NOPMDCAdapter()

    override fun getLoggerFactory(): ILoggerFactory =
        ILoggerFactory {
            KermitLogger(it, Logger)
        }

    override fun getMarkerFactory(): IMarkerFactory = markerFactory

    override fun getMDCAdapter(): MDCAdapter = mdcAdapter

    override fun getRequestedApiVersion(): String = "2.0.0"

    override fun initialize() = Unit
}
