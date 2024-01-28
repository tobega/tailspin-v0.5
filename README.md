# tailspin-v0.5
Reworking syntax and reimplementing [Tailspin from v0](https://github.com/tobega/tailspin-v0/tree/master) to be better [based on concepts](https://tobega.blogspot.com/2024/01/usability-in-programming-language.html)

## Breaking changes
- Projections will get a clear and general transform step at the end, marked by `->`, e.g. `$(5; -> 0)` will select the 5th element of the array and the transform it to `0` (OK, this example ends up being useless, but it is simple and shows the syntax). The advantage of the "inside" transform is that overlying structure can be kept.
- Since there will be an arrow (`->`) in the projection transform step, it makes sense to just use regular `$` instead of `§`
- `*` will be introduced as a short form of `first..last` (and will also work as an *all* selector for non-indexed collections like relations)
- As a consequence, the structure transform projection, e.g. `$({x:, y: §.y + 1"1"})` will become `$(*; -> {x:, y: $.y + 1"1"})`
- Array templates will be folded in as projections, so `$ -> \[i]($ * $i! \)` will be written instead `$(i: *; -> $ * $i)` (I have to think how `i:` conflicts with field access, though, so it may change a bit, maybe `*:i`)
- The magic property of the structure transform projection to apply no matter how many array dimensions down, will disappear. Maybe it will be enabled by an explicit `**`
- Probably an underscore `_` will be introduced as a way to reference a transform without applying it, so passing templates as parameters would be prefixed by `_`
- Speculatively, I might remove the `.` syntax to access fields, so it has to be `$record(field:)` instead of `$record.field`

