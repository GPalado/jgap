/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licencing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.supergenes;

import java.util.*;
import org.jgap.*;
import org.jgap.impl.*;
import junit.framework.*;

/**
 * Test persistent representation of the AbstractSupergene.
 *
 * @author Meskauskas Audrius
 * @since 2.0
 */
public class SupergenePersistentRepresentationTest
    extends JGAPTestCase {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.11 $";

  public static Test suite() {
    TestSuite suite =
        new TestSuite(SupergenePersistentRepresentationTest.class);
    return suite;
  }

  public static class InstantiableSupergene
      extends AbstractSupergene {
    public InstantiableSupergene(final Configuration a_config)
        throws InvalidConfigurationException {
      super(a_config, new Gene[] {});
    }

    public InstantiableSupergene()
        throws InvalidConfigurationException {
      super();
    }

    public boolean isValid(Gene[] a_gene) {
      return true;
    };
  }
  public void testRepresentation()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    Gene i1 = new IntegerGene(conf, 1, 12);
    Gene i2 = new DoubleGene(conf, 3, 4);
    i1.setAllele(new Integer(7));
    i2.setAllele(new Double(3.2));
    gene.addGene(i1);
    gene.addGene(i2);
    InstantiableSupergene nested = new InstantiableSupergene(conf);
    Gene n1 = new IntegerGene(conf, 1, 12);
    Gene n2 = new DoubleGene(conf, 3, 4);
    n1.setAllele(new Integer(5));
    n2.setAllele(new Double(3.6));
    nested.addGene(n1);
    nested.addGene(n2);
    gene.addGene(nested);
    InstantiableSupergene nested2 = new InstantiableSupergene(conf);
    nested2.setValidator(new TestValidator(conf));
    Gene nn1 = new IntegerGene(conf, 1, 1000);
    Gene nn2 = new DoubleGene(conf, 0, 1000);
    nn1.setAllele(new Integer(22));
    nn2.setAllele(new Double(44));
    nested2.addGene(nn1);
    nested2.addGene(nn2);
    gene.addGene(nested2);
    InstantiableSupergene nested3 = new InstantiableSupergene(conf);
    nested3.setValidator(null);
    Gene nnn1 = new IntegerGene(conf, 1, 1000);
    Gene nnn2 = new DoubleGene(conf, 0, 1000);
    nnn1.setAllele(new Integer(555));
    nnn2.setAllele(new Double(777));
    nested3.addGene(nnn1);
    nested3.addGene(nnn2);
    nested2.addGene(nested3);
    String representation =
        gene.getPersistentRepresentation();
    InstantiableSupergene restored = new InstantiableSupergene(conf);
    restored.setValueFromPersistentRepresentation(representation);
    assertTrue(gene.equals(restored));
  }

  public void testValidatorPers_0() {
    Validator val = new TestValidator(conf);
    String pers = val.getPersistent();
    val.setFromPersistent(null);
    assertSame(pers, val.getPersistent());
    assertSame(conf, val.getConfiguration());
  }

  public void testGetGenes_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    assertEquals(0, gene.getGenes().length);
    Gene subGene = new BooleanGene(conf);
    gene.addGene(subGene);
    assertEquals(1, gene.getGenes().length);
    assertSame(subGene, gene.geneAt(0));
  }

  public void testSize_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    assertEquals(0, gene.size());
    Gene subGene = new BooleanGene(conf);
    gene.addGene(subGene);
    assertEquals(1, gene.size());
  }

  public void testIsValid_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    gene.m_validator = null;
    assertTrue(gene.isValid());
    gene.m_validator = gene;
    try {
      assertTrue(gene.isValid());
      fail();
    }
    catch (Error e) {
      ; //this is OK
    }
  }

  public void testReset_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    /**@todo care that m_immutable is filled*/
    gene.reset();
    Set[] m = (Set[]) privateAccessor.getField(gene, "m_immutable");
    assertEquals(1, m.length);
  }

  public void testPers_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    try {
      gene.setValueFromPersistentRepresentation(null);
      fail();
    }
    catch (UnsupportedRepresentationException uex) {
      ; //this is OK
    }
  }

  public void testToString_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    gene.m_validator = null;
    String s = gene.toString();
    assertEquals("Supergene " + gene.getClass().getName()
                 + " {"
                 + " non validating"
                 + "}"
                 , s);
  }

  public void testToString_1()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    gene.m_validator = gene;
    String s = gene.toString();
    assertEquals("Supergene " + gene.getClass().getName()
                 + " {"
                 + " validator: " + gene.getClass().getName()
                 + "}"
                 , s);
  }

  public void testToString_2()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    Gene bgene = new BooleanGene(conf);
    gene.addGene(bgene);
    gene.m_validator = gene;
    String s = gene.toString();
    assertEquals("Supergene " + gene.getClass().getName()
                 + " {|"
                 + bgene.toString()
                 + "|"
                 + " validator: " + gene.getClass().getName()
                 + "}"
                 , s);
  }

  public void testCompareTo_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    assertEquals(0, gene.compareTo(gene));
    InstantiableSupergene gene2 = new InstantiableSupergene(conf);
    assertEquals(0, gene.compareTo(gene2));
  }

  public void testCompareTo_1()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    try {
      gene.compareTo(new Vector());
      fail();
    }
    catch (ClassCastException ex) {
      ; //this is OK
    }
  }

  public void testCompareTo_2()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    InstantiableSupergene gene2 = new InstantiableSupergene(conf);
    gene2.addGene(new BooleanGene(conf));
    assertEquals( -1, gene.compareTo(gene2));
    assertEquals(1, gene2.compareTo(gene));
  }

  public void testCompareTo_3()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    AbstractSupergene gene2 = new TestClass(conf);
    assertTrue(gene.compareTo(gene2) != 0);
    assertTrue(gene2.compareTo(gene) != 0);
    gene2.addGene(new BooleanGene(conf));
    assertEquals( -1, gene.compareTo(gene2));
    assertEquals(1, gene2.compareTo(gene));
  }

  public void testEquals_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    assertEquals(0, gene.compareTo(gene));
    assertFalse(gene.equals(null));
    assertFalse(gene.equals(new Vector()));
    InstantiableSupergene gene2 = new InstantiableSupergene(conf);
    assertTrue(gene.equals(gene2));
  }

  public void testNewGene_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    gene.m_validator = null;
    InstantiableSupergene gene2 = (InstantiableSupergene) gene.newGene();
    assertEquals(gene, gene2);
    assertEquals(gene2, gene);
  }

  public void testNewGene_1()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    for (int i = 0; i < 5; i++) {
      Gene aGene = new DoubleGene(conf);
      gene.addGene(aGene);
    }
    InstantiableSupergene gene2 = (InstantiableSupergene) gene.newGene();
    assertEquals(gene, gene2);
    assertEquals(gene2, gene);
  }

  public void testNewGene_2()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    gene.m_validator = gene;
    InstantiableSupergene gene2 = (InstantiableSupergene) gene.newGene();
    assertEquals(gene, gene2);
    assertEquals(gene2, gene);
  }

  public void testSetAllele_0()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    gene.setAllele(new Double(23.4));
    assertEquals(0, gene.size());
  }

  public void testSetAllele_1()
      throws Exception {
    InstantiableSupergene gene = new InstantiableSupergene(conf);
    for (int i = 0; i < 5; i++) {
      Gene aGene = new DoubleGene(conf);
      gene.addGene(aGene);
    }
    gene.setAllele(new Double(23.569));
    for (int i = 0; i < 5; i++) {
      DoubleGene aGene = new DoubleGene(conf);
      assertEquals(23.569, aGene.doubleValue(), DELTA);
    }
  }

  public class TestValidator
      extends Validator {
    public TestValidator(final Configuration a_conf) {
      super(a_conf);
    }

    public boolean isValid(Gene[] a_gene, Supergene a_supergene) {
      return true;
    }
  }
  class TestClass
      extends AbstractSupergene {
    public TestClass(final Configuration a_conf)
        throws InvalidConfigurationException {
      super(a_conf, new Gene[] {});
    }

    public boolean isValid(Gene[] a) {
      throw new Error("Should never be called.");
    }

    protected Gene newGeneInternal() {
      throw new Error("Should never be called.");
    }
  }
}
