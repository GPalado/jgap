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

import java.util.*;

import org.jgap.*;
import junit.framework.*;

/**
 * Tests for CompositeGene class
 *
 * @author Klaus Meffert
 * @since 1.1
 */
public class CompositeGeneTest
    extends TestCase {

  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.14 $";

  //delta for distinguishing whether a value is to be interpreted as zero
  private static final double DELTA = 0.000001d;

  public CompositeGeneTest() {
  }

  public static Test suite() {
    TestSuite suite = new TestSuite(CompositeGeneTest.class);
    return suite;
  }

  public void testConstruct_0() {
    //following should be possible without exception
    Gene gene = new CompositeGene();
  }

   /**
    * Blocked.
    * @todo Unblock if the nested composite genes are not yet properly
    * supported
    * @author Audrius Meskauskas (of blocking this test).
    * */
  public void testAddGene_0() {
    CompositeGene gene = new CompositeGene();
    try {
      gene.addGene(new CompositeGene(), false);
      // fail(); // uncomment this to remove the block
    }
    catch (IllegalArgumentException iex) {
      ; //this is OK
    }
  }

  public void testAddGene_1() {
    CompositeGene gene = new CompositeGene();
    Gene newGene = new DoubleGene();
    gene.addGene(newGene, false);
    assertEquals(1, gene.size());
    gene.addGene(new DoubleGene(1.2d, 3.4d), false);
    assertEquals(2, gene.size());
    //try to remove a non existent gene
    gene.removeGeneByIdentity(new DoubleGene());
    assertEquals(2, gene.size());
    //try to add an already existent gene
    try {
      gene.addGene(newGene, false);
      fail();
    }
    catch (IllegalArgumentException iex) {
      ; //this is OK
    }
  }

  public void testToString_0() {
    CompositeGene gene = new CompositeGene();
    Gene newGene1 = new DoubleGene();
    newGene1.setAllele(new Double(47.123d));
    gene.addGene(newGene1, false);
    assertEquals("("+newGene1.toString()+")", gene.toString());
    Gene newGene2 = new IntegerGene();
    newGene2.setAllele(new Integer(23456));
    gene.addGene(newGene2, false);
    assertEquals("("+newGene1.toString() + gene.GENE_DELIMITER +
                 newGene2.toString()+")", gene.toString());
  }

  public void testGetAllele_0() {
    Gene gene = new CompositeGene();
    //this should be possible without exception
    gene.setAllele(new Vector());
  }

  public void testGetAllele_1() {
    CompositeGene gene = new CompositeGene();
    Gene newGene1 = new IntegerGene(1, 5);
    gene.addGene(newGene1, false);
    Vector v = new Vector();
    v.add(new Integer(4));
    gene.setAllele(v);
    v = (Vector) gene.getAllele();
    assertEquals(newGene1.getAllele(), v.elementAt(0));
    assertEquals(1, v.size());
  }

  public void testGetAllele_2() {
    CompositeGene gene = new CompositeGene();
    Gene newGene1 = new IntegerGene(1, 5);
    gene.addGene(newGene1, false);
    Gene newGene2 = new DoubleGene(77.2d, 999.0d);
    gene.addGene(newGene2, false);
    Vector v = new Vector();
    v.add(new Integer(4));
    v.add(new Double(333.5d));
    gene.setAllele(v);
    v = (Vector) gene.getAllele();
    assertEquals(newGene1.getAllele(), v.elementAt(0));
    assertEquals(newGene2.getAllele(), v.elementAt(1));
    assertEquals(2, v.size());
  }

  public void testEquals_0() {
    Gene gene1 = new CompositeGene();
    Gene gene2 = new CompositeGene();
    assertTrue(gene1.equals(gene2));
    assertTrue(gene2.equals(gene1));
  }

  public void testEquals_1() {
    Gene gene1 = new CompositeGene();
    assertFalse(gene1.equals(null));
  }

  public void testEquals_2() {
    Gene gene1 = new CompositeGene();
    assertFalse(gene1.equals(new BooleanGene()));
  }

  public void testEquals_3() {
    Gene gene1 = new CompositeGene();
    assertFalse(gene1.equals(new Vector()));
  }

  public void testEquals_4() {
    CompositeGene gene1 = new CompositeGene();
    gene1.addGene(null, false);
    CompositeGene gene2 = new CompositeGene();
    gene2.addGene(null, false);
    assertTrue(gene1.equals(gene2));
    assertTrue(gene2.equals(gene1));
  }

  public void testEquals_5() {
    CompositeGene gene1 = new CompositeGene();
    Gene newGene1 = new IntegerGene(3, 5);
    gene1.addGene(newGene1, false);
    CompositeGene gene2 = new CompositeGene();
    gene2.addGene(newGene1, false);
    assertTrue(gene1.equals(gene2));
    assertTrue(gene2.equals(gene1));
  }

  public void testEquals_6() {
    CompositeGene gene1 = new CompositeGene();
    Gene newGene1 = new IntegerGene(3, 5);
    gene1.addGene(newGene1, false);
    Gene newGene2 = new IntegerGene(7, 9);
    CompositeGene gene2 = new CompositeGene();
    gene2.addGene(newGene2, false);
    assertEquals(newGene1.equals(newGene2), gene1.equals(gene2));
    assertEquals(newGene2.equals(newGene1), gene2.equals(gene1));
  }

  public void testEquals_7() {
    CompositeGene gene1 = new CompositeGene();
    Gene newGene1 = new IntegerGene(3, 5);
    gene1.addGene(newGene1, false);
    Gene newGene2 = new DoubleGene(7.2d, 59.4d);
    CompositeGene gene2 = new CompositeGene();
    gene2.addGene(newGene2, false);
    assertEquals(newGene1.equals(newGene2), gene1.equals(gene2));
    assertEquals(newGene2.equals(newGene1), gene2.equals(gene1));
  }

  public void testSetAllele_0() {
    Gene gene = new CompositeGene();
    try {
      gene.setAllele(null);
      fail();
    }
    catch (IllegalArgumentException iex) {
      ; //this is OK
    }
  }

  public void testSetAllele_1() {
    Gene gene = new CompositeGene();
    try {
      gene.setAllele(new Double(2.3d));
      fail();
    }
    catch (IllegalArgumentException iex) {
      ; //this is OK
    }
  }

  /**
   * Set Allele to empty Vector, no exception should occur
   */
  public void testSetAllele_2() {
    Gene gene1 = new CompositeGene();
    gene1.setAllele(new Vector());
  }

  public void testSetAllele_3() {
    Gene gene1 = new CompositeGene();
    try {
      gene1.setAllele("22");
      fail();
    }
    catch (IllegalArgumentException classex) {
      ; //this is OK
    }
  }

  public void testNewGene_0() throws Exception {
    CompositeGene gene1 = new CompositeGene();
    gene1.addGene(new DoubleGene(2.05d, 7.53d), false);
    gene1.addGene(new DoubleGene(128.35d, 155.90d), false);
    gene1.addGene(new IntegerGene(3, 8), false);
    gene1.addGene(new BooleanGene(), false);
    gene1.addGene(new StringGene(), false);
    gene1.addGene(new StringGene(2, 5), false);
    gene1.addGene(new StringGene(6, 11, "ABC"), false);
    CompositeGene gene2 = (CompositeGene) gene1.newGene();
    assertTrue(gene1.equals(gene2));
    //Remove all genes from gene2 that are contained in gene1.
    //Because they should be equal, gene2 should then be empty.
    //---------------------------------------------------------
    for (int i = 0; i < gene1.size(); i++) {
      gene2.removeGene(gene1.geneAt(i));
    }
    assertEquals(0, gene2.size());
  }

  public void testPersistentPresentation_0() throws Exception {
    CompositeGene gene1 = new CompositeGene();
    Gene gene0 = new DoubleGene(2.05d, 7.53d);
    gene0.setAllele(new Double(7.52d));
    gene1.addGene(gene0, false);
    gene1.addGene(new DoubleGene(128.35d, 155.90d), false);
    gene0 = new IntegerGene(3, 8);
    gene0.setAllele(new Integer(5));
    gene1.addGene(gene0, false);
    gene0 = new BooleanGene();
    gene0.setAllele(new Boolean(true));
    gene1.addGene(gene0, false);
    gene1.addGene(new StringGene(), false);
    gene1.addGene(new StringGene(2, 5), false);
    gene0 = new StringGene(6, 11, ":ABC"); // using ':'
    gene0.setAllele("B:BCBCCA");
    gene1.addGene(gene0, false);
    String pres1 = gene1.getPersistentRepresentation();
    CompositeGene gene2 = new CompositeGene();
    gene2.setValueFromPersistentRepresentation(pres1);
    String pres2 = gene2.getPersistentRepresentation();
    assertEquals(pres1, pres2);
  }

  /**
   * Test a nested persistent representation, including strings
   * @throws Exception
   *
   * @author Audrius Meskauskas
   */
  public void testPersistentPresentation_1() throws Exception {
    CompositeGene composite1 = new CompositeGene();
    Gene strgene = new DoubleGene(2.05d, 7.53d);
    strgene.setAllele(new Double(7.52d));

    composite1.addGene(strgene, false);
    composite1.addGene(new DoubleGene(128.35d, 155.90d), false);
    strgene = new IntegerGene(3, 8);
    strgene.setAllele(new Integer(5));
    composite1.addGene(strgene, false);
    strgene = new BooleanGene();
    strgene.setAllele(new Boolean(true));

    composite1.addGene(strgene, false);
    composite1.addGene(new StringGene(), false);
    composite1.addGene(new StringGene(2, 5), false);

    String string = "<!-- many:various:chars &%$�/()=<>C:CA/ -->";
    strgene = new StringGene(6, 50, "CA! many:various:chars<>:&%$�/()-=");
    strgene.setAllele(string);

    // remember where, we will check the value later
    int stringPosition = composite1.size();
    composite1.addGene(strgene, false);


    CompositeGene compositeInside = new CompositeGene();

    Gene istrgene = new DoubleGene(2.05d, 17.53d);
    istrgene.setAllele(new Double(3.33));
    compositeInside.addGene(istrgene, false);

    compositeInside.addGene(new DoubleGene(128.35d, 155.90d), false);
    istrgene = new IntegerGene(3, 8);
    istrgene.setAllele(new Integer(5));
    compositeInside.addGene(istrgene, false);
    istrgene = new BooleanGene();
    istrgene.setAllele(new Boolean(true));
    compositeInside.addGene(istrgene, false);
    compositeInside.addGene(new StringGene(), false);
    compositeInside.addGene(new StringGene(2, 5), false);
    istrgene = new StringGene(6, 11, "&xyzab<:>");

    String string2 = "<:yzab:>";
    istrgene.setAllele(string2);
    int position2 = compositeInside.size();
    compositeInside.addGene(istrgene, false);

    int whereCompositeGene = composite1.size();
    composite1.addGene( compositeInside );

    String pres1 = composite1.getPersistentRepresentation();
    CompositeGene gene2 = new CompositeGene();

    gene2.setValueFromPersistentRepresentation(pres1);
    String pres2 = gene2.getPersistentRepresentation();

    assertEquals(pres1, pres2);

    // check the string
    StringGene s = (StringGene) gene2.geneAt(stringPosition);
    assertTrue ( string.equals(s.getAllele()));

    // check also in the composite gene
    CompositeGene cg = (CompositeGene) gene2.geneAt(whereCompositeGene);
    s = (StringGene) cg.geneAt(position2);

    assertTrue (string2.equals(s.getAllele()));
  }
 }

