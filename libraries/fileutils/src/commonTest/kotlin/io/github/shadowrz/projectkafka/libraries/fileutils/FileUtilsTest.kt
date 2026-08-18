package io.github.shadowrz.projectkafka.libraries.fileutils

import io.github.shadowrz.projectkafka.libraries.uniqueid.UniqueID
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import okio.Path.Companion.toPath
import okio.buffer
import okio.fakefilesystem.FakeFileSystem

class FileUtilsTest :
    FreeSpec({
        "createTempFile creates a new temporary file with extension" {
            val filesystem = FakeFileSystem()
            filesystem.createTempFile(
                baseDir = "/".toPath(),
                extension = "txt",
                uniqueID = UniqueID.IncrementingID(),
            )
            filesystem.exists("/1.txt".toPath()).shouldBeTrue()
        }

        "writeTempFile writes a new temporary file with extension" {
            val filesystem = FakeFileSystem()
            filesystem.writeTempFile(
                baseDir = "/".toPath(),
                extension = "txt",
                uniqueID = UniqueID.IncrementingID(),
            ) {
                writeUtf8("Hello World")
            }
            filesystem.exists("/1.txt".toPath()).shouldBeTrue()

            val result = filesystem.source("/1.txt".toPath()).buffer().readUtf8()
            result shouldBe "Hello World"
        }

        "createTempFile creates a new temporary file without extension" {
            val filesystem = FakeFileSystem()
            filesystem.createTempFile(
                baseDir = "/".toPath(),
                uniqueID = UniqueID.IncrementingID("prefix-"),
            )
            filesystem.exists("/prefix-1".toPath()).shouldBeTrue()
        }

        "writeTempFile writes a new temporary file without extension" {
            val filesystem = FakeFileSystem()
            filesystem.writeTempFile(
                baseDir = "/".toPath(),
                uniqueID = UniqueID.IncrementingID("prefix-"),
            ) {
                writeUtf8("Hello World")
            }
            filesystem.exists("/prefix-1".toPath()).shouldBeTrue()

            val result = filesystem.source("/prefix-1".toPath()).buffer().readUtf8()
            result shouldBe "Hello World"
        }
    })
