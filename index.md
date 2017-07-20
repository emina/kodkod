---
title: "About"
---

## About Kodkod

Kodkod is an efficient [SAT-based constraint solver][] for first order logic with relations, transitive closure, bit-vector arithmetic, and partial models. It provides automated reasoning facilities for both satisfiable and unsatisfiable problems: a [finite model finder][] for the former and a [minimal unsatisfiable core extractor][] for the latter. Kodkod is used in a wide range of [applications][], including code checking, test-case generation, declarative execution, declarative configuration, and lightweight analysis of Alloy, UML, and Isabelle/HOL.

Designed as a plugin component that can be easily incorporated into other tools, Kodkod provides a clean Java interface for constructing, manipulating, and solving constraints. The implementation is open-source and available for [download][] under the [MIT license][]. The source code is extensively documented, and the distribution includes many [examples][] demonstrating the use of the [Kodkod API][].


[SAT-based constraint solver]: http://homes.cs.washington.edu/~emina/pubs/kodkod.phd.pdf
[finite model finder]: http://homes.cs.washington.edu/~emina/pubs/kodkod.tacas07.pdf
[minimal unsatisfiable core extractor]: http://homes.cs.washington.edu/~emina/pubs/kodkod.fm08.pdf
[applications]: apps.html
[download]: https://github.com/emina/kodkod
[MIT license]: http://www.opensource.org/licenses/mit-license.php
[examples]: https://github.com/emina/kodkod/tree/master/examples/kodkod/examples
[Kodkod API]: doc/
