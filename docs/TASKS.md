# KSword — Tasks

Open work on `ksword`, with the reason for each item. Finished work is in git history and
[CHANGELOG.md](../CHANGELOG.md), so remove an item here once it lands.

## API

- [ ] **Turn on `explicitApi()`.** Strict mode needs about 644 mechanical edits (627 missing
      `public` keywords and 17 missing return types). Warning mode was tried and reverted: it
      only adds warnings and enforces nothing. Do the full strict sweep or leave it off. Marking
      the implementation types `internal` already removed most of the public surface.
- [ ] **Make the threading contract consistent.** The backend read methods block (synchronous
      okio) but are not `suspend`, so nothing stops a caller using them on the UI thread.
      `Books.getBooks()` must stay `suspend`, because `Installer : BookList` shares it and the
      installers do network calls.

## Tests and tooling

- [ ] **Keep the network tests out of the default test run.** They all live in
      `androidHostTest`, mixed with tests that need no network, so the default run hits live
      servers: slow, flaky and failing offline. Give them their own source set or Gradle task.
      `commonTest` needs no network.
- [ ] **Add local test files** so unit tests don't depend on live servers.
- [ ] **Add CI** (build and test on each target), ktlint/detekt and Dokka.

## Clean-up

- [ ] **Delete dead commented-out Java and stale JSword javadoc.** For example, the old interface
      at the end of `Book.kt` and the comment blocks in `AbstractPassage.kt`. 54 files still
      have `@author` and javadoc that names types that don't exist.
- [ ] **Go through the 13 `TODO`/`FIXME` markers.**
- [ ] **Check the 30 `!!` uses.**

## Platforms

- [ ] Add `iosX64` (Intel simulator) and a desktop/macOS target, if the app needs them.

## Notes

### A partial port hides which code is really unused

Only one of JSword's five `Passage` implementations was ported. JSword defaults to
`PassageType.SPEED` (`RocketPassage`, bitwise-backed), but And Bible overrides it with
`PassageKeyFactory.setDefaultType(PassageType.MIX)` — `RangedPassage` — commented "Optimize for
less memory", so that is the only one ksword needs. `RocketPassage`, `BitwisePassage`,
`DistinctPassage` and `PassageTally` are absent, and so are `SynchronizedPassage`,
`ReadOnlyPassage`, the `KeyList` family and the passage event classes.

The trap: `AbstractPassage.VerseRangeIterator` then has no caller inside ksword, so it reads as
dead code — but it is JSword's verse-to-range amalgamator, needed by the implementations that
store verses, and by `VerseRange.rangeIterator`. It was deleted as unused in September 2026 and
restored the same day, because `RangedPassage` had a re-implementation of it that dropped the
first verse of every chapter after the first in `rangeIterator(CHAPTER)` — the path
`AbstractBackend` reads passages through. In JSword the two classes share a name but not a
signature (`(Versification, Iterator<Key>, restrict)` amalgamates verses; `(Iterator<VerseRange>,
restrict)` splits stored ranges), and that difference is what stops them being confused; the port
had lost it.

Before deleting anything here as unused, check the Java original: it may be load-bearing for a
part of JSword that simply hasn't been ported yet.
