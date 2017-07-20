Kodkod
=======

This repository includes the source code for the
[Kodkod](http://emina.github.io/kodkod/) solver for relational
logic.  Kodkod provides a clean Java [API](http://emina.github.io/kodkod/doc/) for constructing,
manipulating, and solving relational constraints. The
source code is extensively documented, and the repository includes
many [examples](https://github.com/emina/kodkod/tree/master/examples/kodkod/examples) demonstrating the use of the Kodkod API.

Kodkod is open-source and available under the [MIT license](LICENSE). However, the implementation relies on third-party SAT solvers ([SAT4J](http://www.sat4j.org), [MiniSat](http://minisat.se), [Glucose](http://www.labri.fr/perso/lsimon/glucose/), and [(P)Lingeling](http://fmv.jku.at/lingeling/)), some of which are released under stricter licenses. Please see the solver licenses for details.  

### Downloading Kodkod

The easiest way to get started is to [download](https://github.com/emina/kodkod/releases) the latest release, which includes precompiled binaries for Kodkod and various SAT solvers. You will need Java 8 running on Linux or Mac OS X.


### Building Kodkod

The following instructions for building Kodkod from source have been
tested on Linux (Fedora 22 with gcc 5.1.1) and on Mac OS X (10.10.5 with clang 6.0 and gcc 4.9). You may
need to modify the build scripts for other operating systems.

Kodkod uses the [Waf](https://github.com/waf-project/waf) build
system, which requires Python 2.5 or later. You will also need Java 8
and a C/C++ compiler, and your JAVA_HOME environment variable needs to
point to the JDK 8 home directory.

* Set the JAVA_HOME variable.  For example, on OS X:

  ``$ export JAVA_HOME=`/usr/libexec/java_home` ``

* Clone the kodkod repository:

  `$ git clone https://github.com/emina/kodkod.git`  
  `$ cd kodkod`  

* Download Waf 1.8.12 and make it executable:

  `$ wget --no-check-certificate https://waf.io/waf-1.8.12`  
  `$ chmod u+x waf-1.8.12`   
  `$ alias waf=$PWD/waf-1.8.12`

* Build the native libraries, ``kodkod.jar``, and ``examples.jar`` and install them into the ``kodkod/lib`` directory:

  `$ waf configure --prefix=. --libdir=lib build install`  

### Running Kodkod

[Download](#downloading-kodkod) or [build](#building-kodkod) the ``kodkod.jar`` binary, solver binaries, and the ``examples.jar`` binary. Assuming that the current working directory contains these binaries, run the  [Sudoku example](https://github.com/emina/kodkod/blob/master/examples/kodkod/examples/sudoku/Sudoku.java) as follows:

  `$ java -cp kodkod.jar:examples.jar -Djava.library.path=. kodkod.examples.sudoku.Sudoku`  

The program will produce a solution to a sample Sudoku puzzle:

```
p cnf 3452 7954
primary variables: 486
translation time: 176 ms
solving time: 2 ms
+-------+-------+-------+
| 6 4 7 | 2 1 3 | 9 5 8 |
| 9 1 8 | 5 6 4 | 7 2 3 |
| 2 5 3 | 8 7 9 | 4 6 1 |
+-------+-------+-------+
| 1 9 5 | 6 4 7 | 8 3 2 |
| 4 8 2 | 3 5 1 | 6 7 9 |
| 7 3 6 | 9 2 8 | 1 4 5 |
+-------+-------+-------+
| 5 7 4 | 1 9 2 | 3 8 6 |
| 8 2 9 | 7 3 6 | 5 1 4 |
| 3 6 1 | 4 8 5 | 2 9 7 |
+-------+-------+-------+
```
