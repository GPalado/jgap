/*
 * This file is part of JGAP.
 *
 * JGAP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * JGAP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser Public License
 * along with JGAP; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.jgap.impl;

import java.util.*;
import org.jgap.*;
import junit.framework.*;
import junitx.util.*;

/**
 * Tests for IntegerGene class
 *
 * @author Klaus Meffert
 * @since 1.1
 */
public class IntegerGeneTest
    extends TestCase {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.10 $";

  public IntegerGeneTest() {
  }

  public void setUp() {
    Genotype.setConfiguration(null);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(IntegerGeneTest.class);
    return suite;
  }

  public void testConstruct_0() {
    Gene gene = new IntegerGene(1, 100);
    //following should be possible without exception
    gene.setAllele(new Integer(101));
  }

  public void testToString_0() {
    Gene gene = new IntegerGene(1, 100);
    gene.setAllele(new Integer(47));
    assertEquals("47", gene.toString());
  }

  public void testToString_1() {
    Gene gene = new IntegerGene(1, 100);
    gene.setAllele(new Integer(102));
    int toString = Integer.parseInt(gene.toString());
    assertTrue(toString >= 1 && toString <= 100);
  }

  public void testGetAllele_0() {
    Gene gene = new IntegerGene(1, 100);
    gene.setAllele(new Integer(33));
    assertEquals(new Integer(33), gene.getAllele());
  }

  public void testGetAllele_1() {
    Gene gene = new IntegerGene(1, 100);
    gene.setAllele(new Integer(1));
    assertEquals(new Integer(1), gene.getAllele());
  }

  public void testGetAllele_2() {
    Gene gene = new IntegerGene(1, 100);
    gene.setAllele(new Integer(100));
    assertEquals(new Integer(100), gene.getAllele());
  }

  public void testEquals_0() {
    Gene gene1 = new IntegerGene(1, 100);
    Gene gene2 = new IntegerGene(1, 100);
    assertTrue(gene1.equals(gene2));
    assertTrue(gene2.equals(gene1));
  }

  public void testEquals_1() {
    Gene gene1 = new IntegerGene(1, 100);
    assertFalse(gene1.equals(null));
  }

  public void testEquals_2() {
    Gene gene1 = new IntegerGene(1, 100);
    assertFalse(gene1.equals(new BooleanGene()));
  }

  public void testEquals_3() {
    Gene gene1 = new IntegerGene(1, 100);
    assertFalse(gene1.equals(new Vector()));
  }

  public void testEquals_4() {
    Gene gene1 = new IntegerGene(1, 100);
    Gene gene2 = new IntegerGene(1, 99);
    assertTrue(gene1.equals(gene2));
    assertTrue(gene2.equals(gene1));
  }

  public void testIntValue_0() {
    IntegerGene gene1 = new IntegerGene(1, 10000);
    gene1.setAllele(new Integer(4711));
    assertEquals(4711, gene1.intValue());
  }

  public void testIntValue_1() {
    IntegerGene gene1 = new IntegerGene(1, 10000);
    gene1.setAllele(null);
    try {
      assertEquals(0, gene1.intValue());
      fail();
    }
    catch (NullPointerException nullex) {
      ; //this is OK
    }
  }

  /**
   * Set Allele to null, no exception should occur
   */
  public void testSetAllele_0() {
    Gene gene1 = new IntegerGene(1, 10000);
    gene1.setAllele(null);
  }

  public void testSetAllele_1() {
    Gene gene1 = new IntegerGene(1, 10000);
    try {
      gene1.setAllele("22");
      fail();
    }
    catch (ClassCastException classex) {
      ; //this is OK
    }
  }

  public void testNewGene_0()
      throws Exception {
    Gene gene1 = new IntegerGene(1, 10000);
    gene1.setAllele(new Integer(4711));
    Integer lower1 = (Integer) PrivateAccessor.getField(gene1,
        "m_lowerBounds");
    Integer upper1 = (Integer) PrivateAccessor.getField(gene1,
        "m_upperBounds");
    Gene gene2 = gene1.newGene();
    Integer lower2 = (Integer) PrivateAccessor.getField(gene2,
        "m_lowerBounds");
    Integer upper2 = (Integer) PrivateAccessor.getField(gene2,
        "m_upperBounds");
    assertEquals(lower1, lower2);
    assertEquals(upper1, upper2);
  }

  public void testCleanup() {
    //cleanup should do nothing!
    Gene gene = new IntegerGene(1, 6);
    Gene copy = gene.newGene();
    gene.cleanup();
    assertEquals(copy, gene);
  }

  public void testPersistentRepresentation_0()
      throws Exception {
    Gene gene1 = new IntegerGene(2, 753);
    gene1.setAllele(new Integer(45));
    String pres1 = gene1.getPersistentRepresentation();
    Gene gene2 = new IntegerGene();
    gene2.setValueFromPersistentRepresentation(pres1);
    String pres2 = gene2.getPersistentRepresentation();
    assertEquals(pres1, pres2);
  }

  public void testCompareToNative_0() {
    Gene gene1 = new IntegerGene(13, 65);
    gene1.setAllele(new Integer(58));
    Gene gene2 = new IntegerGene(53, 67);
    gene2.setAllele(new Integer(59));
    assertEquals( ( (Integer) gene1.getAllele()).compareTo(gene2.
        getAllele()), gene1.compareTo(gene2));
  }

  public void testCompareToNative_1() {
    Gene gene1 = new IntegerGene(13, 65);
    gene1.setAllele(new Integer(58));
    Gene gene2 = new IntegerGene(53, 67);
    gene2.setAllele(new Integer(58));
    assertEquals( ( (Integer) gene1.getAllele()).compareTo(gene2.
        getAllele()), gene1.compareTo(gene2));
  }

  public void testCompareToNative_2() {
    Gene gene1 = new IntegerGene(13, 65);
    gene1.setAllele(new Integer(59));
    Gene gene2 = new IntegerGene(53, 67);
    gene2.setAllele(new Integer(58));
    assertEquals( ( (Integer) gene1.getAllele()).compareTo(gene2.
        getAllele()), gene1.compareTo(gene2));
  }

  public void testCompareToNative_3() {
    Gene gene1 = new IntegerGene(13, 65);
    gene1.setAllele(new Integer(59));
    Gene gene2 = new IntegerGene(53, 67);
    gene2.setAllele(new Integer( -59));
    assertEquals( ( (Integer) gene1.getAllele()).compareTo(gene2.
        getAllele()), gene1.compareTo(gene2));
  }

  public void testCompareToNative_4() {
    Gene gene1 = new IntegerGene(13, 65);
    gene1.setAllele(new Integer(0));
    Gene gene2 = new IntegerGene(53, 67);
    gene2.setAllele(new Integer( -0));
    assertEquals( ( (Integer) gene1.getAllele()).compareTo(gene2.
        getAllele()), gene1.compareTo(gene2));
  }

  public void testApplyMutation_0() {
    IntegerGene gene = new IntegerGene(0, 100);
    gene.setAllele(new Integer(50));
    gene.applyMutation(0, 0.0d);
    assertEquals(50, gene.intValue());
  }

  public void testApplyMutation_1()
      throws Exception {
    DefaultConfiguration config = new DefaultConfiguration();
    config.setRandomGenerator(new RandomGeneratorForTest(15));
    Genotype.setConfiguration(config);
    IntegerGene gene = new IntegerGene(0, 100);
    gene.setAllele(new Integer(50));
    gene.applyMutation(0, 0.5d);
    assertEquals(Math.round(50 + (100 - 0) * 0.5d), gene.intValue());
  }

  public void testApplyMutation_2()
      throws Exception {
    DefaultConfiguration config = new DefaultConfiguration();
    config.setRandomGenerator(new RandomGeneratorForTest(15));
    Genotype.setConfiguration(config);
    IntegerGene gene = new IntegerGene(44, 100);
    gene.setAllele(new Integer(50));
    gene.applyMutation(0, 0.3d);
    assertEquals(Math.round(50 + (100 - 44) * 0.3d), gene.intValue());
  }

  public void testApplyMutation_3()
      throws Exception {
    DefaultConfiguration config = new DefaultConfiguration();
    config.setRandomGenerator(new RandomGeneratorForTest(15));
    Genotype.setConfiguration(config);
    IntegerGene gene = new IntegerGene(33, 100);
    gene.setAllele(new Integer(50));
    gene.applyMutation(0, 1.9d);
    assertEquals(Math.round(33 + 15), gene.intValue());
  }

  public void testApplyMutation_4()
      throws Exception {
    DefaultConfiguration config = new DefaultConfiguration();
    config.setRandomGenerator(new RandomGeneratorForTest(15));
    Genotype.setConfiguration(config);
    IntegerGene gene = new IntegerGene(2, 100);
    gene.setAllele(new Integer(60));
    gene.applyMutation(0, 1.9d);
    assertEquals(Math.round(2 + 15), gene.intValue());
  }

  public void testApplyMutation_5()
      throws Exception {
    DefaultConfiguration config = new DefaultConfiguration();
    config.setRandomGenerator(new RandomGeneratorForTest(15));
    Genotype.setConfiguration(config);
    IntegerGene gene = new IntegerGene(0, 100);
    gene.setAllele(new Integer(60));
    gene.applyMutation(0, -1.0d);
    assertEquals(Math.round(0 + 15), gene.intValue());
  }

  public void testApplyMutation_6() {
    IntegerGene gene = new IntegerGene(0, 100);
    gene.setAllele(new Integer(60));
    gene.applyMutation(0, -0.4d);
    assertEquals(Math.round(60 + (100 * ( -0.4d))), gene.intValue());
  }

  public void testSetToRandomValue_0() {
    Gene gene = new IntegerGene(1, 6);
    gene.setAllele(new Integer(5));

    gene.setToRandomValue(new RandomGeneratorForTest(0.2d));
    assertEquals(new Integer((int)(0.2d * (6 - 1) + 1)), gene.getAllele());
  }

  public void testSetToRandomValue_1() throws Exception {
    Gene gene = new IntegerGene( -1, 7);
    gene.setAllele(new Integer(4));

    Configuration conf = new DefaultConfiguration();
    Genotype.setConfiguration(conf);

    gene.setToRandomValue(new RandomGeneratorForTest(0.3d));
    assertEquals(new Integer((int)(0.3d * (7 + 1) - 1)), gene.getAllele());
  }

   public void testSetToRandomValue_2() throws Exception {
     Gene gene = new IntegerGene( -2, -1);
     gene.setAllele(new Integer(4));

     Configuration conf = new DefaultConfiguration();
     Genotype.setConfiguration(conf);

     gene.setToRandomValue(new RandomGeneratorForTest(0.8d));
     assertEquals(new Integer((int)(0.8d * ( -1 + 2) - 2)), gene.getAllele());
  }

  public void testSetToRandomValue_3(){
    IntegerGene gene = new IntegerGene(0, 8);
    gene.setAllele(new Integer(5));
    gene.setToRandomValue(new RandomGeneratorForTest(4));

    if (gene.intValue() < 0 ||
        gene.intValue() > 8) {
      fail();
    }
  }

  public void testSetToRandomValue_4(){
    IntegerGene gene = new IntegerGene(1, 6);
    gene.setAllele(new Integer(2));
    gene.setToRandomValue(new RandomGeneratorForTest(3));

    if (gene.intValue() < 1 ||
        gene.intValue() > 6) {
      fail();
    }
  }
}
