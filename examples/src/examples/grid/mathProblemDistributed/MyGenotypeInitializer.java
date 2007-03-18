/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licencing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package examples.grid.mathProblemDistributed;

import org.jgap.distr.grid.*;
import org.jgap.distr.grid.gp.*;
import org.jgap.*;
import org.jgap.gp.impl.*;

/**
 * Initializes the genotype on behalf of the workers in a grid.
 *
 * @author Klaus Meffert
 * @since 3.2
 */
public class MyGenotypeInitializer
    implements IGenotypeInitializerGP {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.1 $";

  public GPGenotype setupGenotype(JGAPRequestGP a_req, GPPopulation a_initialPop)
      throws Exception {
    GPConfiguration conf = a_req.getConfiguration();
    GPPopulation pop;
    if (a_initialPop == null) {
      pop = new GPPopulation(conf, conf.getPopulationSize());
    }
    else {
      pop = a_initialPop;
    }
    int size = conf.getPopulationSize() - pop.size();
    GPGenotype result = null;/**@todo new GPGenotype(conf, pop, conf.get);*/
    result.fillPopulation(size);
    return result;
  }
}