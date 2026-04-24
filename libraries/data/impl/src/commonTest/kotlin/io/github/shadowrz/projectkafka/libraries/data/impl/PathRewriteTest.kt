package io.github.shadowrz.projectkafka.libraries.data.impl

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PathRewriteTest :
    StringSpec({
        "relative to absolute" {
            val uri = "assets/7f.webp"
            val root = "/data/"
            uri.toAbsolute(root) shouldBe "/data/assets/7f.webp"
        }

        "relative to absolute with leading slash" {
            val uri = "/assets/7f.webp"
            val root = "/data/"
            uri.toAbsolute(root) shouldBe "/assets/7f.webp"
        }

        "absolute to relative within root" {
            val root = "/data/"
            val uri = "/data/assets/7f.webp"
            uri.toRelative(root) shouldBe "assets/7f.webp"
        }

        "absolute to relative outside root" {
            val root = "/data/"
            val uri = "/files/assets/7f.webp"
            uri.toRelative(root) shouldBe "/files/assets/7f.webp"
        }
    })
