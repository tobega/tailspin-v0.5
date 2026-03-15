# Performance tests
## How to run
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
