package io.github.shadowrz.projectkafka.features.licenses.impl

import androidx.compose.runtime.Composable
import com.mikepenz.aboutlibraries.Libs
import dev.zacsweers.metro.Inject
import io.github.shadowrz.hanekokoro.framework.runtime.Presenter

@Inject
class LicensesPresenter(
    private val libs: Libs,
) : Presenter<LicensesState> {
    @Composable
    override fun present(): LicensesState =
        LicensesState(
            libraries = libs,
        )
}
