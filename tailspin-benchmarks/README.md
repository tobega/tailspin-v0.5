# Performance tests
## How to run
*NB: clean and package in the top project. If you ever ran install, the benchmark may be using old code*
Build the jar `mvn clean package`
Run `java -jar tailspin-benchmarks/target/benchmarks.jar`

### Options
Faster run for "smoke test"
```bash
# -f 1 (1 fork), -wi 2 (2 warmup iterations), -i 3 (3 measurement iterations)
java -jar tailspin-benchmarks/target/benchmarks.jar -f 1 -wi 2 -i 3
```

List benchmarks `java -jar tailspin-benchmarks/target/benchmarks.jar -l`
Run particular benchmarks, select by regex `java -jar tailspin-benchmarks/target/benchmarks.jar nacci`

### Profiling
```bash
# Check GC allocation rates
java -jar tailspin-benchmarks/target/benchmarks.jar -prof gc

# See which methods are "hot" (requires perf on Linux or DTrace on Mac)
java -jar tailspin-benchmarks/target/benchmarks.jar -prof stack
```

## Results
Look in git history for earlier results

2026-01-01 Introducing small rationals
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    21,080 |±    0,820  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    37,331 |±    1,203  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    39,752 |±    0,630  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    42,422 |±   17,872  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    41,633 |±    2,411  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    59,664 |±    4,496  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    93,564 |±    1,570  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    41,149 |±    1,779  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    37,169 |±    0,261  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1647,810 |±   10,213  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   605,714 |±   38,907  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   147,770 |±    1,211  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  2049,524 |±   46,009  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    82,773 |±    1,962  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1079,814 |±   14,689  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   399,975 |±   15,645  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  9102,078 |±  318,994  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    24,537 |±    0,217  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |   952,201 |±   15,380  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  1021,925 |±   92,809  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 13198,866 |±  993,788  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    16,938 |±    2,770  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 16683,648 |± 1066,799  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4812,849 |± 1762,913  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    12,694 |±    0,132  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    22,141 |±    0,183  |us/op|

2026-01-03 Add Hilbert benchmark for rationals
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    20,057 |±    1,572  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    35,473 |±    2,558  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    37,616 |±    0,325  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    38,119 |±    2,717  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    38,805 |±    2,823  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    55,467 |±    4,604  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    87,375 |±    0,735  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    39,010 |±    2,972  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    36,419 |±    1,956  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1553,969 |±  177,282  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   590,352 |±  151,836  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   142,129 |±   18,813  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  2017,279 |±  239,047  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    79,684 |±    2,675  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |   959,825 |±  155,075  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   407,860 |±   40,823  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  9005,537 |± 1074,830  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  8725,481 |±  465,238  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1444,746 |±  184,615  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    24,278 |±    3,548  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |   896,611 |±   75,003  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |   903,216 |±  117,363  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 11598,874 |±  725,577  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    15,600 |±    1,076  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 15092,251 |±  973,451  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4435,355 |±  515,443  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    14,242 |±    0,949  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    20,983 |±    2,528  |us/op|

2026-01-04 Better gcd
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    22,378 |±    3,669  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    36,611 |±    0,490  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    39,480 |±    0,798  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    39,465 |±    1,031  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    40,640 |±    1,322  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    58,171 |±    0,919  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    91,978 |±    1,628  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    39,979 |±    0,465  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    37,154 |±    6,445  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1427,210 |±   40,260  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   505,098 |±   19,649  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   126,138 |±    5,292  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1335,104 |±   90,515  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    71,939 |±    3,450  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |   878,597 |±   17,242  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   366,042 |±   10,206  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  8057,862 |±  453,739  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  7928,951 |±  327,776  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1373,089 |±   59,619  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    21,298 |±    0,964  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |   854,978 |±   57,042  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |   856,311 |±   25,845  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 10553,881 |±  702,921  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    14,513 |±    0,566  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 14228,629 |±  749,989  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  3884,297 |± 1277,522  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,121 |±    1,456  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    19,095 |±    0,454  |us/op|

