package io.github.shadowrz.projectkafka.libraries.zipwriter

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem
import okio.openZip
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ZipWriterTest {
    private lateinit var fileSystem: FakeFileSystem

    @Test
    fun `zip writer - write simple ZIP file`() {
        val path = "file.zip".toPath()
        fileSystem.write(path) {
            writeZip {
                file("a.txt") {
                    writeUtf8("Hello World")
                }
                directory("dir")
            }
        }
        fileSystem.exists(path).shouldBeTrue()
        fileSystem.openZip(path).run {
            val path = "a.txt".toPath()
            exists(path).shouldBeTrue()
            "dir".toPath().let {
                exists(it).shouldBeTrue()
                metadata(it).isDirectory.shouldBeTrue()
            }
            read(path) {
                readUtf8().shouldBe("Hello World")
            }
        }
    }

    @Test
    fun `zip writer - ZIP entry with implict subdir`() {
        val path = "file.zip".toPath()
        fileSystem.write(path) {
            writeZip {
                file("dir/a.txt") {
                    writeUtf8("Hello World")
                }
            }
        }
        fileSystem.exists(path).shouldBeTrue()
        fileSystem.openZip(path).run {
            "dir".toPath().let {
                exists(it).shouldBeTrue()
                metadata(it).isDirectory.shouldBeTrue()
            }
            "dir/a.txt".toPath().let {
                exists(it).shouldBeTrue()
                metadata(it).isRegularFile.shouldBeTrue()
                read(it) {
                    readUtf8().shouldBe("Hello World")
                }
            }
        }
    }

    @Test
    fun `zip writer - write nested directories`() {
        val path = "file.zip".toPath()
        fileSystem.write(path) {
            writeZip {
                directory("dir1")
                directory("dir2") {
                    directory("dir3")
                    file("file1") {}
                }
            }
        }
        fileSystem.exists(path).shouldBeTrue()
        fileSystem.openZip(path).run {
            "dir1".toPath().let {
                exists(it).shouldBeTrue()
                metadata(it).isDirectory.shouldBeTrue()
            }
            "dir2".toPath().let {
                exists(it).shouldBeTrue()
                metadata(it).isDirectory.shouldBeTrue()
            }
            "dir2/dir3".toPath().let {
                exists(it).shouldBeTrue()
                metadata(it).isDirectory.shouldBeTrue()
            }
            "dir2/dir3/file1".toPath().let {
                exists(it).shouldBeFalse()
            }
        }
    }

    @BeforeTest
    fun setUp() {
        fileSystem = FakeFileSystem()
    }

    @AfterTest
    fun tearDown() {
        fileSystem.checkNoOpenFiles()
    }
}
