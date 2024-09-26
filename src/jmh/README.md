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

2024-08-12 Improve measure performance
|Benchmark                                      |Mode  |Cnt  |   Score |    Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  20,066 |±   0,250  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |  33,230 |±   0,244  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |  38,368 |±   0,543  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  36,225 |±   0,969  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  | 242,712 |±  30,909  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  59,304 |±   4,228  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  25,078 |±   3,574  |us/op|
|ListBenchmark.list_tailspin                    |avgt  |  5  |1159,714 |± 271,304  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  12,705 |±   0,939  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  20,295 |±   2,158  |us/op|

2024-08-13 fix conditions and add alternative list benchmark with empty array instead of optional field
|Benchmark                                      |Mode  |Cnt  |  Score |   Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  | 19,823 |±  0,635  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  | 33,206 |±  0,256  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  | 38,493 |±  0,904  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  | 36,395 |±  1,171  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  |221,785 |±  4,664  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  | 56,249 |±  0,751  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  | 23,119 |±  0,508  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  |882,169 |±  9,181  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  |944,346 |± 26,222  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  | 11,823 |±  0,302  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  | 19,231 |±  0,423  |us/op|

2024-08-13 Temporary addition of javascript list benchmark. Tailspin is significantly faster without typechecking and only 50% slower with vocabulary typing
|Benchmark                             |Mode  |Cnt  |   Score |    Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|ListBenchmark.list_java               |avgt  |  5  |  25,044 |±   2,175  |us/op|
|ListBenchmark.list_javascript         |avgt  |  5  | 684,716 |±  73,494  |us/op|
|ListBenchmark.list_tailspin_empty     |avgt  |  5  | 992,382 |±  42,454  |us/op|
|ListBenchmark.list_tailspin_optional  |avgt  |  5  |1021,012 |± 153,130  |us/op|

2024-08-13 intern measure units
|Benchmark                                      |Mode  |Cnt  |  Score |   Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  | 20,806 |±  1,329  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  | 33,971 |±  1,820  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  | 38,489 |±  1,126  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  | 36,232 |±  0,256  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  |124,074 |±  3,835  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  | 56,190 |±  0,522  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  | 23,174 |±  0,466  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  |886,183 |± 12,564  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  |935,029 |± 28,690  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  | 11,819 |±  0,135  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  | 19,180 |±  0,417  |us/op|

2024-08-16 Refactor measure operations
|Benchmark                                      |Mode  |Cnt  |   Score |    Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  19,766 |±   0,270  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |  33,039 |±   0,465  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |  38,303 |±   0,801  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  36,226 |±   0,096  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  | 123,804 |±   0,792  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  56,596 |±   1,291  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  22,958 |±   0,454  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  | 886,821 |±  13,059  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  |1027,036 |± 300,237  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  12,435 |±   2,038  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  19,346 |±   0,357  |us/op|

2024-08-17 Type bound for equality - bad performance hit
|Benchmark                                      |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  19,660 |±  0,445  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |  32,488 |±  1,222  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |  37,300 |±  1,096  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  35,557 |±  1,085  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  | 120,477 |±  3,080  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |3266,338 |± 99,197  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  22,333 |±  0,523  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  |2611,152 |± 53,963  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  |2926,670 |± 75,537  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  11,338 |±  0,359  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  18,555 |±  0,475  |us/op|

2024-08-17 Refactor type bound for equality
|Benchmark                                      |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|-----|---------|----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  20,088 |±  0,180  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |  33,847 |±  2,903  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |  38,815 |±  2,106  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  36,340 |±  0,289  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  | 124,671 |±  3,678  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  57,200 |±  2,924  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  22,733 |±  0,757  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  | 877,572 |± 23,691  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  |1033,012 |± 16,249  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  11,427 |±  0,400  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  20,548 |±  2,583  |us/op|

2024-08-18 dynamic range type bounds are costly
|Benchmark                                      |Mode  |Cnt  |   Score |   Error  |Units|
|-----------------------------------------------|------|-----|---------|----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  19,303 |±  0,379  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  | 919,857 |± 22,963  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |1143,652 |± 25,431  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  36,840 |±  0,902  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  | 125,336 |± 21,682  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  55,826 |±  1,323  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  24,152 |±  2,993  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  | 877,485 |± 38,974  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  | 929,849 |± 21,578  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  11,514 |±  0,738  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  | 364,272 |±  7,661  |us/op|

2024-08-18 Cache dynamic type bound
|Benchmark                                      |Mode  |Cnt  |   Score |    Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  19,580 |±   0,903  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |  32,409 |±   0,480  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |  37,773 |±   0,878  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  35,798 |±   1,041  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  | 121,396 |±   2,653  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  56,386 |±   0,573  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  22,790 |±   0,642  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  |1012,899 |±  34,247  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  | 965,548 |± 237,757  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  13,795 |±   0,834  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  18,532 |±   0,189  |us/op|

