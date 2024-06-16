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
|-----------------------------------------------|------|--------|---------|-------|-----|
|FibonacciBenchmark.recursive_eval_js           |avgt    |5    |62,309 |±  2,355  |us/op|
|FibonacciBenchmark.recursive_eval_sl           |avgt    |5    |63,485 |±  0,783  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin     |avgt    |5  |1168,769 |± 21,090  |us/op|
|FibonacciBenchmark.recursive_eval_v0_tailspin  |avgt    |5  |2212,986 |± 46,117  |us/op|
|FibonacciBenchmark.recursive_java              |avgt    |5    |36,587 |±  0,111  |us/op|

Going to just returning value instead of mucking with iterators:
|Benchmark                                         |Mode  |Cnt  |   Score    |Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|FibonacciBenchmark.recursive_eval_tailspin        |avgt  |  5  |1194,630 |± 46,617  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin_value  |avgt  |  5  |  53,368 |±  0,559  |us/op|
|FibonacciBenchmark.recursive_java                 |avgt  |  5  |  36,893 |±  0,252  |us/op|

Tried to replace iterators with MaterializedFrame, but that was worse (1573,9 us/op)

Creating an array-based ResultIterator instead of using java Collectons really did the trick
|Benchmark                                         |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|FibonacciBenchmark.recursive_eval_tailspin        |avgt  |  5  |88,607 |± 1,519  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin_value  |avgt  |  5  |53,632 |± 1,517  |us/op|
|FibonacciBenchmark.recursive_java                 |avgt  |  5  |36,708 |± 0,115  |us/op|

Shaving a bit more by allowing single results from a transform
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |53,055 |± 0,592  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,966 |± 1,233  |us/op|

More realistic code with a chain on the recursion. Can we optimize when parsing?
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |376,848 |± 8,904 |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,806 |± 0,459 |us/op|

Order restored by specialization on ChainNode
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |51,804 |± 0,650  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,582 |± 0,495  |us/op|

Introduced a way to pass the defining scope. There is a slight cost for accessing the parent scope (BTW, @ExplodeLoop was 100x faster)
If no parent scope needs to be accessed, it can be optimized away
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |59,101 |± 1,034  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,857 |± 0,384  |us/op|

Got Bubblesort working, but obviously doing something stupid
|Benchmark                                   |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort_java               |avgt  |  5  |  25,625 |±  0,466  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |1533,427 |± 35,622  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |  54,172 |±  2,411  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |  36,636 |±  0,132  |us/op|

A little less stupid
|Benchmark                                   |Mode  |Cnt  |   Score |    Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort_java               |avgt  |  5  |  25,862 |±   0,894  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |1003,520 |± 122,934  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |  54,288 |±   2,761  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |  36,961 |±   0,360  |us/op|

Notes trying to find bottleneck: The matcher doesn't matter much, nor does the mutation of state.
SendToTemplates itself takes ~400 usec, so ~500 seems down to the iteration in chain stages.
Allocating bigger chunks to ResultIterator didn't matter

Added a differently coded bubblesort with more templates calls and more state access
|Benchmark                                   |Mode  |Cnt  |  Score |   Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |935,129 |± 12,307  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 25,562 |±  0,402  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |979,289 |± 78,278  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 56,546 |±  1,739  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,977 |±  1,192  |us/op|

Removing cached interop libraries in favour of TruffleBoundary and java code did something good
|Benchmark                                   |Mode  |Cnt  |  Score |   Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |368,516 |±  2,784  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 25,897 |±  0,574  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |932,141 |± 12,395  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 53,774 |±  0,606  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,687 |±  0,509  |us/op|

A SinkNode is a little faster than an EmitNode
|Benchmark                                   |Mode  |Cnt  |  Score |   Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |366,792 |±  3,908  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 25,608 |±  0,471  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |886,675 |± 19,197  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 56,799 |±  7,174  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 37,695 |±  1,232  |us/op|

Simplifying a little
|Benchmark                                   |Mode  |Cnt  |  Score |   Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |367,527 |± 11,028  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 25,649 |±  0,307  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |815,799 |± 63,238  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 53,959 |±  0,901  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,838 |±  0,376  |us/op|

Doing range iteration as iteration made an improvement.
TruffleBoundary was no longer needed when dispatching on a concrete class instead of an interface.
Remaining difference between the two sorts is probably because nodes are better than regular java classes.
Need to find a more nodesy way to express iterators.
Do I need to make my own VM to better handle data streaming?
|Benchmark                                   |Mode  |Cnt  |  Score |  Error|  Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |342,798 |± 2,389|  us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 26,082 |± 0,791|  us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |557,185 |± 7,374|  us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 54,881 |± 2,109|  us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,766 |± 0,641|  us/op|

