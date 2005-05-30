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
 * Tests for ChromosomePool class
 *
 * @author Klaus Meffert
 * @since 2.0
 */
public class ChromosomePoolTest
    extends JGAPTestCase {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.5 $";

  public static Test suite() {
    TestSuite suite = new TestSuite(ChromosomePoolTest.class);
    return suite;
  }

  /**
   * Test if construction possible without failure
   *
   * @author Klaus Meffert
   * @since 2.0
   */
  public void testConstruct_0() {
    new ChromosomePool();
  }

  /**
   * @author Klaus Meffert
   * @since 2.0
   */
  public void testAquireChromosome_0() {
    assertEquals(null, new ChromosomePool().acquireChromosome());
  }

  /**
   * @author Klaus Meffert
   * @since 2.0
   */
  public void testReleaseChromosome_0() {
    try {
      new ChromosomePool().releaseChromosome(null);
      fail();
    }
    catch (NullPointerException nex) {
      ; //this is OK
    }
  }

  /**
   *
   * @throws Exception
   *
   * @author Klaus Meffert
   * @since 2.0
   */
  public void testReleaseChromosome_1()
      throws Exception {
    ChromosomePool pool = new ChromosomePool();
    Configuration conf = new DefaultConfiguration();
    conf.setFitnessFunction(new TestFitnessFunction());
    conf.setPopulationSize(5);
    Gene sampleGene = new IntegerGene(1, 10);
    Chromosome chrom = new Chromosome(sampleGene, 3);
    conf.setSampleChromosome(chrom);
    pool.releaseChromosome(chrom);
  }
}
