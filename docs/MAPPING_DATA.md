# Cross-versification mapping data

The `org.crosswire.ksword.versification.mapping.data` package embeds CrossWire's versification
mapping tables verbatim, one Kotlin `object` per distinct upstream file. The mapper
(`VersificationToKJVAMapper` / `VersificationsMapper`, exposed via `VersificationConverter`)
is a port of JSword's `VersificationToKJVMapper` / `VersificationsMapper`.

## Provenance

- Upstream: `jsword` repo, `src/main/resources/org/crosswire/jsword/versification/*.properties`
- Commit the embedded data and the port were verified against: `901dbac716a0b5bd897dc7377b26ac2d412a7341`
- Verification: a differential sweep of **every ordinal of every versification, both directions
  through the KJVA pivot (1,233,538 conversions)** matched Java JSword exactly, apart from the
  intentional divergences listed below. The throwaway harness (a `GoldenGen.java` run against the
  jsword checkout plus a temporary `jvmTest` comparator) was deleted after it went green; re-create
  it from this description if a future re-sync needs re-proving.

## Data layout

Each `data/XxxMapping.kt` holds the upstream file as a raw string, byte-for-byte:

- the content starts on the line after `const val TEXT: String = """` and ends on the line before
  the closing `"""`, so `TEXT` is `"\n" + <file bytes>` (the parser skips the empty first line);
- the files are pure ASCII with no `$`, `\` or `"""`, so no escaping is involved — **do not
  reformat them**;
- `German` ≡ `Luther` and `NRSV` ≡ `NRSVA` are byte-identical upstream, so only `LutherMapping`
  and `NrsvaMapping` exist and `MappingData.sourceFor` aliases the other name to them.

Versifications with no upstream mapping file map identically to the KJVA:
KJV, KJVA, Calvin, DarbyFr, LXX, Orthodox.

## Re-sync procedure

1. For each `data/XxxMapping.kt`, extract the block between the `= """` line and the closing `"""`
   line and `diff` it against the upstream `.properties` file:

   ```sh
   awk '/= """$/{flag=1;next}/^"""$/{flag=0}flag' XxxMapping.kt | diff - $JSWORD/.../Xxx.properties
   ```

2. Paste changed files in verbatim; update the commit SHA above.
3. Run the mapping tests. `MappingDataDigestTest` pins each file's length/hash and the loaded table
   sizes — regenerate its values for deliberately changed files.
4. `MappingDataIntegrityTest` guards the syntax subset (below) and the known-bad-line inventory;
   if upstream fixed a known-bad line, remove it from `MappingData` and the guards will say so.

## Deliberate deviations from JSword

1. **Parts on range endpoints are dropped** (e.g. Synodal `1Kgs.18.34=1Kgs.18.33!b-1Kgs.18.34`).
   JSword keeps the part and then throws `ClassCastException` when such a range reaches
   `unmap`'s part-stripping fallback.
2. **`+N`/`-N` offset syntax is rejected** (`MappingSyntaxException`). It appears in zero shipped
   files; JSword carries ~45 lines of dead code for it. `MappingDataIntegrityTest.noOffsetSyntax`
   fails loudly if upstream ever starts using it.
3. **Bad entries are recorded, not thrown.** JSword's cardinality errors throw
   `LucidRuntimeException` out of the constructor, aborting the whole mapper; here each bad entry
   lands in `VersificationToKJVAMapper.errors` and the rest of the file still loads.
4. **Known upstream data fixes** (`MappingData.KNOWN_UPSTREAM_FIXES`, applied after parsing):
   - Catholic + Catholic2: `Hos.12.2-Hos.12.15=Hos.1.1-Hos.1.14` → right side corrected to
     `Hos.12.1-Hos.12.14`. KJVA Hosea 1 has 11 verses; the file entry sits between the
     `Hos.12.1=Hos.11.12` and `Hos.14.1=Hos.13.16` entries and is an obvious typo. JSword's
     lack of validation makes it silently map Catholic Hosea 12 onto KJV Hosea 1–2.
5. **Known bad lines that are skipped** (`MappingData.KNOWN_BAD_LINES`):
   - Segond: `Mark.10.53=Mark.10.52!b` — Segond's own canon table (upstream and here) gives
     Mark 10 exactly 52 verses, so the left side does not exist and the intended fix is unclear.
     JSword silently treats it as `Mark.11.0`.

## Verse 0 policy

Verse 0 is an ordinary, fully mappable verse.

- *Structural role*: every chapter of every versification has a verse-0 slot (chapter
  introduction; chapter 0 = book introduction). The mapping files never mention introductions,
  so they convert by implicit identity and never fail.
- *Semantic role*: the KJV numbers the Hebrew Psalm title as verse 0 where Synodal/Catholic/Vulgate
  number it verse 1. Verse-0-ness is therefore not preserved where the data says otherwise:
  KJVA `Ps.50.0` → Synodal `Ps.49.1`, and Synodal `Ps.49.1` → KJVA `Ps.50.0-Ps.50.1`.
- The upstream `!zerosUnmapped` flag lines are parsed and skipped, which is also what current
  JSword does (And Bible's historical build-time patch for this is obsolete).
- A caution for tests: chapter intros the data leaves unmapped are *asymmetric* — Synodal
  `Ps.21.0` → KJVA `Ps.21.0` (implicit identity) but KJVA `Ps.21.0` → Synodal `Ps.20.1`
  (explicit). JSword behaves identically; round-trip invariants only hold for real verses.

## Bugs found and fixed during the port

- **Five versification tables were corrupt in ksword** (values drifted from JSword's during the
  original transcription): Leningrad and MT (27 books each — Hebrew numbering had reverted
  to KJV values), Segond (12 books), DarbyFr (6 books), Synodal (one dropped Psalms entry that
  shifted chapters 119–151 by one). All 18 tables were regenerated from JSword's runtime output
  and are now identical to it. Note this changes ordinals for those five versifications.
- **`Versification.validate` accepted books absent from the versification** (the port had dropped
  JSword's `bookList.contains(book)` check), which made book introductions of absent books
  "valid" and silently decode to ordinal 0.
- **`Versifications.getVersification`/`register` were not thread-safe**; two instances of the same
  versification would break `Verse.equals`, which compares versifications by identity.
