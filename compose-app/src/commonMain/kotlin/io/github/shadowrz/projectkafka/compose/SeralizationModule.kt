package io.github.shadowrz.projectkafka.compose

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import io.github.shadowrz.projectkafka.features.about.api.AboutScreen
import io.github.shadowrz.projectkafka.features.createsystem.api.CreateSystemScreen
import io.github.shadowrz.projectkafka.features.datamanage.api.DataManageScreen
import io.github.shadowrz.projectkafka.features.editmember.api.AddMemberScreen
import io.github.shadowrz.projectkafka.features.editmember.api.EditMemberScreen
import io.github.shadowrz.projectkafka.features.home.api.HomeScreen
import io.github.shadowrz.projectkafka.features.licenses.api.LicensesScreen
import io.github.shadowrz.projectkafka.features.messages.api.MessagesScreen
import io.github.shadowrz.projectkafka.features.preferences.api.PreferencesScreen
import io.github.shadowrz.projectkafka.features.profile.api.MemberProfileScreen
import io.github.shadowrz.projectkafka.features.quickstart.api.QuickStartScreen
import io.github.shadowrz.projectkafka.features.switchsystem.api.SwitchSystemScreen
import io.github.shadowrz.projectkafka.features.welcome.api.WelcomeScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

internal object SeralizationModule {
    val CONFIG = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(AboutScreen::class, AboutScreen.serializer())
                subclass(CreateSystemScreen::class, CreateSystemScreen.serializer())
                subclass(DataManageScreen::class, DataManageScreen.serializer())
                subclass(AddMemberScreen::class, AddMemberScreen.serializer())
                subclass(EditMemberScreen::class, EditMemberScreen.serializer())
                subclass(HomeScreen::class, HomeScreen.serializer())
                subclass(LicensesScreen::class, LicensesScreen.serializer())
                subclass(MessagesScreen::class, MessagesScreen.serializer())
                subclass(MemberProfileScreen::class, MemberProfileScreen.serializer())
                subclass(PreferencesScreen::class, PreferencesScreen.serializer())
                subclass(QuickStartScreen::class, QuickStartScreen.serializer())
                subclass(SwitchSystemScreen::class, SwitchSystemScreen.serializer())
                subclass(WelcomeScreen::class, WelcomeScreen.serializer())
            }
        }
    }
}
