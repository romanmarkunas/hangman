# Architecture
Project architecture has 4 layers, similar to Onion architecture. Main idea of architecture is dependency inversion,
that is the inner layer knows nothing about outer layer.
## 1) Domain - the innermost layer. Contains business wide data structures, which are usually stored in databases. In
this project domain contains words, which might be used in any word game.
