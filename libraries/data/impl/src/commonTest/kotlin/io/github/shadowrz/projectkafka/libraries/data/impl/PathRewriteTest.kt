package io.github.shadowrz.projectkafka.libraries.data.impl

import io.github.shadowrz.projectkafka.libraries.data.api.MediaFile
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import okio.Buffer
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

class PathRewriteTest :
    FreeSpec({
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

        "write as hashed" {
            val buffer = Buffer()
            buffer.writeUtf8("Hello World")
            buffer.flush()

            val filesystem = FakeFileSystem()
            filesystem.createDirectory("/cache".toPath())
            filesystem.createDirectory("/files".toPath())
            filesystem.write("/cache/1.webp".toPath()) {
                writeAll(buffer)
            }
            filesystem.writeAsHashed("/files".toPath(), "/cache/1.webp".toPath())
            filesystem.exists("/files/assets/a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e.webp".toPath()).shouldBeTrue()
        }

        "rewrite to persisted" -
            {
                "in cache" {
                    val filesystem = FakeFileSystem()
                    val buffer = Buffer()
                    buffer.writeUtf8("Hello World")
                    buffer.flush()
                    filesystem.createDirectory("/cache".toPath())
                    filesystem.createDirectory("/files".toPath())
                    filesystem.write("/cache/1.webp".toPath()) {
                        writeAll(buffer)
                    }

                    val mediaFile = MediaFile("/cache/1.webp")
                    val rewrited = filesystem.run {
                        mediaFile.rewriteToPersisted("/files".toPath(), "/cache".toPath())
                    }
                    filesystem
                        .exists("/files/assets/a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e.webp".toPath())
                        .shouldBeTrue()

                    rewrited shouldBe "assets/a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e.webp"
                }

                "outside of cache" {
                    val filesystem = FakeFileSystem()
                    val buffer = Buffer()
                    buffer.writeUtf8("Hello World")
                    buffer.flush()
                    filesystem.createDirectory("/files".toPath())
                    filesystem.write("/files/1.webp".toPath()) {
                        writeAll(buffer)
                    }

                    val mediaFile = MediaFile("/files/1.webp")
                    val rewrited = filesystem.run {
                        mediaFile.rewriteToPersisted("/files".toPath(), "/cache".toPath())
                    }
                    filesystem.exists("/files/1.webp".toPath()).shouldBeTrue()

                    rewrited shouldBe "1.webp"
                }
            }
    })
