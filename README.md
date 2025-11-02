# tailspin-v0.5
Reworking syntax and reimplementing [Tailspin from v0](https://github.com/tobega/tailspin-v0/tree/master) to be better [based on concepts](CONCEPTS.md).
Also aiming for better performance and incremental parsing for creating a REPL and a language server.

Examples of programs with updated syntax in the [samples](samples) folder.

Great thanks to Adam Ruka for his [Truffle tutorial](https://www.endoflineblog.com/graal-truffle-tutorial-part-0-what-is-truffle)

## Status update 2025-11-02
I have now figured out mostly how I will [handle errors](https://tobega.blogspot.com/2025/08/exploring-error-handling-concepts-for.html),
which unblocks my further work.

## Breaking changes
### Names first and more words
- Names are more important, and need to be more easily scannable, than the type of statement, so names come first, the statement type keyword second.
- Words are more searchable and scannable than symbols, so the following words are introduced:
  - `is` for definitions, so `def foo: 5;` will be `foo is 5;`
  - `set` for mutations, so `@: 7;` will be `@ set 7;`
  - The word `matches` is introduced for conditions, `?($foo matches <'a.*'>)` instead of `?($foo <'a.*'>)`
  - `data` constructs are replaced by the word `requires` between the identifier and the matcher (the word `local` is not used)
  - As mentioned below, the anonymous inline templates will just use `templates ... end` instead of the weird `\( ... \)`
  - Also, indexed array templates will be handled by the new harmonized projection syntax, see below

### Harmonizing projections
- Projections will get a clear and general transform step at the end, marked by `->`, e.g. `$(5; -> 0)` will select the 5th element of the array and then transform it to `0` (OK, this example ends up being useless, but it is simple and shows the syntax). The advantage of the "inside" transform is that overlying structure can be kept.
- Since there will be an arrow (`->`) in the projection transform step, it makes sense to just use regular `$` instead of `§`
- The current value that a lens applies to is made accessible as $.
- The special array context words `first` and `last` are removed, just use range without specific bound, e.g. `..`, `~..` or `2..~`. If needed, use `$::first` and `$::last`.
- `..` will be introduced as a short form of `first..last` (and will also work as an *all* selector for non-indexed collections like relations)
- As a consequence, the structure transform projection, e.g. `$({x:, y: §.y + 1"1"})` will become `$(-> {x:, y: $.y + 1"1"})` for a structure value, equivalent to `$ -> {x:, y: $.y + 1"1"}`, or `$(..; -> {x:, y: $.y + 1"1"})` for a collection of structures.
- Array templates will be folded in as projections, so `$ -> \[i]($ * $i! \)` will be written instead `$(.. as i; -> $ * $i)`
- The magic property of the structure transform projection to apply no matter how many array dimensions down, will disappear. Maybe it will be enabled by an explicit `;;`
- The `.` syntax to access fields is removed, so it has to be `$record(field:)` instead of `$record.field`

### Functions (templates and similar things)
- The `#` for sending to matchers is just a transform call and must be followed by an emit `!` to emit or could be followed by more chained transforms.
- Inline templates will no longer be written as `\(......\)`, instead just `templates ........ end`. This unifies syntax and opens up for inline composer, etc.
- Probably an underscore `_` will be introduced as a way to reference a transform without applying it, so passing templates as parameters would be prefixed by `_`
- Since `#` is a transform call, I will experimentally try to disable named recursion. All recursion will then have to be on matchers. This makes sense from a usability perspective.
- Multipliers for array contant and composers come before instead of after, so `?<=2>`, not `<=2>?`

### Matchers
- In structure matchers, just a key is sufficient to denote existence (no `<>` needed), so `when <{name:}>` instead of `when <{name: <>}>`
- Also the first (or only) option must start with a `|`, e.g. `when <|=5>`, mainly because `when <=5>` makes a confusing arrow-like symbol accidentally

### Miscellaneous
- Line comments will start with `--` instead of `//` because it just looks lighter and cleaner
- Key-values will have to be wrapped in a structure, so `{ foo: 1 }` instead of an independent `(foo:1)`, this just removes an unnecessary special case
- Type bounds are listed outside the angle brackets, e.g. `´|""´<|=5"s">`
- Array start indexes are given as `&0[]` instead of `0:[]`, to work better together with type assertions (below)
- Tagged values will be printed with their tags.
- It will not be possible to create ranges of tagged values, since they cannot be manipulated mathematically. Instead, do `1..5 -> tag´$`. It will still be possible to do a range comparison, `<|tag´1..tag´5>`.
- Multiplication and division of measures by raw numbers is not allowed, it needs to be a scalar.
- The mod operator on measures needs to have the same measure on both sides
- Dividing by the same measure gives a scalar.

## New features
- Syntax sugar for a filter expression, `\(<.....> $! \)` can be written as `if <.....>`
- Assert a single value from a chain by surrounding with parentheses, `($source -> filter)`, previously only for arithmetic value
- Type assertions by tagging values, e.g. `foo´{bar: 5}` checks that the structure-literal is of type `foo`
- There is now a divide operator `/` that creates rational numbers (exact math)
- Scientific numbers with a specified number of digits precision (inexact math) can also be created and used.

### Contracts
- Preconditions specified by an optional `requires <.....>` at the top of a templates definition.
- Postconditions specified by an optional `produces <.....>` at the top of a templates definition.
  The `produces` clause can be more than one matcher (comma-separated), have multipliers prepended or
  define sequences, all as for array content matchers.
- Contract failures are generally hard failures (crashes), but precondition failures can be converted
  to no-results by putting the `try` keyword in front of the templates call (or maybe any transform?).
  A `try` should probably only be able to catch precondition failures for the immediate call.
- Sometimes it may not be entirely possible to cover all precondition checks in a matcher, so a `!REJECT`
  directive can be used to reject the input when a flaw is discovered

### Transactionality
- A failed precondition check caught by a `try` will roll back any mutations done in that transform

### Composers (WIP)
- It should be possible to use a composer as a composer matcher.
- Inline composers
- It should be possible to create custom composers as Processors. Tentative interface:
   - ::parse that emits the remaining text if successful, nothing otherwise
   - ::undo that undoes the action and emits the previous input text (or should it be backtrack that tries alternative?)
   - ::result that streams out the results

### A "nothing" code path (WIP)
It is already possible to handle a nothing code path by wrapping in a list and checking if it is empty or not.
`[$ -> maybe-nothing] -> templates when <|[](=0)> do ... nothing-path ... otherwise ... end`

I think I want to enable another type of when-clause, `when any maybe-nothing do ... not-nothing ... otherwise ... nothing-path ...`

## Performance check
See the [performance tests](src/jmh/README.md) for how Tailspin performs relative to java
