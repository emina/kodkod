Release Notes
=============

### Kodkod 2.1 (September 20, 2015)

* The supported subset of bitvector arithmetic follows the  semantics defined by [SMTLIB-2](http://smtlib.cs.uiowa.edu/).

* Supports the following SAT solvers via the Java Native Interface:
	- Glucose 4.0 (library name 'glucose')
	- Lingeling ayv (library name 'lingeling')
	- MiniSat 2.2.0 (library name 'minisat')
	- MiniSat-P 1.14 (library name 'minisatprover')

* Supports the latest version of SAT4J (v20130525).

* Supports Plingeling ayv as an external (statically linked) solver.  Kodkod communicates with it by reading/writing
  files in DIMACS format.  It is loaded automatically, by searching 
  java.library.path for 'plingeling'.

* Includes a bug fix for the skolemizer.  Thanks to Aleks Milicevic for discovering and fixing the bug.


### Kodkod 2.0 (September 28, 2012)

* Includes support for incremental solving.  See [kodkod.engine.IncrementalSolver](https://github.com/emina/kodkod/blob/master/src/kodkod/engine/IncrementalSolver.java) for details.

* Supports the following SAT solvers via the Java Native Interface:
	- CryptoMiniSat 2.9.5 (library name 'cryptominisat')
	- Glucose 2.1 (library name 'glucose')
	- Lingeling 587f (library name 'lingeling')
	- MiniSat 2.2.0 (library name 'minisat')
	- MiniSat-P 1.14 (library name 'minisatprover')

* Supports the latest version of SAT4J (v20120709).

* Supports Plingeling 587f as an external library.  The program is 
  statically linked and the engine communicates with it by reading/writing
  files in DIMACS format.  It is loaded automatically, by searching 
  java.library.path for 'plingeling'.

* Includes a patch for minisat-p_v1.14/core/Proof.c to handle the addition of an 
  empty (conflict) clause by the client code.

* Drops support for unused features, including minimizing SAT solvers (zchaffmincost)
  and circuit flattening prior to CNF generation.

### Kodkod 1.5.2 (April 19, 2012)

* Fixed a bug in dot product computation.  Thanks to Marcelo Frias.

### Kodkod 1.5.1 (September 25, 2011)

* Fixed a bug in CryptoMiniSat bindings.  Thanks to Jasmin Blanchette.

* Fixed a bug in Lingeling bindings.  Thanks to Jasmin Blanchette.

### Kodkod 1.5 (September 24, 2011)

* Includes JNI bindings for the latest SAT solvers:
	- MiniSat 2.2.0 (library name 'minisat')
	- CryptoMiniSat 2.9.1 (library name 'cryptominisat')
	- Lingeling 276 (library name 'lingeling')

* Includes support for Plingeling 276 as an external library.  The program is
  statically linked and the engine communicates with it by reading/writing
  files in DIMACS format.  It is loaded automatically, by searching 
  java.library.path for 'plingeling'.

* Includes updated support for older solvers that perform well or have features
  not provided by newer solvers:
  	- MiniSat 1.14 (outperforms newer solvers on many benchmarks; library version suffix 1.14)
  	- MiniSatP 1.14 (provides proof logging; library name 'minisatprover')
  	- ZChaff Mincost (provides solution minimization; library name 'zchaffmincost')

* The JNI loading process can be configured to look for different versions of a
  given library in a specific order.  If the engine is unable to load the SAT 
  library 'satlib', it will query the system property 'kodkod.satlib' to obtain 
  a string of version suffixes, separated by the system-specific path-separator
  character (e.g., colon on Unix).  It will then try loading the versions of
  the library identified by concatenating the library name with each of the 
  suffixes in turn (e.g. 'satlib2.0').  The suffix property for a particular
  solver can be set at the command line via -Dkodkod.satlib="1.0:2.0:3.0".
  
* Fixed a bug in translation caching.  Thanks to Matvey Arye.

