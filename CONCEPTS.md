# Concepts, design principles and their relation to Tailspin features
Mostly based on [my blogpost on concepts and programming language usability](https://tobega.blogspot.com/2024/01/usability-in-programming-language.html)

## General design principles, in priority order
1. Tailspin programming structures shall strive to be a visual representation of the desired result, as far as possible. 
2. There is a clear value in representing constructs intended for different purposes in visibly different ways.
3. There is also a clear value in having things that have similar purpose be structured the same way.
4. Tailspin code distinguishes between immutable, serializable data on one hand and functions and mutable objects on the other. 
5. The default behaviour shall be consistent with sound programming, "trickier" code shall be more work and be as explicit as possible. 
6. Tailspin shall fail hard if it seems like the program might be erroneous. 
7. Tailspin should be slightly helpfully annoying to force the programmer to communicate intent clearly, if possible to do without too much extra work for the programmer. 
8. Programming structures shall have a clear beginning and end.

## Identified concepts
- Repetition - Purpose: Allows repeating similar operations with slight variation. Operational Principle: If you provide the parts that vary, the algorithm is repeated with those variations.
- Aggregation - Purpose: Collects separate related units into one unit. Operational Principle: If you provide the parts and specify how they relate to each other, an aggregate unit is created.
- Projection - Purpose: Creates a view of parts of an aggregate. Operational Principle: If you specify the location(s) within the aggregate you want to access, those parts are extracted into a smaller aggregate (or single value)
- Selection - Purpose: Allows a choice of which units to process and which to ignore. Operational Principle: If you specify the selection criteria, the matching units will be processed.
- Verification - Purpose: Allows checking correctness criteria of the program. Operational Principle: If you specify a criterion and run the verification procedure (which may be built in), a warning/failure is issued if the program does not fulfil the criterion.
- Documentation - Purpose: Allows communicating aspects of the design and purpose to a future reader of the program. Operational Principle: If you specify the relevant information, a future reader will receive it when needed.

## Some features examined
### Pipeline processing
Fulfils the repetition concept by applying the same transforms to multiple values.
Creates a visual representation of the steps involved in the processing.

### Streams of data, expanding and contracting
Allows the pipeline steps to show how each value is transformed more clearly without worrying about a container object.
Allowing transform steps to output a stream of values for each input value further enhances repetition and also fulfils
the selection concept by stopping processing when the stream is empty.
