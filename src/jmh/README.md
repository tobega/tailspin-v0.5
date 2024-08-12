# Performance tests

2024-06-23
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|--------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.iterate_tailspin        |avgt  |  5  |39,801 |± 0,469  |us/op|
|BubblesortBenchmark.recurse_tailspin        |avgt  |  5  |38,033 |± 0,612  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |19,469 |± 0,206  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |55,552 |± 0,978  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |36,470 |± 3,051  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |11,672 |± 0,338  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |21,156 |± 0,342  |us/op|

2024-06-27 Introduce condition node and executeWith
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|--------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.iterate_tailspin        |avgt  |  5  |41,940 |± 1,197  |us/op|
|BubblesortBenchmark.recurse_tailspin        |avgt  |  5  |38,616 |± 0,548  |us/op|
|BubblesortBenchmark.sort_java               |avgt  |  5  |19,902 |± 0,457  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |56,109 |± 2,619  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |41,736 |± 0,654  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |11,702 |± 0,295  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |21,075 |± 0,317  |us/op|

2024-06-30 Run the fibonacci benchmark from tailspin source code
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|--------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.sort_java               |avgt  |  5  |19,688 |± 0,486  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate   |avgt  |  5  |41,042 |± 0,827  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse   |avgt  |  5  |38,868 |± 1,090  |us/op|
|FibonacciBenchmark.recursive_eval_tailspin  |avgt  |  5  |54,976 |± 1,228  |us/op|
|FibonacciBenchmark.recursive_java           |avgt  |  5  |35,881 |± 0,356  |us/op|
|FibonacciBenchmark.recursive_tailspin       |avgt  |  5  |55,696 |± 0,807  |us/op|
|PascalBenchmark.triangle_java               |avgt  |  5  |11,541 |± 0,220  |us/op|
|PascalBenchmark.triangle_tailspin           |avgt  |  5  |20,897 |± 0,505  |us/op|

2024-07-12 No change from introducing Reference class

2024-07-15 No change from using callLevel and definitionLevl for SendToTemplates

2024-07-21 adding @CompilationFinal to Slot brought things back to what it was

2024-07-21 Running pascal benchmark from tailspin source
NOTE: evaluating the answer through truffle array interop is really slow, so casting Value to TailspinArray
|Benchmark                                   |Mode  |Cnt  | Score |  Error  |Units|
|--------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.sort_java              |avgt    |5  |20,025 |± 0,264  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate  |avgt    |5  |41,724 |± 0,891  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse  |avgt    |5  |40,047 |± 0,277  |us/op|
|FibonacciBenchmark.recursive_java          |avgt    |5  |36,336 |± 0,577  |us/op|
|FibonacciBenchmark.recursive_tailspin      |avgt    |5  |56,017 |± 0,804  |us/op|
|PascalBenchmark.triangle_java              |avgt    |5  |12,024 |± 0,415  |us/op|
|PascalBenchmark.triangle_tailspin          |avgt    |5  |19,085 |± 0,239  |us/op|
|PascalBenchmark.triangle_tailspin_nodes    |avgt    |5  |18,932 |± 0,383  |us/op|

2024-07-27 Running bubblesort from tailspin source. Range iteration now working as expected!
|Benchmark                                        |Mode  |Cnt  | Score |  Error  |Units|
|--------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.sort_java                    |avgt  |  5  |19,685 |± 0,366  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate        |avgt  |  5  |34,536 |± 0,444  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_nodes  |avgt  |  5  |42,123 |± 0,725  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse        |avgt  |  5  |39,349 |± 0,452  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_nodes  |avgt  |  5  |39,318 |± 0,579  |us/op|
|FibonacciBenchmark.recursive_java                |avgt  |  5  |36,193 |± 0,172  |us/op|
|FibonacciBenchmark.recursive_tailspin            |avgt  |  5  |56,488 |± 1,288  |us/op|
|PascalBenchmark.triangle_java                    |avgt  |  5  |14,126 |± 0,305  |us/op|
|PascalBenchmark.triangle_tailspin                |avgt  |  5  |19,217 |± 0,238  |us/op|

2024-07-28 Exclusive range ends, and alternative matchers and several conditions changed nothing
|Benchmark                                  |Mode  |Cnt  | Score |  Error  |Units|
|-------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.sort_java              |avgt  |  5  |19,680 |± 0,257  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate  |avgt  |  5  |34,431 |± 0,548  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse  |avgt  |  5  |40,209 |± 1,391  |us/op|
|FibonacciBenchmark.recursive_java          |avgt  |  5  |36,228 |± 0,246  |us/op|
|FibonacciBenchmark.recursive_tailspin      |avgt  |  5  |56,710 |± 0,575  |us/op|
|PascalBenchmark.triangle_java              |avgt  |  5  |11,848 |± 0,231  |us/op|
|PascalBenchmark.triangle_tailspin          |avgt  |  5  |19,411 |± 1,143  |us/op|

