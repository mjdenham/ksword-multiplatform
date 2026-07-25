# KSword — Improvement Tasks

Actionable checklist derived from [IMPROVEMENTS.md](./IMPROVEMENTS.md). Grouped by priority.
Check items off as they land; prefer closing via a commit that references the item.

## P0 — Safety & consumability

- [x] Fix the `OpenFileStateManager` data race — reference-counted + lock-guarded (atomicfu
      `SynchronizedObject`); a `FileHandle` is never closed while a reader still holds the state.
- [x] Synchronize `Books` — book collections + `driver` guarded by an atomicfu lock, restoring
      the behaviour of the removed `@Synchronized`.
- [x] Sort out the `ktar` dependency — published `io.github.mjdenham:ktar:0.1.0` to Maven Central;
      ksword now depends on the published coordinate (the local `includeBuild` still substitutes
      it during development).
- [x] Add `maven-publish` + a `version` — done via the `com.vanniktech.maven.publish` plugin;
      `ksword` and `ktar` published to Maven Central as `io.github.mjdenham:*:0.1.0`.
      (Documenting coordinates + usage in the README is still pending — see the README item in P3.)

## P1 — API hardening (breaking; do before any external release)

- [x] Mark implementation types `internal` — done: the whole backend layer (`Backend`,
      `AbstractBackend`, `*Backend`, `SwordBook`, `DataEntry`/`DataIndex`, `BlockType`/`BookType`,
      `SwordUtil`, `SwordConstants`) + the `state/` package, the 19 `System*` v11n tables +
      `SwordDefault`, the ~70 per-language localization objects, unused `common/util/*`
      (`FileUtil`/`IoUtil`/`IniSection`/`ItemIterator`/`WebResource`/`MissingResourceException`)
      + `JSMsg`, `Books.addBook`, `SwordBookMetaData.bookType`, and `SwordDictionary`'s
      constructor+`backend`. Kept public (consumer-used): `SwordBookPath`, `SwordDictionary`,
      `SwordBookMetaData`(+`.driver`→`SwordBookDriver`), `OsisXmlConstants`, `Log`/`Version`/`Locale`,
      `SwordInstallerFactory`+installers, `Books.refresh`/`getBook`/`getBooks`, `LocalizedBookNames`.
      Validated: both sibling apps compile against the working tree (bible app common+iOS; commentary
      generator jvm).
- [~] `explicitApi()` — enabled in **warning** mode (`explicitApiWarning()`). Strict mode needs
      627 `public` keywords + 17 explicit return types added mechanically; deferred — tighten to
      strict + add the keywords incrementally before 0.2.0.
- [x] Replace bare `throw Exception(...)` with `BookException` / typed exceptions; added a
      cause-chaining constructor to `BookException`. (Enum-parse failures → `IllegalArgumentException`.)
- [x] Stop swallowing exceptions in the backend read paths — the empty catches in
      `AbstractBackend` read loops, `RawLDBackend.getAllKeys`, and `SwordUtil.decode` now log;
      removed the no-op `getCipherKey` catch; `getRawText` now wraps failures in `BookException`.
- [~] Make the threading contract consistent — `install(zipFile)` is now `suspend`
      (`withContext(IO)`), matching its siblings. `Books.getBooks()` can NOT drop `suspend`:
      `Installer : BookList` shares `getBooks()` and its installer impl does real network IO.

## P2 — Maintainability & tooling

- [ ] Separate integration tests from unit tests (own source set / Gradle task, or `@Ignore`
      + manual run) so the default test run doesn't hit the network. (The network/module-dependent
      tests all live in `androidHostTest`; `commonTest` is pure unit tests.)
- [x] Make `commonTest` run on iOS — the only blocker was `LocalizedBookNamesTest` using bare
      `assert()` (`@ExperimentalNativeApi` on Native); switched to `kotlin.test.assertTrue`.
      100 common tests now run on `iosSimulatorArm64`.
- [x] Remove `junit` from `commonTest` — it was unused there (`commonTest` is 100% `kotlin.test`)
      AND redundant everywhere: `kotlin("test")` already pulls JUnit4 transitively via
      `kotlin-test-junit` on JVM/Android, which is how `androidHostTest` (JUnit4) still resolves
      `org.junit.*`. So the line was simply deleted, not relocated. (It was never the iOS blocker.)
      The `junit` entries in `libs.versions.toml` are now orphaned — remove if desired.
- [ ] Add real local test fixtures so unit tests don't depend on live servers.
- [ ] Replace the `println` `Log` shim with a real logging abstraction (levels + exception arg).
- [ ] Add CI (build + test on each target), ktlint/detekt, and Dokka.
- [ ] Delete dead commented-out Java blocks and stale JSword javadoc.
- [ ] Resolve or ticket the `TODO("Not yet implemented")` stubs so they can't throw silently.
- [ ] Audit the `!!` not-null assertions.

## P3 — Reach

- [ ] Add `iosX64` (Intel simulator) and a desktop/macOS target if the app needs them.
- [x] Rewrite the README to describe the actual library (removed koin / composeApp / iosApp
      template text; added Maven Central coordinates + a usage section).
