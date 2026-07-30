# Changelog

All notable changes to KSword are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the project uses
[Semantic Versioning](https://semver.org/) — while at `0.x`, breaking changes ship in minor releases.

## [0.2.0] — 2026-07-30

### Breaking

- **`install(zipFile)` is now `suspend`** (runs on the IO dispatcher), matching the other `install`
  overloads. Call it from a coroutine.
- **The implementation layer is now `internal`.** Affected: the whole backend layer (`Backend`,
  `AbstractBackend`, `*Backend`, `DataEntry`/`DataIndex`, `BlockType`/`BookType`, `SwordUtil`,
  `SwordConstants`), the `state` package, `SwordBook`, the `System*` versification tables and
  `SwordDefault`, the per-language localization objects, `Books.addBook`,
  `SwordBookMetaData.bookType`, `SwordDictionary`'s constructor and `backend`, and the unused
  `common/util` helpers (`FileUtil`, `IoUtil`, `IniSection`, `ItemIterator`, `WebResource`,
  `MissingResourceException`) plus `JSMsg`.

  Still public: `SwordBookPath`, `SwordDictionary`, `SwordBookMetaData` (and `.driver` →
  `SwordBookDriver`), `OsisXmlConstants`, `Log`, `Version`, `Locale`, `SwordInstallerFactory` and
  its installers, `Books.refresh`/`getBook`/`getBooks`, `LocalizedBookNames`. If your usage is
  confined to that list, no change is needed.
- **Typed exceptions replace bare `Exception`.** Failures across `book`, `backend`, `install` and
  `state` now throw `BookException`; enum-name parse failures (`KeyType`, `BlockType`) throw
  `IllegalArgumentException`. `getRawText` wraps underlying failures in `BookException`. Update any
  `catch (e: Exception)` that relied on the old type. `BookException` gains a `(message, cause)`
  constructor for cause chaining.

### Fixed

- **Data race in `OpenFileStateManager`** — states are now reference-counted and lock-guarded, so a
  `FileHandle` is never closed while another reader still holds the state.
- **Unsynchronized access in `Books`** — the book collections and `driver` are guarded by a lock,
  restoring the behaviour lost when `@Synchronized` was removed during the KMP port.
- **Silently swallowed read errors** — the empty catches in the `AbstractBackend` read loops,
  `RawLDBackend.getAllKeys` and `SwordUtil.decode` now log instead of discarding the failure.

### Changed

- **`Verse.DEFAULT` is now John 1:1** (was Genesis 1:1). This changes behaviour at runtime rather
  than at compile time — check anywhere you rely on the default verse.
- `Log` is a real logging facade with levels and an exception argument, replacing the `println` shim.
- Dependencies: Kotlin 2.4.10, AGP 9.3.1, ktor 3.5.1, okio 3.18.1, Gradle 9.6.1, ktar 0.1.1.
- `commonTest` now also runs on `iosSimulatorArm64`.

## [0.1.0] — 2026-07-18

Initial release.

[0.2.0]: https://github.com/mjdenham/ksword-multiplatform/releases/tag/v0.2.0
[0.1.0]: https://github.com/mjdenham/ksword-multiplatform/releases/tag/v0.1.0
