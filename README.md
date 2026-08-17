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
./gradlew :android-app:assembleDebug # Debug (signed with included debug keys)
./gradlew :android-app:assembleRelease # Release (unsigned)
```

Desktop Build **(EXPERIMENTAL)**:
```sh
./gradlew :desktop-app:packageReleaseAppImage
```

**License**: GPL-3.0-or-later

## Etymology

Name taken from [Kafka from _Honkai: Star Rail_](https://honkai-star-rail.fandom.com/wiki/Kafka),
who bears the [Spirit Whisper](https://honkai-star-rail.fandom.com/wiki/Stellaron_Hunter:_Kafka#Skills) ability.

The icon consists of a camera and an umbrella, representing [March 7th](https://honkai-star-rail.fandom.com/wiki/March_7th)
and [Evernight](https://honkai-star-rail.fandom.com/wiki/Evernight) respectively.
