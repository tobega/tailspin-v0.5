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
