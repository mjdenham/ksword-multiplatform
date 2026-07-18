# KSword — Improvement Tasks

Actionable checklist derived from [IMPROVEMENTS.md](./IMPROVEMENTS.md). Grouped by priority.
Check items off as they land; prefer closing via a commit that references the item.

## P0 — Safety & consumability

- [ ] Fix the `OpenFileStateManager` data race — guard the cache + release job with a `Mutex`
      (or reference-count states) so a `FileHandle` can't be closed mid-read.
- [ ] Synchronize `Books` — protect the book collections + `driver` against concurrent
      read/mutate (restores the behaviour of the removed `@Synchronized`).
- [x] Sort out the `ktar` dependency — published `io.github.mjdenham:ktar:0.1.0` to Maven Central;
      ksword now depends on the published coordinate (the local `includeBuild` still substitutes
      it during development).
- [x] Add `maven-publish` + a `version` — done via the `com.vanniktech.maven.publish` plugin;
      `ksword` and `ktar` published to Maven Central as `io.github.mjdenham:*:0.1.0`.
      (Documenting coordinates + usage in the README is still pending — see the README item in P3.)

## P1 — API hardening (breaking; do before any external release)

- [ ] Enable `explicitApi()` and mark implementation types `internal` (backends, `state/`,
      `common/util/*`, localization classes, `SwordBook.backend`, `Books.addBook`/`refresh`).
- [ ] Replace bare `throw Exception(...)` with `BookException` / `NoSuchKeyException`; add a
      cause-chaining constructor to `BookException`.
- [ ] Stop swallowing exceptions in the backend read paths — handle or propagate, and log.
- [ ] Make the threading contract consistent — drop `suspend` where there's no IO
      (`Books.getBooks()`), make `install(zipFile)` match its `suspend` siblings.

## P2 — Maintainability & tooling

- [ ] Separate integration tests from unit tests (own source set / Gradle task, or `@Ignore`
      + manual run) so the default test run doesn't hit the network.
- [ ] Move `junit` out of `commonTest` into `androidHostTest`.
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