2026-03-15 Add selection sort benchmark
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    20,037 |±    3,004  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    35,616 |±    1,796  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    38,966 |±    1,524  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    38,863 |±    2,540  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    38,187 |±    0,976  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    54,213 |±    3,611  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    87,378 |±    7,769  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    38,855 |±    2,818  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    35,508 |±    1,070  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1509,173 |±  101,016  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   598,873 |±   88,294  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   139,390 |±   27,159  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1429,722 |±  232,181  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    79,441 |±    3,493  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |   899,035 |±   33,561  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   366,190 |±   13,493  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  8575,240 |± 2166,099  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  8023,967 |±  134,351  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1530,745 |±  343,620  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    24,956 |±    4,373  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |   940,733 |±   97,478  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |   952,479 |±   81,091  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 12318,080 |±  693,919  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    14,439 |±    1,211  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 14618,995 |± 4529,764  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  3890,489 |±  136,966  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,132 |±    0,683  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    18,994 |±    1,002  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    58,074 |±    2,950  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   562,377 |±   26,391  |us/op|

2026-03-16 Use ListStream instead of TailspinArray for streaming (tried StreamLibrary but that was slower)
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    18,580 |±    1,478  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    34,372 |±    2,195  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    35,648 |±    1,221  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    40,244 |±    5,076  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    39,461 |±    3,135  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    54,695 |±    1,382  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    89,235 |±    1,356  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    39,200 |±    1,202  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    43,874 |±    2,264  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1454,925 |±   56,434  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   538,368 |±   16,277  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   129,723 |±    3,742  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1504,096 |±  134,473  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    80,415 |±    2,169  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |   966,962 |±  106,919  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   416,423 |±   44,088  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  8653,025 |±  610,513  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  8332,668 |±  398,287  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1574,626 |±  198,990  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    23,377 |±    3,380  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |   929,614 |±   81,621  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |   893,503 |±   49,672  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 12021,245 |± 1685,991  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    15,801 |±    1,025  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 15660,035 |± 3161,771  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4298,786 |±  630,948  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    12,218 |±    0,853  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    21,377 |±    0,920  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    56,893 |±    1,842  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   495,438 |±   88,504  |us/op|

2026-04-04 Let ListStream aggregate results as well, remove ArrayList
Re-run 2026-05-31, no significant change
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    20,201 |±    4,168  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    35,470 |±    3,527  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    39,106 |±    3,463  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    38,205 |±    1,870  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    39,280 |±    3,740  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    61,906 |±   16,372  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    88,179 |±    3,464  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    40,626 |±    6,640  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    36,935 |±    2,257  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1788,980 |±  814,963  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   560,147 |±   16,973  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   149,910 |±   16,161  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1520,622 |±  116,191  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    82,706 |±   15,226  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |   896,480 |±   82,017  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   387,287 |±   28,049  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  8387,066 |±  432,295  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  7755,636 |±  677,065  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1388,247 |±   68,655  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    21,826 |±    1,573  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |   857,250 |±   50,586  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |   851,621 |±   18,399  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 10716,192 |±  231,565  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    14,853 |±    0,938  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 15725,698 |± 2440,888  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4567,570 |± 1359,653  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,101 |±    0,537  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    18,407 |±    1,329  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    46,373 |±    3,186  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   448,472 |±   31,420  |us/op|

2026-04-26 Handle interop streams everywhere
Re-run 2026-05-31
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    20,048 |±    4,896  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    36,935 |±    5,257  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    39,206 |±    4,791  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    40,062 |±    0,913  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    40,084 |±    1,951  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    57,793 |±    1,796  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    90,621 |±    5,815  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    38,307 |±    4,279  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    36,365 |±    1,055  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  2231,345 |±  185,250  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   783,826 |±   74,761  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   409,218 |±   21,403  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1925,922 |±  208,238  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    80,666 |±    8,704  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1654,206 |±  130,415  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   684,179 |±   74,065  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 | 10145,363 |±  802,934  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  9874,031 |±  639,525  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1904,541 |±  240,469  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    23,863 |±    1,521  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |  1376,468 |±   72,199  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  1199,453 |±  104,275  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 12203,430 |± 1637,868  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    16,138 |±    1,566  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 17010,533 |± 1755,736  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4927,805 |± 1566,602  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    12,592 |±    0,507  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    21,105 |±    4,473  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    59,327 |±    5,487  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   569,501 |±   81,755  |us/op|

