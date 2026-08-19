# Changelog

All notable changes to KSword are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the project uses
[Semantic Versioning](https://semver.org/) — while at `0.x`, breaking changes ship in minor releases.

## [Unreleased]

### Added

- **Cross-versification mapping.** New `VersificationConverter` converts verses and ranges between
  versifications using CrossWire's mapping tables, pivoting through the KJVA — e.g. KJV `Ps.51.1`
  converts to Synodal `Ps.50.3`. The API is strict: `convertOrNull` returns null when a verse
  genuinely has no counterpart (an NT verse in a Hebrew-only versification), unlike
  `Verse.reversify`, which only reinterprets the same numbers and is now documented as such.
  `convertAll` exposes split verses (a verse whose counterpart is several verses, e.g.
  Psalm-title merges) so callers can choose a landing policy.
  Range conversion maps endpoints only. `preload(v11n)` parses a versification's table off the
  caller's thread; first unpreloaded use parses inline (a few ms). Ported from JSword's
  `VersificationsMapper`/`VersificationToKJVMapper` and verified against it with a differential
  sweep of every ordinal of every versification in both directions (1,233,538 conversions);
  the data files are embedded verbatim — see `docs/MAPPING_DATA.md` for provenance, the
  re-sync procedure, and the deliberate deviations (all of them fixes to upstream defects).

### Fixed

- **Five versification tables had drifted from JSword's during the original transcription**:
  Leningrad and MT (27 books each — Hebrew verse numbering had reverted to KJV values), Segond
  (12 books), DarbyFr (6 books), and Synodal (one dropped Psalms entry shifted chapters 119–151
  by one, so e.g. Russian Psalm 151 had 0 verses). All 18 tables are now regenerated from and
  identical to JSword's. **Ordinals change for those five versifications** — anything persisting
  raw ordinals for them was storing misaligned values before this fix.
- `Versification.validate` now rejects books absent from the versification (JSword's
  `bookList.contains` check had been dropped in the port); previously the book introduction of an
  absent book counted as valid and silently decoded to ordinal 0.
- `Versifications.getVersification` and `register` are now thread-safe. Versifications are
  compared by identity (e.g. in `Verse.equals`), so the previous unsynchronized lazy cache could
  hand out two instances of the same versification under concurrent first use.

## [0.2.1] — 2026-08-14

### Fixed

- **Linked commentary verses are merged correctly.** zCom modules store a multi-verse comment once
  and point every covered verse at the same block, so linked verses share an identical
  `(blockNum, verseStart, verseSize)` tuple in the `.bzv` index. That tuple is now read directly
  rather than inferred by comparing decoded text: consecutive verses sharing one are merged into a
  single `KeyText(VerseRange, text)`, and empty verses never merge, so per-verse entries for gaps
  are preserved. `findNextKey`/`findPreviousKey` walk the index with no 40-verse cap and land on
  the first verse of a linked run. Text comparison remains as a fallback for backends with no
  verse index. Verified against KingComments Genesis 1, where the merged keys match the module's
  own `annotateRef` declarations exactly.

### Changed

- Updated ktar to 0.2.0, which fixes a hang when reading a truncated archive and rejects entry
  names that would escape the destination folder when extracting. ktar is an `implementation`
  dependency, so this is not visible on your compile classpath.

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

[0.2.1]: https://github.com/mjdenham/ksword-multiplatform/releases/tag/v0.2.1
[0.2.0]: https://github.com/mjdenham/ksword-multiplatform/releases/tag/v0.2.0
[0.1.0]: https://github.com/mjdenham/ksword-multiplatform/releases/tag/v0.1.0
