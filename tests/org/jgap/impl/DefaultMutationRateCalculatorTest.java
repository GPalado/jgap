/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licencing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.impl;

import org.jgap.*;

import junit.framework.*;

/**
 * Tests for DefaultMutationRateCalculator class
 *
 * @author Klaus Meffert
 * @since 1.1
 */
public class DefaultMutationRateCalculatorTest
    extends JGAPTestCase {

  /** String containing the CVS revision. Read out via reflection!*/
  private static final String CVS_REVISION = "$Revision: 1.9 $";

  public static Test suite() {
    TestSuite suite = new TestSuite(DefaultMutationRateCalculatorTest.class);
    return suite;
  }

  /**
   *
   * @throws Exception
   * @author Klaus Meffert
   */
  public void testCalculateCurrentRate_0() throws Exception {
    Configuration conf = new DefaultConfiguration();
    Genotype.setConfiguration(conf);
    IUniversalRateCalculator calc = new DefaultMutationRateCalculator();
    Gene gene = new IntegerGene(1, 5);
    Chromosome chrom = new Chromosome(gene, 50);
    conf.setSampleChromosome(chrom);
    int rate = calc.calculateCurrentRate();
    assertEquals(conf.getChromosomeSize(), rate);
    chrom = new Chromosome(gene, 30);
    conf.setSampleChromosome(chrom);
    rate = calc.calculateCurrentRate();
    assertEquals(conf.getChromosomeSize(), rate);
  }

  /**
   * If there are zero chromosomes in the config., the mutation rate
   * nevertheless should be 1, because Random needs positive integers as input
   * (see MutationOperator.operate for calling Random class)
   * @throws Exception
   * @author Klaus Meffert
   */
  public void testCalculateCurrentRate_1() throws Exception {
    Configuration conf = new DefaultConfiguration();
    Genotype.setConfiguration(conf);
    IUniversalRateCalculator calc = new DefaultMutationRateCalculator();
    int rate = calc.calculateCurrentRate();
    assertEquals(1, rate);
  }
}