Oddly enough, making iterators nodesy had a great effect on the less iterator-heavy version, but
not so much on the iterator-heavy version, which is the opposite of what I expected.
|Benchmark                                   |Mode  |Cnt  |  Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |171,817 |± 1,499  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 26,035 |± 0,428  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |508,323 |± 6,977  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 56,662 |± 6,624  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 37,454 |± 0,347  |us/op|

Minor improvement
|Benchmark                                   |Mode  |Cnt  |  Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |173,146 |± 4,575  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 26,174 |± 0,633  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |481,628 |± 6,990  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 54,624 |± 0,133  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,882 |± 0,235  |us/op|

Making the java code more similar to the Tailspin code makes me feel happy about the performance
of the sort2. Still puzzling why the "simpler" iterating Tailspin sort takes more than twice the time.
|Benchmark                                   |Mode  |Cnt  |  Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |170,915 |± 4,614  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 76,618 |± 0,702  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |483,675 |± 6,186  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 54,440 |± 0,471  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,842 |± 0,540  |us/op|

Scaled down the bubblesort benchmark and added a Pascal's triangle benchmark
|Benchmark                                   |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |  46,089 |±  0,550  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |  20,453 |±  0,343  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  | 125,255 |±  1,416  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |  54,108 |±  0,546  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |  36,871 |±  0,396  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |  12,293 |±  0,113  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |1536,142 |± 16,376  |us/op|

Minor tweaks
|Benchmark                                   |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |  45,488 |±  0,504  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |  20,100 |±  0,287  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  | 124,575 |±  1,170  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |  53,218 |±  2,803  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |  36,560 |±  0,136  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |  12,035 |±  0,077  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |1515,745 |± 24,911  |us/op|

A TruffleBoundary shaves a little more, but what is the fundamental problem here?
|Benchmark                                   |Mode  |Cnt     |Score    |Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt    |5  |  43,688 |±  0,757  |us/op|
|BubblesortBenchmark.sort_java               |avgt    |5  |  20,069 |±  0,178  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt    |5  | 124,479 |±  0,769  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt    |5  |  53,445 |±  0,960  |us/op|
|FibonacciBenchmark.recursive_java           |avgt    |5  |  36,614 |±  0,218  |us/op|
|PascalBenchmark.triangle_java               |avgt    |5  |  12,255 |±  0,165  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt    |5  |1505,070 |± 29,806  |us/op|

Using ArrayList instead of ResultIterator is miraculous
|Benchmark                                   |Mode  |Cnt  |  Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  | 42,483 |± 1,317  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 20,389 |± 0,917  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |123,362 |± 1,176  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 53,446 |± 3,029  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,578 |± 0,256  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  | 12,095 |± 0,129  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  | 36,387 |± 0,538  |us/op|

Sending in a result builder into templates introduces a little overhead but has a good effect
on the Pascal benchmark.
|Benchmark                                   |Mode  |Cnt  |  Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  | 43,741 |± 1,528  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 20,439 |± 1,197  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |128,674 |± 1,709  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 61,445 |± 0,758  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 41,898 |± 0,103  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  | 12,283 |± 0,200  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  | 25,659 |± 0,379  |us/op|

Better separation between streams (from transforms) and simple values
|Benchmark                                   |Mode  |Cnt  |  Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  | 41,064 |± 0,474  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  | 20,252 |± 0,232  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |136,688 |± 2,323  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  | 63,402 |± 0,679  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  | 36,651 |± 0,173  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  | 12,352 |± 0,195  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  | 20,969 |± 0,348  |us/op|

Decoupling the double iteration with a template call solves the knot!
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |41,271 |± 0,182  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |20,325 |± 0,198  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |41,073 |± 1,304  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |63,627 |± 0,465  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,632 |± 0,237  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |12,169 |± 0,243  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |20,877 |± 0,390  |us/op|

Shaving a little by relentless profiling
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |37,947 |± 1,147  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |20,389 |± 0,389  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |42,403 |± 1,160  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |58,968 |± 0,663  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,817 |± 0,185  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |14,845 |± 0,245  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |21,177 |± 0,324  |us/op|

ReadContextValue replaces LocalReferenceNode
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|-----------------------------------------------|------|--------|---------|-------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |37,924 |± 1,745  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |19,598 |± 0,602  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |40,549 |± 0,383  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |54,846 |± 1,213  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |35,963 |± 0,745  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |11,833 |± 0,182  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |20,541 |± 1,972  |us/op|

Using truffle block node helps a little
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|--------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.sort2_tailspin          |avgt  |  5  |36,066 |± 0,098  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |19,516 |± 0,253  |us/op|
|BubblesortBenchmark.sort_tailspin           |avgt  |  5  |38,665 |± 0,920  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |54,981 |± 1,778  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |35,896 |± 0,646  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |11,518 |± 0,314  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |20,326 |± 0,462  |us/op|
