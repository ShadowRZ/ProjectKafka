# Contribution Guide

This documents outlines various important infomations when you're going to contribute to this project.

## General notes about terminology

As this project is primarily geared towards plurality, a general knowledge of plurality terms can help here.

When in doubt, ask @ShadowRZ for a review.

## Coding notes

> [!WARNING]
> The [Nix](https://nix.dev) Flakes environment only contains some bare essentional tools, most importantly **it doesn't include Android SDK**.

Basic familiarity with Kotlin, SQLDelight, Decompose and Metro are recommended.

### Kotlin

This projects is written fully in Kotlin, please don't write Java classes.

### Dependency updates

Dependency updates are primarily managed by [Renovate](https://docs.renovatebot.com), so generally there's no need to submit dependency update PRs unless replacing dependencies are strongly recommended (for example to move off deprecated APIs).

### Code Quality

Run detekt and ktlint checks:

```shell
$ ./gradlew ktlintCheck detekt
```

Format some ktlint issues:

```shell
$ ./gradlew ktlintFormat
```

### Android Lint

```shell
$ ./gradlew lintDebug
```

It's recommended to check if they're all passed before submitting PR.

### Tests

Tests are exclusively written using [Kotest](https://kotest.io), in most cases [`StringSpec`](https://kotest.io/docs/next/framework/testing-styles.html#string-spec), but feel free to use other testing styles.

**TODO**: Tests and coverage guideline, currently coverage is not measured, and only some presenters are tested.
