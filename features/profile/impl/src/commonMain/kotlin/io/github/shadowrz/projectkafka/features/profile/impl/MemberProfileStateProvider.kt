package io.github.shadowrz.projectkafka.features.profile.impl

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import kotlinx.datetime.LocalDate

class MemberProfileStateProvider : PreviewParameterProvider<MemberProfileState> {
    override val values: Sequence<MemberProfileState>
        get() =
            sequenceOf(
                aMemberProfileState(name = "N", description = "Hello"),
                aMemberProfileState(
                    name = "N",
                    description = "Long Text Long Text Long Text Long Text Long Text Long Text Long Text Long Text Long Text Long Text",
                ),
            )
}

private fun aMemberProfileState(name: String, description: String): MemberProfileState =
    MemberProfileState(
        member =
            Result.Success(
                Member(
                    id = MemberID("1"),
                    name = name,
                    description = description,
                    avatar = null,
                    cover = null,
                    preferences = "",
                    roles = "",
                    birth = LocalDate(2024, 1, 1),
                    admin = false,
                )
            )
    )
