# tailspin-v0.5
Reworking syntax and reimplementing [Tailspin from v0](https://github.com/tobega/tailspin-v0/tree/master) to be better [based on concepts](CONCEPTS.md).
Also aiming for better performance and incremental parsing for creating a REPL and a language server.

Great thanks to Adam Ruka for his [Truffle tutorial](https://www.endoflineblog.com/graal-truffle-tutorial-part-0-what-is-truffle)

## Breaking changes
- Projections will get a clear and general transform step at the end, marked by `->`, e.g. `$(5; -> 0)` will select the 5th element of the array and then transform it to `0` (OK, this example ends up being useless, but it is simple and shows the syntax). The advantage of the "inside" transform is that overlying structure can be kept.
- Since there will be an arrow (`->`) in the projection transform step, it makes sense to just use regular `$` instead of `§`
- `*` will be introduced as a short form of `first..last` (and will also work as an *all* selector for non-indexed collections like relations)
- As a consequence, the structure transform projection, e.g. `$({x:, y: §.y + 1"1"})` will become `$(*; -> {x:, y: $.y + 1"1"})`
- Array templates will be folded in as projections, so `$ -> \[i]($ * $i! \)` will be written instead `$(* as i; -> $ * $i)`
- The magic property of the structure transform projection to apply no matter how many array dimensions down, will disappear. Maybe it will be enabled by an explicit `**`
- Probably an underscore `_` will be introduced as a way to reference a transform without applying it, so passing templates as parameters would be prefixed by `_`
- Speculatively, I might remove the `.` syntax to access fields, so it has to be `$record(field:)` instead of `$record.field`
- The `#` for sending to matchers is just a transform call and must be followed by an emit `!` to emit or could be followed by more chained transforms.
- Inline templates will no longer be written as `\(......\)`, instead just `templates ........ end`. This unifies syntax and opens up for inline composer, etc.

## New features
- Syntax sugar for a filter expression, `\(<.....> $! \)` can be written as `when <.....>`
- Assert a single value from a chain by surrounding with parentheses, `($source -> filter)`

## Performance check
See the [performance tests](src/jmh/README.md) for how Tailspin performs relative to java
