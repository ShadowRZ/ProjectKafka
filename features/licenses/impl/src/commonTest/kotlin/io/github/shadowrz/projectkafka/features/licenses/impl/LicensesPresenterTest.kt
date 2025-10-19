package io.github.shadowrz.projectkafka.features.licenses.impl

import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.License
import io.github.shadowrz.projectkafka.tests.utils.WarmUpRule
import io.github.shadowrz.projectkafka.tests.utils.test
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test

class LicensesPresenterTest {
    @get:Rule
    val warmUp = WarmUpRule()

    @Test
    fun `presenter - initial state`() =
        runTest {
            presenter().test {
                val state = awaitItem()
                assertSoftly(state) {
                    libraries.shouldNotBeNull {
                        libraries.shouldBeEmpty()
                        licenses.shouldBeEmpty()
                    }
                }
            }
        }
}

private fun TestScope.presenter(): LicensesPresenter {
    val libs =
        Libs(
            libraries = emptyList<Library>().toImmutableList(),
            licenses = emptySet<License>().toImmutableSet(),
        )
    val presenter = LicensesPresenter(libs)

    return presenter
}