2024-08-19 the nbody benchmark is quite slow. Seems parsing broke on Fibonacci
|Benchmark                                  |Mode  |Cnt  |    Score |     Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java              |avgt  |  5  |   20,254 |±    0,619  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate  |avgt  |  5  |   34,097 |±    0,419  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse  |avgt  |  5  |   40,558 |±    2,400  |us/op|
|FibonacciBenchmark.recursive_java          |avgt  |  5  |   38,552 |±    0,966  |us/op|
|ListBenchmark.list_java                    |avgt  |  5  |   24,478 |±    1,058  |us/op|
|ListBenchmark.list_tailspin_empty          |avgt  |  5  |  974,952 |±   50,405  |us/op|
|ListBenchmark.list_tailspin_optional       |avgt  |  5  | 1502,678 |±  157,128  |us/op|
|NBodyBenchmark.nbody_java                  |avgt  |  5  |   16,462 |±    0,258  |us/op|
|NBodyBenchmark.nbody_tailspin              |avgt  |  5  |15918,030 |± 1730,642  |us/op|
|PascalBenchmark.triangle_java              |avgt  |  5  |   12,369 |±    0,356  |us/op|
|PascalBenchmark.triangle_tailspin          |avgt  |  5  |   20,221 |±    0,950  |us/op|

2024-08-19 fix parsing
|Benchmark                                      |Mode  |Cnt  |    Score |     Error  |Units|
|-----------------------------------------------|------|-----|----------|------------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |   29,004 |±    1,684  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |   44,175 |±    2,687  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |   52,048 |±    3,287  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |   49,989 |±    1,012  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  |  170,536 |±   24,423  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |   83,964 |±    3,473  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |   30,773 |±    0,552  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  | 1323,872 |±  216,534  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  | 2369,795 |±  474,219  |us/op|
|NBodyBenchmark.nbody_java                      |avgt  |  5  |   19,997 |±    0,767  |us/op|
|NBodyBenchmark.nbody_tailspin                  |avgt  |  5  |20975,904 |± 2530,225  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |   16,403 |±    1,445  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |   25,603 |±    0,800  |us/op|

2024-08-20 Running nbody java with BigDecimal takes about the same time as the Tailspin version (although it gives the wrong answer)
NBodyBenchmark.nbody_java                      avgt    5  11220,231 ± 321,714  us/op
NBodyBenchmark.nbody_tailspin                  avgt    5  15152,368 ± 797,550  us/op

2024-08-21 Represent small SciNums as double values
|Benchmark                                      |Mode  |Cnt  |   Score |    Error  |Units|
|-----------------------------------------------|------|-----|---------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |  19,257 |±   0,260  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |  33,153 |±   2,977  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |  37,846 |±   0,488  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |  35,854 |±   0,227  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  | 124,990 |±   6,453  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |  56,764 |±   1,082  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |  22,811 |±   0,581  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  |1190,262 |±  37,626  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  | 921,520 |±  15,058  |us/op|
|NBodyBenchmark.nbody_java                      |avgt  |  5  |  15,332 |±   0,263  |us/op|
|NBodyBenchmark.nbody_tailspin                  |avgt  |  5  |6056,729 |± 210,260  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |  13,981 |±   0,508  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |  18,788 |±   0,523  |us/op|

2024-08-22 Reverting because it is not worth the complexity. Have to think about what to do here. Just go floating point?
|Benchmark                                      |Mode  |Cnt  |    Score |    Error  |Units|
|-----------------------------------------------|------|-----|----------|-----------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |   19,902 |±   0,952  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |   32,790 |±   1,220  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |   37,496 |±   0,947  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |   35,745 |±   0,761  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  |  121,144 |±   2,372  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |   56,266 |±   2,257  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |   22,520 |±   0,850  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  |  887,708 |±  13,519  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  |  938,157 |±  33,229  |us/op|
|NBodyBenchmark.nbody_java                      |avgt  |  5  |   15,277 |±   0,536  |us/op|
|NBodyBenchmark.nbody_tailspin                  |avgt  |  5  |15793,573 |± 538,511  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |   11,690 |±   0,470  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |   18,700 |±   0,369  |us/op|

