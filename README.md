Kodkod
=======

This repository includes the source code for the
[Kodkod](http://alloy.mit.edu/kodkod/index.html) solver for relational
logic.  Kodkod provides a clean Java [API](http://alloy.mit.edu/kodkod/release/doc/) for constructing,
manipulating, and solving relational constraints. The
source code is extensively documented, and the repository includes
many [examples](https://github.com/emina/kodkod/tree/master/examples/kodkod/examples) demonstrating the use of the Kodkod API.

Kodkod is open-source and available under the [MIT license](LICENSE). However, the implementation relies on third-party SAT solvers ([SAT4J](http://www.sat4j.org), [MiniSAT](http://minisat.se), [Glucose](http://www.labri.fr/perso/lsimon/glucose/), and [(P)Lingeling](http://fmv.jku.at/lingeling/)), some of which are released under stricter licenses. Please see the solver licenses for details.  

### Building and installing Kodkod

The following compilation and installation instructions have been
tested on Linux (Fedora 22) and on Mac OS X (10.10.5). You may
need to modify the build scripts for other operating systems.

Kodkod uses the [Waf](https://github.com/waf-project/waf) build
system, which requires Python 2.5 or later. You will also need Java 8
and a C/C++ compiler, and your JAVA_HOME environment variable needs to
point to the JDK 8 home directory.

* Clone the kodkod repository:

  `$ git clone https://github.com/emina/kodkod.git`  
  `$ cd kodkod`  

* Download Waf 1.8.12:

  `$ wget --no-check-certificate https://waf.io/waf-1.8.12`  
  `$ mv waf-1.8.12 waf`  
  `$ chmod 755 waf`  
  `$ alias waf=$PWD/waf`

* Build the native libraries, `kodkod.jar`, and `examples.jar` and install them into
  the `kodkod/lib` directory:

  `$ waf configure --prefix=. --libdir=lib build install`  

	
* Solve an example problem:

  `$ java -cp lib/kodkod.jar:lib/examples.jar -Djava.library.path=lib kodkod.examples.sudoku.Sudoku`
 