2026-05-20 single-value tail-call flattening
Re-run 2026-05-31
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    18,931 |±    1,285  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    33,537 |±    1,559  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    35,536 |±    2,006  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    36,017 |±    2,243  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    37,551 |±    3,552  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    60,601 |±    7,351  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |    96,594 |±   16,475  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    40,954 |±   10,010  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    38,109 |±    3,684  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  2302,174 |±  460,644  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   841,991 |±   58,173  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   490,211 |±   66,146  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1781,498 |±  138,467  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    79,740 |±    5,330  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1479,261 |±  218,326  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   856,481 |±  109,660  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  9479,941 |± 1038,339  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  9812,520 |±  360,002  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1788,857 |±  118,360  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    23,511 |±    1,831  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |  5282,444 |± 1135,099  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  5277,575 |±  455,771  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 12382,921 |± 1599,948  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    15,671 |±    1,826  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 16121,408 |± 1708,567  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4653,413 |±  388,964  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,895 |±    0,821  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    21,701 |±    2,283  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    63,577 |±    7,389  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   579,751 |±   61,617  |us/op|

2026-05-20 tail call with state
Re-run 2026-05-31
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    18,567 |±    1,214  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    33,390 |±    1,871  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    35,451 |±    0,822  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    35,535 |±    1,916  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    31,754 |±    1,757  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    47,077 |±    2,146  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |   147,621 |±   10,991  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    31,741 |±    0,883  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    34,257 |±    1,276  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1918,555 |±  116,443  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   787,723 |±   41,061  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   386,534 |±   51,393  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1603,176 |±  185,301  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    73,732 |±    5,218  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1287,547 |±  100,831  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   604,358 |±   19,085  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  9043,485 |±  535,219  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  9552,995 |± 2005,616  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1770,928 |±  133,563  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    21,690 |±    2,135  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |  4793,501 |±  468,304  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  5072,986 |±  764,557  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 10776,481 |±  597,623  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    15,368 |±    0,602  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 15384,093 |± 4239,804  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4170,767 |± 1057,175  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,009 |±    0,464  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    23,118 |±    1,432  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    59,953 |±    2,743  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   480,912 |±   25,188  |us/op|

2026-05-24 fix bug: use range values instead of range iteration
Correctly run 2026-05-31
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    18,897 |±    1,183  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    65,188 |±    3,047  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    74,401 |±    3,659  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    70,715 |±    2,975  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    65,237 |±    8,501  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    81,738 |±    3,821  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |   148,969 |±    6,942  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    64,881 |±    6,520  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    34,578 |±    1,439  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  1903,183 |±   71,867  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   740,592 |±   32,003  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   440,226 |±   19,690  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1711,605 |±  124,116  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    74,275 |±    4,869  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1244,045 |±   41,441  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   623,911 |±   33,616  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  8682,267 |±  399,307  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  8520,298 |±  365,524  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1562,078 |±  127,207  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    21,688 |±    1,768  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |  5420,825 |±  463,785  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  5185,292 |±  517,272  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 10745,758 |±  546,829  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    14,759 |±    1,078  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 15209,920 |± 2923,479  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  3913,464 |±  346,053  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,267 |±    0,827  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    48,322 |±   25,038  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    58,567 |±    2,814  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   440,251 |±   65,887  |us/op|

2026-05-31 write array by relative index
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    20,031 |±    1,596  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    70,869 |±    6,004  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    82,724 |±   16,077  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    78,732 |±    9,692  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    71,535 |±   11,495  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    81,494 |±    3,896  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |   142,401 |±    5,845  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    67,206 |±    2,657  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    35,486 |±    1,229  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |   580,990 |±   47,653  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   736,930 |±   19,780  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   340,695 |±   15,056  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1699,237 |±   68,314  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    77,283 |±    7,722  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1153,099 |±   51,525  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   622,459 |±   25,930  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  1479,199 |±   47,262  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  9430,472 |± 2467,785  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1829,696 |±  116,873  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    24,191 |±    2,397  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |  1906,816 |± 1353,349  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  5579,755 |± 6983,664  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 12521,280 |± 1731,608  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    16,675 |±    1,562  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 16955,007 |± 2682,092  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  4657,142 |± 2289,284  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,478 |±    0,734  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    21,916 |±    1,144  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    47,195 |±    3,528  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   400,752 |±   12,936  |us/op|