2024-08-24 Generalize ranges
|Benchmark                                      |Mode  |Cnt  |    Score |     Error  |Units|
|-----------------------------------------------|------|-----|----------|------------|-----|
|BubblesortBenchmark.sort_java                  |avgt  |  5  |   19,573 |±    0,546  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate      |avgt  |  5  |   31,982 |±    1,281  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse      |avgt  |  5  |   36,394 |±    0,493  |us/op|
|FibonacciBenchmark.recursive_java              |avgt  |  5  |   35,852 |±    0,827  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure  |avgt  |  5  |  122,743 |±    5,149  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw      |avgt  |  5  |   56,946 |±    1,760  |us/op|
|ListBenchmark.list_java                        |avgt  |  5  |   22,700 |±    0,365  |us/op|
|ListBenchmark.list_tailspin_empty              |avgt  |  5  |  856,712 |±   22,676  |us/op|
|ListBenchmark.list_tailspin_optional           |avgt  |  5  |  930,229 |±   21,536  |us/op|
|NBodyBenchmark.nbody_java                      |avgt  |  5  |   15,270 |±    0,271  |us/op|
|NBodyBenchmark.nbody_tailspin                  |avgt  |  5  |16511,772 |± 3048,357  |us/op|
|PascalBenchmark.triangle_java                  |avgt  |  5  |   11,593 |±    0,128  |us/op|
|PascalBenchmark.triangle_tailspin              |avgt  |  5  |   18,728 |±    0,513  |us/op|

2024-08-25 Measure performance on all raw number types on fibonacci
|Benchmark                                           |Mode  |Cnt  |    Score |     Error  |Units|
|----------------------------------------------------|------|-----|----------|------------|-----|
|BubblesortBenchmark.sort_java                       |avgt  |  5  |   20,297 |±    1,455  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate           |avgt  |  5  |   33,889 |±    3,291  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse           |avgt  |  5  |   38,871 |±    2,569  |us/op|
|FibonacciBenchmark.recursive_java                   |avgt  |  5  |   36,343 |±    0,667  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber     |avgt  |  5  |  558,561 |±   22,317  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long  |avgt  |  5  |  130,141 |±    3,204  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational      |avgt  |  5  |10022,302 |±  261,510  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long      |avgt  |  5  |   58,881 |±    2,966  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum        |avgt  |  5  |  948,608 |±   67,944  |us/op|
|ListBenchmark.list_java                             |avgt  |  5  |   24,603 |±    2,738  |us/op|
|ListBenchmark.list_tailspin_empty                   |avgt  |  5  |  957,918 |±   90,104  |us/op|
|ListBenchmark.list_tailspin_optional                |avgt  |  5  | 1038,677 |±   59,960  |us/op|
|NBodyBenchmark.nbody_java                           |avgt  |  5  |   15,908 |±    0,841  |us/op|
|NBodyBenchmark.nbody_tailspin                       |avgt  |  5  |18456,447 |± 1593,608  |us/op|
|PascalBenchmark.triangle_java                       |avgt  |  5  |   12,015 |±    0,739  |us/op|
|PascalBenchmark.triangle_tailspin                   |avgt  |  5  |   19,882 |±    1,299  |us/op|

2024-09-05 After latest array projection work, things may need a little optimizing
|Benchmark                                           |Mode  |Cnt  |    Score |      Error  |Units|
|----------------------------------------------------|------|-----|----------|------------|-----|
|BubblesortBenchmark.sort_java                       |avgt  |  5  |   20,104 |±     0,416  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate           |avgt  |  5  |  924,398 |±    73,358  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse           |avgt  |  5  | 1161,091 |±   295,721  |us/op|
|FibonacciBenchmark.recursive_java                   |avgt  |  5  |   35,893 |±     0,408  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber     |avgt  |  5  |  526,284 |±    17,124  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long  |avgt  |  5  |  121,127 |±     4,376  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational      |avgt  |  5  | 9166,873 |±   391,761  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long      |avgt  |  5  |   56,515 |±     0,941  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum        |avgt  |  5  |  888,670 |±    21,272  |us/op|
|ListBenchmark.list_java                             |avgt  |  5  |   24,108 |±     6,469  |us/op|
|ListBenchmark.list_tailspin_empty                   |avgt  |  5  |12605,883 |±  9061,488  |us/op|
|ListBenchmark.list_tailspin_optional                |avgt  |  5  |18388,441 |± 11333,414  |us/op|
|NBodyBenchmark.nbody_java                           |avgt  |  5  |   15,969 |±     0,647  |us/op|
|NBodyBenchmark.nbody_tailspin                       |avgt  |  5  |35963,946 |±  5531,127  |us/op|
|PascalBenchmark.triangle_java                       |avgt  |  5  |   12,252 |±     0,255  |us/op|
|PascalBenchmark.triangle_tailspin                   |avgt  |  5  |  214,786 |±     6,629  |us/op|

