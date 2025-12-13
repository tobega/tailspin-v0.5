# Concurrency in Tailspin

The basic idea is to have transactional memory access.

Entry to a templates is considered a checkpoint to which state rolls back if the transaction fails.

Since this may lead to very fine-grained transactions on transactions,
ways are needed to coarsen transactions. Possibilities:
- A `commit` keyword before a templates call inhibits a new checkpoint.
- A templates might be labelled as `auxiliary` to make it always work in the same transaction.

