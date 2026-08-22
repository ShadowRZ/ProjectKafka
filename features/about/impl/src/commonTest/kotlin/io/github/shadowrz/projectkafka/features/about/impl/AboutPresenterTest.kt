package io.github.shadowrz.projectkafka.features.about.impl

import io.github.shadowrz.projectkafka.buildmeta.BuildMeta
import io.github.shadowrz.projectkafka.tests.utils.test
import io.github.shadowrz.projectkafka.tests.utils.warmUpMolecule
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class AboutPresenterTest :
    FreeSpec({
        warmUpMolecule()

        "presenter - initial state" {
            runTest {
                presenter().test {
                    val state = awaitItem()
                    assertSoftly(state.buildMeta) {
                        applicationName shouldBe "Project Kafka"
                        applicationId shouldBe "io.github.shadowrz.projectkafka"
                        versionName shouldBe "1.0"
                        versionCode shouldBe 1
                        platform shouldBe BuildMeta.Platform.Desktop
                    }
                }
            }
        }
    })

private fun TestScope.presenter(): AboutPresenter {
    val presenter =
        AboutPresenter(
            BuildMeta(
                applicationName = "Project Kafka",
                applicationId = "io.github.shadowrz.projectkafka",
                versionName = "1.0",
                versionCode = 1,
                platform = BuildMeta.Platform.Desktop,
            )
        )

    return presenter
}
