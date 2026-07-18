# KSword

A Kotlin Multiplatform library for reading [CrossWire Sword](https://www.crosswire.org/sword/index.jsp)
Bible modules — downloading and unpacking modules, and reading their text (including OSIS → text
conversion). It is a port of [JSword](https://github.com/crosswire/jsword) to Kotlin Multiplatform.

Published on Maven Central as `io.github.mjdenham:ksword`.

It powers `kmp-compose-bible-app`, a Compose Multiplatform Bible app for Android and iOS
(currently a private repository).

## Why a port?

JSword can't run on Kotlin Multiplatform because it relies on JVM-only facilities — `InputStream`,
`ZipInputStream`, `File`, the SAX parser, an Apache HTTP client, and the classloader for
translations. KSword replaces these with multiplatform equivalents:

- [Okio](https://github.com/square/okio) for file and stream I/O
- [Ktor](https://ktor.io/) for module downloads
- [ktar](https://github.com/mjdenham/ktar-multiplatform) for tar / tar.gz extraction

## Supported targets

`jvm`, `android`, `iosArm64`, `iosSimulatorArm64`.

## Installation

```kotlin
repositories {
    mavenCentral()
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.mjdenham:ksword:0.1.0")
        }
    }
}
```

`ktar` is pulled in transitively — no extra setup required.

## Usage

### 1. Set the module storage location

KSword reads and installs modules under a single directory. Set it **before** touching `Books` or
any installer (the path is an Okio `Path`):

```kotlin
import okio.Path.Companion.toPath
import org.crosswire.ksword.book.sword.SwordBookPath

// e.g. context.filesDir.path on Android, or NSDocumentDirectory on iOS
SwordBookPath.swordBookPath = "/path/to/sword".toPath()
```

### 2. Install a module

Installing downloads and unpacks the module, then registers it with `Books`. Installer calls are
`suspend` functions (they perform network + disk I/O):

```kotlin
import org.crosswire.ksword.book.install.sword.SwordInstallerFactory

val factory = SwordInstallerFactory()

// Install from a specific repository...
factory.crosswireInstaller.install("KJV")

// ...or let KSword find whichever repository hosts the module:
factory.findInstaller("KJV").install("KJV")
```

Available repositories on `SwordInstallerFactory`: `crosswireInstaller`, `ebibleInstaller`,
`lockmanInstaller`, `andBibleInstaller`, `ibtInstaller`, `tapBibleInstaller`. List what a
repository offers with `installer.getBooks()` (also `suspend`).

### 3. Read text

```kotlin
import org.crosswire.ksword.book.Books
import org.crosswire.ksword.passage.Verse
import org.crosswire.ksword.passage.VerseRange
import org.crosswire.ksword.versification.BibleBook

val book = Books.getBook("KJV") ?: error("KJV is not installed")
val v11n = book.bookMetaData.versification

// A single verse — returns List<KeyText>, each with a .text payload
val john3v16 = book.readToOsis(Verse(v11n, BibleBook.JOHN, 3, 16))
println(john3v16.first().text)

// A range (e.g. a chapter)
val genesis1 = VerseRange(
    v11n,
    Verse(v11n, BibleBook.GEN, 1, 1),
    Verse(v11n, BibleBook.GEN, 1, 31),
)
book.readToOsis(genesis1).forEach { println(it.text) }

// Raw (unparsed) module text:
val raw = book.getRawText(Verse(v11n, BibleBook.JOHN, 3, 16))
```

Already-installed modules are discovered by `Books` on startup; call `Books.refresh()` after
changing the module directory outside of an installer.

> **Threading:** installer functions are `suspend` and switch to a background dispatcher
> internally. The read functions (`readToOsis`, `getRawText`) are **blocking** — call them off the
> main/UI thread.

## Building

Standard Kotlin Multiplatform / Gradle project with a single `:ksword` module:

```bash
./gradlew :ksword:build          # compile + test all targets
./gradlew :ksword:jvmTest        # fast JVM unit tests
./gradlew :ksword:publishToMavenLocal
```

`settings.gradle.kts` includes the sibling `ktar-multiplatform` build via `includeBuild`, so a
local `ktar` checkout beside this repo is substituted automatically during development while
published builds resolve `io.github.mjdenham:ktar` from Maven Central.

See [docs/IMPROVEMENTS.md](docs/IMPROVEMENTS.md) and [docs/TASKS.md](docs/TASKS.md) for the current
maintenance backlog.

## License

GNU Lesser General Public License, version 2.1 — see [LICENSE](LICENSE). KSword is derived from
JSword, © CrossWire Bible Society.
