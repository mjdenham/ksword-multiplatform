# KSword — Improvements & Maintenance Notes

Durable notes on the known rough edges in `ksword` and why they matter. This is the
*narrative* — the actionable checklist lives in [TASKS.md](./TASKS.md).

`ksword` is a Kotlin Multiplatform port of the Java **JSword** library for reading CrossWire
Sword modules. It works and is actively maintained, but it still reads in places like a
line-by-line Java translation that hasn't been fully Kotlin-ified or hardened as a reusable
library. The themes below are where that shows.

---

## 1. Packaging & consumability

The library currently can't be consumed as a published artifact — it's only usable by
cloning it (and its `ktar` sibling) side-by-side and depending on it as a project build.

- No `maven-publish` configuration, and no `version` is declared (only `group`).
- It depends on an **unpublished** sibling via a hardcoded composite build
  (`includeBuild("../ktar-multiplatform")` + `implementation("org.martin:ktar")`). A consumer
  pulling ksword from a Maven repo could not resolve `ktar`.
- The README is still the generic KMP-wizard template: it references `koin`, `/composeApp`,
  and `/iosApp`, none of which exist, and never explains how to depend on or use the library.

**Why it matters:** this is the single biggest barrier to the library being reusable by
anything other than the one app that's checked out next to it.

## 2. Correctness — concurrency

Two pieces of global mutable state lost the synchronization the Java original had.

- **`OpenFileStateManager`** caches open-file state and evicts it from a background coroutine
  with no locking. A `release()` job can close a `FileHandle` while another coroutine is
  mid-read on the same shared state — a real crash/corruption risk, not cosmetic.
- **`Books`** mutates its book collections while readers iterate them; the original guarded
  these with `@Synchronized` (now commented out). Concurrent read+mutate risks
  `ConcurrentModificationException` / torn reads.

Related: the backend read methods are blocking (synchronous okio) but non-`suspend`, so
nothing stops a consumer calling them on a UI thread.

## 3. Correctness — error handling

- **Swallowed exceptions:** several read paths catch `Exception` and do nothing (some with the
  log line itself commented out), silently producing partial or empty content.
- **Generic throws:** bare `throw Exception(...)` / `RuntimeException` in many places where the
  domain types `BookException` / `NoSuchKeyException` already exist. `BookException` also has no
  cause-chaining constructor, so wrapping an IO error loses the original cause.
- **Logging is a `println` shim** with no levels or filtering and no way to attach an exception
  to a debug line — so swallowed errors often leave no trace at all.

## 4. Public API design

The library runs with default (public) visibility and no explicit-API mode, so nearly the
entire internal machine is public API.

- Implementation types leak: backends, the `state/` machinery, `common/util/*`, and ~90
  localization classes are all public; `SwordBook` exposes its `backend` directly.
- Leftover Java idioms: `getX()` methods that should be Kotlin properties, a mutable `var`
  `bookMetaData` on the `Book` interface, and `suspend` on functions that do no IO
  (`Books.getBooks()`) alongside blocking functions that arguably should be `suspend`.
- Book-level facts a consumer needs (category, language, locked/unlock) are only reachable via
  `book.bookMetaData.*`; the convenient accessors are commented out on `Book`.

**Why it matters:** every public type is a compatibility commitment. Locking the surface down
*before* publishing avoids painful breaking changes later.

## 5. Maintainability

- ~30 `TODO`/`FIXME` markers, plus a few `TODO("Not yet implemented")` stubs that throw at
  runtime if reached.
- Large blocks of dead, commented-out Java (e.g. ~150 lines of dead interface in `Book.kt`,
  ~447 comment lines in `AbstractPassage.kt`), and ~40 files still carrying JSword javadoc
  (`@author`, `gnu.lgpl.License`, references to non-existent types).
- A scattering of `!!` not-null assertions worth auditing.

## 6. Build, test & tooling

- Unit tests (`commonTest`) and **live-network integration tests** (`androidHostTest`) are
  mixed together with no separation and **zero `@Ignore`**, so the integration tests hit real
  servers by default — flaky, slow, and failing offline.
- `junit` is declared in `commonTest` where it isn't usable (all common tests use
  `kotlin.test`); the real JUnit4 usage is in `androidHostTest`.
- No CI, no linting (ktlint/detekt), no coverage, no binary-compatibility validation, no Dokka.
- Target gaps: `iosX64` (Intel simulator) is missing, and there's no desktop/macOS target
  despite the README's framing.

---

## Guiding order

1. Make it **safe** (concurrency fixes) — these are latent crashes.
2. Make it **consumable** (publishing + dependency story).
3. Make it **stable to depend on** (lock the API surface before external release).
4. Then maintainability and tooling.

See [TASKS.md](./TASKS.md) for the tracked checklist.
