# tailspin-v0.5
Reworking syntax and reimplementing [Tailspin from v0](https://github.com/tobega/tailspin-v0/tree/master) to be better [based on concepts](https://tobega.blogspot.com/2024/01/usability-in-programming-language.html)

Great thanks to Adam Ruka for his [Truffle tutorial](https://www.endoflineblog.com/graal-truffle-tutorial-part-0-what-is-truffle)

## Breaking changes
- Projections will get a clear and general transform step at the end, marked by `->`, e.g. `$(5; -> 0)` will select the 5th element of the array and the transform it to `0` (OK, this example ends up being useless, but it is simple and shows the syntax). The advantage of the "inside" transform is that overlying structure can be kept.
- Since there will be an arrow (`->`) in the projection transform step, it makes sense to just use regular `$` instead of `§`
- `*` will be introduced as a short form of `first..last` (and will also work as an *all* selector for non-indexed collections like relations)
- As a consequence, the structure transform projection, e.g. `$({x:, y: §.y + 1"1"})` will become `$(*; -> {x:, y: $.y + 1"1"})`
- Array templates will be folded in as projections, so `$ -> \[i]($ * $i! \)` will be written instead `$(i: *; -> $ * $i)` (I have to think how `i:` conflicts with field access, though, so it may change a bit, maybe `*:i` or even `* as i`)
- The magic property of the structure transform projection to apply no matter how many array dimensions down, will disappear. Maybe it will be enabled by an explicit `**`
- Probably an underscore `_` will be introduced as a way to reference a transform without applying it, so passing templates as parameters would be prefixed by `_`
- Speculatively, I might remove the `.` syntax to access fields, so it has to be `$record(field:)` instead of `$record.field`
- The `#` for sending to matchers is just a transform call and must be followed by an emit `!` to emit or could be followed by more chained transforms.

## Performance check
Implemented jmh tests as in Adam Ruka's tutorial. Frustrating debug to find I needed to add a truffle-runtime dependency

|Benchmark                                      |Mode  |Cnt     |Score    |Error  |Units|
|FibonacciBenchmark.recursive_eval_js           |avgt    |5    |62,309 |±  2,355  |us/op|
|FibonacciBenchmark.recursive_eval_sl           |avgt    |5    |63,485 |±  0,783  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin     |avgt    |5  |1168,769 |± 21,090  |us/op|
|FibonacciBenchmark.recursive_eval_v0_tailspin  |avgt    |5  |2212,986 |± 46,117  |us/op|
|FibonacciBenchmark.recursive_java              |avgt    |5    |36,587 |±  0,111  |us/op|

Going to just returning value instead of mucking with iterators:
|Benchmark                                         |Mode  |Cnt  |   Score    |Error  |Units|
|FibonacciBenchmark.recursive_eval_tailspin        |avgt  |  5  |1194,630 |± 46,617  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin_value  |avgt  |  5  |  53,368 |±  0,559  |us/op|
|FibonacciBenchmark.recursive_java                 |avgt  |  5  |  36,893 |±  0,252  |us/op|

Tried to replace iterators with MaterializedFrame, but that was worse (1573,9 us/op)

Creating an array-based ResultIterator instead of using java Collectons really did the trick
|Benchmark                                         |Mode  |Cnt  | Score |  Error  |Units|
|FibonacciBenchmark.recursive_eval_tailspin        |avgt  |  5  |88,607 |± 1,519  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin_value  |avgt  |  5  |53,632 |± 1,517  |us/op|
|FibonacciBenchmark.recursive_java                 |avgt  |  5  |36,708 |± 0,115  |us/op|

Shaving a bit more by allowing single results from a transform
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |53,055 |± 0,592  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,966 |± 1,233  |us/op|

More realistic code with a chain on the recursion. Can we optimize when parsing?
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |376,848 |± 8,904 |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,806 |± 0,459 |us/op|

Order restored by specialization on ChainNode
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |51,804 |± 0,650  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,582 |± 0,495  |us/op|
