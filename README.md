# Overview

A Kotlin Multiplatform Sword library to allow access to [Crosswire Sword](https://www.crosswire.org/sword/index.jsp) Bible modules in Kotlin Multiplatform projects.
This KSword code is derived from [JSword](https://github.com/crosswire/jsword) and is used by kmp-compose-bible-app which is currently a private repository.

# Sword Bible Modules

This multiplatform library provides access to [Sword](https://www.crosswire.org/sword/index.jsp) Bible modules.  It downloads the module, expands it, allows for conversion from OSIS to HTML and provides various document utility classes.  

# Kotlin Multiplatforn (KMP)
KMP is focused on the use of Kotlin for common code and does not natively support Java or C++ libraries nor the various SDK libraries such as IOSteam and SAX
so JSword can not be used in a KMP app.

[JSword](https://www.crosswire.org/jsword/)'s use of InputStream, ZipInputStream, File, SAX parser, Apache Http client for downloads and the Classloader for translations is not supported.
In place of the above the KSword library will use [OKIO](https://github.com/square/okio), [KTOR](https://ktor.io/) and XML Pull Parser ([ktxml](https://github.com/kobjects/ktxml)).  
Additionally [koin](https://insert-koin.io/) is used for dependency injection.

In the kmp-compose-bible-app a single cross-platform UI for iOS and Android is enabled by use of Multiplatform Jetpack Compose and compose-webview-multiplatform.

# KMP Directory Structure

This is a [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) project targeting Android and iOS (desktop maybe be added).

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.
