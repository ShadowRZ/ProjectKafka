package io.github.shadowrz.projectkafka.features.home.impl.overview.members

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.github.shadowrz.projectkafka.libraries.core.Result
import io.github.shadowrz.projectkafka.libraries.data.api.Member
import io.github.shadowrz.projectkafka.libraries.data.api.MemberID
import kotlinx.datetime.LocalDate

class MembersStateProvider : PreviewParameterProvider<MembersState> {
    override val values: Sequence<MembersState>
        get() =
            sequenceOf(
                aMembersState(),
                aMembersState(members = emptyList()),
                aMembersState(members = aMembers()),
            )
}

internal fun aMembersState() = MembersState(members = Result.Loading)

internal fun aMembersState(members: List<Member>) = MembersState(members = Result.Success(members))

internal fun aMembers(): List<Member> =
    listOf(
        Member(
            id = MemberID("1"),
            name = "N",
            description = "Hello world",
            avatar = null,
            cover = null,
            preferences = "",
            roles = "",
            birth = LocalDate(2024, 1, 1),
            admin = false,
        ),
        Member(
            id = MemberID("2"),
            name = "N",
            description = "Long text ".repeat(25),
            avatar = null,
            cover = null,
            preferences = "",
            roles = "",
            birth = LocalDate(2024, 1, 1),
            admin = false,
        ),
        Member(
            id = MemberID("3"),
            name = "N",
            description = null,
            avatar = null,
            cover = null,
            preferences = "",
            roles = "",
            birth = LocalDate(2024, 1, 1),
            admin = false,
        ),
    )