2026-06-06 Resolve ranges in stride
Improves iteration, list benchmarks are all over the place, could probably improve ArrayLiteral creation
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    19,528 |±    0,533  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    59,131 |±    3,351  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    62,785 |±    4,692  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    62,074 |±    1,719  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    37,607 |±    1,175  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    51,210 |±    0,977  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |   123,309 |±    7,394  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    39,848 |±    5,202  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    37,338 |±    3,214  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  9344,749 |±  810,712  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |  1020,390 |±  166,682  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   480,562 |±   23,413  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1766,967 |±   91,430  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    77,316 |±    2,707  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1265,344 |±   57,000  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   683,614 |±   28,084  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  1518,237 |±   55,921  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  9085,529 |±  347,138  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1627,907 |±   63,886  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    22,806 |±    1,886  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |  3283,445 |± 6737,212  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  3585,651 |± 7533,132  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 11228,724 |±  277,150  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    15,247 |±    0,466  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 14653,011 |±  493,430  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  3338,732 |±  122,130  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,368 |±    0,313  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    44,327 |±    2,042  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    48,892 |±    0,933  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   460,492 |±   27,011  |us/op|

2026-06-07 flatten lens transform results
|Benchmark                                                      |Mode | Cnt |    Score  |   Error    |Units|
|---------------------------------------------------------------|-----|-----|-----------|------------|-----|
|BubblesortBenchmark.sort_java                                  |avgt |   5 |    19,357 |±    2,110  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate                      |avgt |   5 |    58,847 |±    3,269  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_measure              |avgt |   5 |    64,210 |±    6,790  |us/op|
|BubblesortBenchmark.sort_tailspin_iterate_tagged_long          |avgt |   5 |    62,009 |±    3,784  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse                      |avgt |   5 |    38,336 |±    2,674  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_full_precondition    |avgt |   5 |    51,153 |±    3,720  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_non_auxiliary        |avgt |   5 |   112,487 |±    5,287  |us/op|
|BubblesortBenchmark.sort_tailspin_recurse_simple_precondition  |avgt |   5 |    37,263 |±    3,261  |us/op|
|FibonacciBenchmark.recursive_java                              |avgt |   5 |    35,846 |±    1,652  |us/op|
|FibonacciBenchmark.recursive_tailspin_array_long               |avgt |   5 |  9450,090 |± 1002,912  |us/op|
|FibonacciBenchmark.recursive_tailspin_bignumber                |avgt |   5 |   886,183 |±   73,892  |us/op|
|FibonacciBenchmark.recursive_tailspin_measure_long             |avgt |   5 |   455,277 |±   36,429  |us/op|
|FibonacciBenchmark.recursive_tailspin_rational                 |avgt |   5 |  1818,337 |±  220,099  |us/op|
|FibonacciBenchmark.recursive_tailspin_raw_long                 |avgt |   5 |    75,730 |±    1,610  |us/op|
|FibonacciBenchmark.recursive_tailspin_scinum                   |avgt |   5 |  1240,434 |±   82,557  |us/op|
|FibonacciBenchmark.recursive_tailspin_small_scinum             |avgt |   5 |   664,815 |±   48,646  |us/op|
|FibonacciBenchmark.recursive_tailspin_structure_measure        |avgt |   5 |  1478,830 |±  157,706  |us/op|
|HilbertBenchmark.rational_tailspin                             |avgt |   5 |  8881,327 |±  530,139  |us/op|
|HilbertBenchmark.small_rational_tailspin                       |avgt |   5 |  1607,747 |±  141,599  |us/op|
|ListBenchmark.list_java                                        |avgt |   5 |    23,024 |±    2,173  |us/op|
|ListBenchmark.list_tailspin_empty                              |avgt |   5 |  1672,333 |±  237,907  |us/op|
|ListBenchmark.list_tailspin_optional                           |avgt |   5 |  3959,495 |± 7294,780  |us/op|
|NBodyBenchmark.nbody_java_big_decimal                          |avgt |   5 | 11037,378 |± 1384,673  |us/op|
|NBodyBenchmark.nbody_java_double                               |avgt |   5 |    15,120 |±    1,265  |us/op|
|NBodyBenchmark.nbody_tailspin_16digits                         |avgt |   5 | 16285,766 |± 2135,435  |us/op|
|NBodyBenchmark.nbody_tailspin_6digits                          |avgt |   5 |  3288,809 |±  296,777  |us/op|
|PascalBenchmark.triangle_java                                  |avgt |   5 |    11,547 |±    0,929  |us/op|
|PascalBenchmark.triangle_tailspin                              |avgt |   5 |    50,238 |±    9,574  |us/op|
|SelectionSortBenchmark.selection_sort_java                     |avgt |   5 |    59,969 |±    4,898  |us/op|
|SelectionSortBenchmark.selection_sort_tailspin                 |avgt |   5 |   445,519 |±   21,502  |us/op|