2024-09-06 So simple Array selection projection
|Benchmark                                           |Mode  |Cnt  |    Score |     Error  |Units|
|----------------------------------------------------|------|-----|----------|------------|-----|
|BubblesortBenchmark.sort_java                       |avgt  |  5  |   19,760 |±    1,320  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate           |avgt  |  5  |   31,662 |±    0,729  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse           |avgt  |  5  |   36,686 |±    0,736  |us/op|
|FibonacciBenchmark.recursive_java                   |avgt  |  5  |   35,593 |±    0,242  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber     |avgt  |  5  |  522,036 |±   17,063  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long  |avgt  |  5  |  120,948 |±    2,944  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational      |avgt  |  5  | 8761,341 |±  110,736  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long      |avgt  |  5  |   57,015 |±    4,173  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum        |avgt  |  5  |  892,497 |±    7,981  |us/op|
|ListBenchmark.list_java                             |avgt  |  5  |   22,596 |±    1,033  |us/op|
|ListBenchmark.list_tailspin_empty                   |avgt  |  5  |  886,772 |±   15,053  |us/op|
|ListBenchmark.list_tailspin_optional                |avgt  |  5  |  929,349 |±   20,581  |us/op|
|NBodyBenchmark.nbody_java                           |avgt  |  5  |   15,347 |±    0,196  |us/op|
|NBodyBenchmark.nbody_tailspin                       |avgt  |  5  |15168,646 |± 1417,988  |us/op|
|PascalBenchmark.triangle_java                       |avgt  |  5  |   11,458 |±    0,393  |us/op|
|PascalBenchmark.triangle_tailspin                   |avgt  |  5  |   19,464 |±    2,686  |us/op|

2024-09-26 Checkpoint
|Benchmark                                           |Mode  |Cnt  |    Score |    Error  |Units|
|----------------------------------------------------|------|-----|----------|------------|-----|
|BubblesortBenchmark.sort_java                       |avgt  |  5  |   19,409 |±   0,295  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate           |avgt  |  5  |   34,697 |±   3,840  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse           |avgt  |  5  |   42,241 |±   1,030  |us/op|
|FibonacciBenchmark.recursive_java                   |avgt  |  5  |   43,668 |±   0,667  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber     |avgt  |  5  |  524,181 |±   9,051  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long  |avgt  |  5  |  122,428 |±   3,971  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational      |avgt  |  5  | 9256,626 |± 253,778  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long      |avgt  |  5  |   56,705 |±   1,161  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum        |avgt  |  5  |  895,264 |±  21,488  |us/op|
|ListBenchmark.list_java                             |avgt  |  5  |   22,663 |±   0,348  |us/op|
|ListBenchmark.list_tailspin_empty                   |avgt  |  5  |  885,263 |±  28,601  |us/op|
|ListBenchmark.list_tailspin_optional                |avgt  |  5  |  947,463 |±  40,851  |us/op|
|NBodyBenchmark.nbody_java                           |avgt  |  5  |   15,424 |±   0,254  |us/op|
|NBodyBenchmark.nbody_tailspin                       |avgt  |  5  |15874,652 |± 401,710  |us/op|
|PascalBenchmark.triangle_java                       |avgt  |  5  |   11,453 |±   0,368  |us/op|
|PascalBenchmark.triangle_tailspin                   |avgt  |  5  |   20,289 |±   0,291  |us/op|

2024-09-26 Only static object frame access
|Benchmark                                           |Mode  |Cnt  |    Score |     Error  |Units|
|----------------------------------------------------|------|-----|----------|------------|-----|
|BubblesortBenchmark.sort_java                       |avgt  |  5  |   19,742 |±    0,450  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate           |avgt  |  5  |   34,985 |±    0,384  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse           |avgt  |  5  |   42,440 |±    1,004  |us/op|
|FibonacciBenchmark.recursive_java                   |avgt  |  5  |   36,126 |±    0,290  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber     |avgt  |  5  |  542,580 |±    5,244  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long  |avgt  |  5  |  125,786 |±    3,261  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational      |avgt  |  5  | 9119,309 |±  243,722  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long      |avgt  |  5  |   70,702 |±    1,399  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum        |avgt  |  5  |  940,555 |±   16,851  |us/op|
|ListBenchmark.list_java                             |avgt  |  5  |   22,991 |±    0,353  |us/op|
|ListBenchmark.list_tailspin_empty                   |avgt  |  5  |  887,310 |±   21,259  |us/op|
|ListBenchmark.list_tailspin_optional                |avgt  |  5  |  873,727 |±   57,750  |us/op|
|NBodyBenchmark.nbody_java                           |avgt  |  5  |   15,576 |±    0,587  |us/op|
|NBodyBenchmark.nbody_tailspin                       |avgt  |  5  |15965,932 |± 1340,698  |us/op|
|PascalBenchmark.triangle_java                       |avgt  |  5  |   11,817 |±    0,385  |us/op|
|PascalBenchmark.triangle_tailspin                   |avgt  |  5  |   21,501 |±    1,932  |us/op|
