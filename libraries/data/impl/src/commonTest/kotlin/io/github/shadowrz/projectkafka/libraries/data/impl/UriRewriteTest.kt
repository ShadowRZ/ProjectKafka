package io.github.shadowrz.projectkafka.libraries.data.impl

import com.eygraber.uri.toKmpUri
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class UriRewriteTest :
    StringSpec({
        "uri - relative to absolute" {
            val uri = "assets/7f.webp".toKmpUri()
            val root = "/data/"
            uri.toAbsolute(root) shouldBe "/data/assets/7f.webp".toKmpUri()
        }

        "uri - relative to absolute with leading slash" {
            val uri = "/assets/7f.webp".toKmpUri()
            val root = "/data/"
            uri.toAbsolute(root) shouldBe "/assets/7f.webp".toKmpUri()
        }

        "uri - relative to absolute using custom protocol" {
            val uri = "io.github.shadowrz.projectkafka.internal:assets/7f.webp".toKmpUri()
            val root = "/data/"
            uri.toAbsolute(root) shouldBe "/data/assets/7f.webp".toKmpUri()
        }

        "uri - absolute to relative within root" {
            val root = "/data/"
            val uri = "/data/assets/7f.webp".toKmpUri()
            uri.toDbRelative(root) shouldBe "assets/7f.webp".toKmpUri()
        }

        "uri - absolute to relative outside root" {
            val root = "/data/"
            val uri = "/files/assets/7f.webp".toKmpUri()
            uri.toDbRelative(root) shouldBe "/files/assets/7f.webp".toKmpUri()
        }
    })
