![[Project Kafka Logo]](assets/projectkafka-logo.svg)

# Project Kafka

_Plurality With You_

Current features:

* [x] Basic systems management
* [x] Members management
  * [x] Name
  * [x] Avatar
  * [ ] Cover
  * [ ] Fields
  * [ ] Admin Role
* [ ] Timeline
* [ ] Chats
* [ ] Polls
* [ ] Settings
* [x] Data management

> [!NOTE]
> Currently the Nix environment doesn't contain Android SDK to minimize devshell closure size.

## Building

Building Project Kafka requires a JDK with minimum version of 21.

> [!NOTE]
> You can also substitute `./gradlew` with another wrapper that eventually calls Gradle.

Building Android APK:

```sh
./gradlew :app:assembleDebug # Debug (signed with included debug keys)
./gradlew :app:assembleRelease # Release (unsigned)
```

Desktop Build **(EXPERIMENTAL)**:
```sh
./gradlew :desktop:packageReleaseAppImage
```

**License**: GPL-3.0-or-later
