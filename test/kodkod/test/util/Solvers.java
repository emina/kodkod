/* 
 * Kodkod -- Copyright (c) 2005-present, Emina Torlak
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package kodkod.test.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import kodkod.engine.satlab.SATFactory;

/**
 * Detects which solvers are available on the system
 * and returns their factories.
 * 
 * @author Emina Torlak
 */
public class Solvers {
	
	/**
	 * Returns a list view of all solver factories available for use on this system.
	 * @return a list view of all solver factories available for use on this system.
	 */
	public static final List<SATFactory> allAvailableSolvers() {
		final List<SATFactory> ret = new ArrayList<>();
		for(Field f : SATFactory.class.getFields()) {
			try {
				 final SATFactory factory = (SATFactory) f.get(null);
				 if (SATFactory.available(factory))
					 ret.add(factory);
			} catch (Exception e) {
				continue;
			}  
		}
		for(Method m : SATFactory.class.getDeclaredMethods()) {
			if (!Modifier.isPublic(m.getModifiers())) continue;
			try {
				final SATFactory factory = (SATFactory) m.invoke(null);
				if (SATFactory.available(factory))
					 ret.add(factory);
			} catch (Exception e) {
				continue;
			}
		}
		return ret;
	}
	
	/**
	 * Prints the list of all solvers found on this system.
	 */
	public static void main(String[] args) { 
		System.out.println(allAvailableSolvers().toString());
	}
	
}