2024-08-03 List benchmark is pretty disastrous
|Benchmark                                  |Mode  |Cnt  |   Score |    Error  |Units|
|-------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.sort_java              |avgt  |  5  |  20,257 |±   0,111  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate  |avgt  |  5  |  34,862 |±   0,440  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse  |avgt  |  5  |  40,653 |±   0,536  |us/op|
|FibonacciBenchmark.recursive_java          |avgt  |  5  |  36,468 |±   0,159  |us/op|
|FibonacciBenchmark.recursive_tailspin      |avgt  |  5  |  57,899 |±   0,524  |us/op|
|ListBenchmark.list_java                    |avgt  |  5  |  14,800 |±   0,774  |us/op|
|ListBenchmark.list_tailspin                |avgt  |  5  |7956,805 |± 990,327  |us/op|
|PascalBenchmark.triangle_java              |avgt  |  5  |  12,436 |±   1,824  |us/op|
|PascalBenchmark.triangle_tailspin          |avgt  |  5  |  20,519 |±   2,822  |us/op|

2024-08-04 Improving structure handling
|Benchmark                                  |Mode  |Cnt  |  Score |  Error  |Units|
|-------------------------------------------|------|-----|-------|---------|-----|
|BubblesortBenchmark.sort_java              |avgt  |  5  | 19,893 |± 0,780  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate  |avgt  |  5  | 34,466 |± 0,280  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse  |avgt  |  5  | 39,900 |± 0,648  |us/op|
|FibonacciBenchmark.recursive_java          |avgt  |  5  | 36,208 |± 0,309  |us/op|
|FibonacciBenchmark.recursive_tailspin      |avgt  |  5  | 55,926 |± 0,604  |us/op|
|ListBenchmark.list_java                    |avgt  |  5  | 14,647 |± 0,765  |us/op|
|ListBenchmark.list_tailspin                |avgt  |  5  |173,311 |± 4,362  |us/op|
|PascalBenchmark.triangle_java              |avgt  |  5  | 11,713 |± 0,212  |us/op|
|PascalBenchmark.triangle_tailspin          |avgt  |  5  | 18,994 |± 0,147  |us/op|

2024-08-04 Make the java list more similar to tailspin
|Benchmark                                  |Mode  |Cnt  |  Score |   Error  |Units|
|-------------------------------------------|------|-----|--------|----------|-----|
|BubblesortBenchmark.sort_java              |avgt  |  5  | 20,635 |±  3,650  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate  |avgt  |  5  | 34,841 |±  1,023  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse  |avgt  |  5  | 40,387 |±  0,385  |us/op|
|FibonacciBenchmark.recursive_java          |avgt  |  5  | 36,932 |±  0,782  |us/op|
|FibonacciBenchmark.recursive_tailspin      |avgt  |  5  | 56,156 |±  1,030  |us/op|
|ListBenchmark.list_java                    |avgt  |  5  | 24,272 |±  2,562  |us/op|
|ListBenchmark.list_tailspin                |avgt  |  5  |187,940 |± 28,834  |us/op|
|PascalBenchmark.triangle_java              |avgt  |  5  | 11,891 |±  0,221  |us/op|
|PascalBenchmark.triangle_tailspin          |avgt  |  5  | 19,183 |±  0,200  |us/op|

2024-08-08 Vocabulary type check has a big cost
|Benchmark                                  |Mode  |Cnt  |   Score |   Error  |Units|
|-------------------------------------------|------|-----|---------|----------|-----|
|BubblesortBenchmark.sort_java              |avgt  |  5  |  20,793 |±  1,060  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate  |avgt  |  5  |  36,726 |±  7,324  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse  |avgt  |  5  |  51,225 |± 44,453  |us/op|
|FibonacciBenchmark.recursive_java          |avgt  |  5  |  41,301 |± 11,838  |us/op|
|FibonacciBenchmark.recursive_tailspin      |avgt  |  5  |  58,936 |±  3,958  |us/op|
|ListBenchmark.list_java                    |avgt  |  5  |  24,985 |±  4,224  |us/op|
|ListBenchmark.list_tailspin                |avgt  |  5  |1012,466 |± 40,730  |us/op|
|PascalBenchmark.triangle_java              |avgt  |  5  |  12,687 |±  0,568  |us/op|
|PascalBenchmark.triangle_tailspin          |avgt  |  5  |  19,476 |±  0,172  |us/op|

2024-08-12 measures are expensive. Something happened to Bubblesort along the way
|Benchmark                                      |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|-----|---------|----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  20,175 |±  0,494  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  | 388,827 |± 36,815  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  | 412,121 |± 83,378  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  36,193 |±  0,169  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  |3870,789 |± 24,982  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  55,774 |±  0,981  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  23,063 |±  1,331  |us/op|
|ListBenchmark.list_tailspin                    |avgt  |  5  | 991,094 |± 19,492  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  11,780 |±  0,151  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  19,038 |±  0,225  |us/op|

2024-08-12 Only freeze the actual state value retrieved
|Benchmark                                      |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|-----|---------|----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  20,327 |±  0,299  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |  33,973 |±  1,896  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |  38,560 |±  0,511  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  36,288 |±  0,276  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  |3743,421 |± 44,952  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  56,612 |±  0,909  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  23,433 |±  1,116  |us/op|
|ListBenchmark.list_tailspin                    |avgt  |  5  | 985,685 |± 30,591  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  11,765 |±  0,121  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  19,210 |±  0,326  |us/op|
