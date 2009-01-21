/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.gp.function;

import org.apache.commons.lang.builder.*;
import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.impl.*;
import org.jgap.util.*;

/**
 * A connector for independent subprograms (subtrees). Each subtree except the
 * last one must have a memory- or stack-modifying command (such as push or
 * store), otherwise there is no connection between the subtrees (which would
 * be useless bloating).
 *
 * @author Klaus Meffert
 * @since 3.0
 */
public class SubProgram
    extends CommandGene implements ICloneable, IMutateable {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.16 $";

  /**
   * Number of subprograms. Redundant, because equal to m_types.length.
   */
  private int m_subtrees;

  /**
   * Return types of the subprograms to excecute.
   */
  private Class[] m_types;

  private boolean m_mutateable;

  public SubProgram(final GPConfiguration a_conf, Class[] a_types)
      throws InvalidConfigurationException {
    this(a_conf, a_types, 0, null);
  }

  /**
   * Collage constructor: Create a sub program that has a_arity elements
   * of the same type a_types.
   *
   * @param a_conf the configuration to use
   * @param a_arity number of children in the collage
   * @param a_types uniform type of all children
   * @throws org.jgap.InvalidConfigurationException
   *
 * @author Klaus Meffert
 * @since 3.4
   */
  public SubProgram(final GPConfiguration a_conf, int a_arity, Class a_types)
      throws InvalidConfigurationException {
    this(a_conf, a_arity, a_types, false);
  }

  /**
   * Collage constructor: Create a sub program that has a_arity elements
   * of the same type a_types.
   *
   * @param a_conf the configuration to use
   * @param a_arity number of children in the collage
   * @param a_types uniform type of all children
   * @param a_mutateable true: allow mutation of the sub program, i.e., the
   * number of children (=arity) may be varied automatically during evolution
   * @throws org.jgap.InvalidConfigurationException
   *
 * @author Klaus Meffert
 * @since 3.4
   */
  public SubProgram(final GPConfiguration a_conf, int a_arity, Class a_types,
          boolean a_mutateable)
      throws InvalidConfigurationException {
    super(a_conf, a_arity, a_types, 0, null);
    if (a_arity < 1) {
      throw new IllegalArgumentException("Arity must be >= 1");
    }
    m_types = new Class[a_arity];
    for(int i=0;i<a_arity;i++) {
      m_types[i] = a_types;
    }
    m_subtrees = a_arity;
    m_mutateable = a_mutateable;
  }

  public SubProgram(final GPConfiguration a_conf, Class[] a_types,
                    boolean a_mutateable)
      throws InvalidConfigurationException {
    this(a_conf, a_types, 0, null, a_mutateable);
  }

  public SubProgram(final GPConfiguration a_conf, Class[] a_types,
                    int a_subReturnType, int[] a_subChildTypes)
      throws InvalidConfigurationException {
    this(a_conf, a_types, a_subReturnType, a_subChildTypes, false);
  }

  public SubProgram(final GPConfiguration a_conf, Class[] a_types,
                    int a_subReturnType, int[] a_subChildTypes, boolean
                    a_mutateable)
      throws InvalidConfigurationException {
    super(a_conf, a_types.length, a_types[a_types.length - 1], a_subReturnType,
          a_subChildTypes);
    if (a_types.length < 1) {
      throw new IllegalArgumentException("Number of subtrees must be >= 1");
    }
    m_types = a_types;
    m_subtrees = a_types.length;
    m_mutateable = a_mutateable;
  }

  public String toString() {
    String ret = "sub[";
    for (int i = 1; i < m_subtrees; i++) {
      ret += "&" + i + " --> ";
    }
    ret += "&" + m_subtrees + "]";
    return ret;
  }

  /**
   * @return textual name of this command
   *
   * @author Klaus Meffert
   * @since 3.2
   */
  public String getName() {
    return "Sub program";
  }

  public int execute_int(ProgramChromosome c, int n, Object[] args) {
    check(c);
    int value = -1;
    for (int i = 0; i < m_subtrees; i++) {
      if (i < m_subtrees - 1) {
        c.execute_void(n, i, args);
      }
      else {
        value = c.execute_int(n, i, args); /**@todo evaluate m_types*/
      }
//      if (i < m_subtrees - 1) {
//        ( (GPConfiguration) getConfiguration()).storeThruput(i,
//            new Integer(value));
//      }
    }
    return value;
  }

  public void execute_void(ProgramChromosome c, int n, Object[] args) {
    check(c);
    for (int i = 0; i < m_subtrees; i++) {
      c.execute_void(n, i, args); /**@todo evaluate m_types*/
    }
  }

  public long execute_long(ProgramChromosome c, int n, Object[] args) {
    check(c);
    long value = -1;
    for (int i = 0; i < m_subtrees; i++) {
      value = c.execute_long(n, i, args);
    }
    return value;
  }

  public float execute_float(ProgramChromosome c, int n, Object[] args) {
    check(c);
    float value = -1;
    for (int i = 0; i < m_subtrees; i++) {
      value = c.execute_float(n, i, args);
    }
    return value;
  }

  public double execute_double(ProgramChromosome c, int n, Object[] args) {
    check(c);
    double value = -1;
    for (int i = 0; i < m_subtrees; i++) {
      value = c.execute_double(n, i, args);
    }
    return value;
  }

  public Object execute_object(ProgramChromosome c, int n, Object[] args) {
    check(c);
    Object value = null;
    for (int i = 0; i < m_subtrees; i++) {
      value = c.execute_object(n, i, args);
    }
    return value;
  }

  public boolean isValid(ProgramChromosome a_program) {
    return true;
  }

  public Class getChildType(IGPProgram a_ind, int a_chromNum) {
    try {
      return m_types[a_chromNum];
    } catch (ArrayIndexOutOfBoundsException aex) {
      return null;
    }
  }

  /**
   * The compareTo-method.
   *
   * @param a_other the other object to compare
   * @return -1, 0, 1
   *
   * @author Klaus Meffert
   * @since 3.0
   */
  public int compareTo(Object a_other) {
    if (a_other == null) {
      return 1;
    }
    else {
      SubProgram other = (SubProgram) a_other;
      return new CompareToBuilder()
          .append(m_types, other.m_types)
          .toComparison();
    }
  }

  /**
   * The equals-method.
   *
   * @param a_other the other object to compare
   * @return true if the objects are seen as equal
   *
   * @author Klaus Meffert
   * @since 3.0
   */
  public boolean equals(Object a_other) {
    if (a_other == null) {
      return false;
    }
    else {
      try {
        SubProgram other = (SubProgram) a_other;
        return new EqualsBuilder()
            .append(m_types, other.m_types)
            .isEquals();
      } catch (ClassCastException cex) {
        return false;
      }
    }
  }

  /**
   * @return deep clone of this instance
   *
   * @author Klaus Meffert
   * @since 3.2
   */
  public Object clone() {
    try {
      int[] subChildTypes = getSubChildTypes();
      if (subChildTypes != null) {
        subChildTypes = (int[]) subChildTypes.clone();
      }
      SubProgram result = new SubProgram(getGPConfiguration(), m_types,
          getSubReturnType(), subChildTypes);
      result.m_subtrees = m_subtrees;
      result.m_types = (Class[]) m_types.clone();
      return result;
    } catch (Throwable t) {
      throw new CloneException(t);
    }
  }

  public CommandGene applyMutation(int index, double a_percentage)
      throws InvalidConfigurationException {
    if (!m_mutateable) {
      return this;
    }
    org.jgap.RandomGenerator randomGen = getGPConfiguration().
        getRandomGenerator();
    double random = randomGen.nextDouble();
    if (random < a_percentage) {
      return applyMutation();
    }
    return this;
  }

  /**@todo use dynamizeArity instead!*/
  public CommandGene applyMutation()
      throws InvalidConfigurationException {
    int[] subChildTypes = getSubChildTypes();
    if (subChildTypes != null) {
      subChildTypes = (int[]) subChildTypes.clone();
    }
    int size = getGPConfiguration().getRandomGenerator().nextInt(7) + 2;
    Class[] types = new Class[size];
    for (int i = 0; i < size; i++) {
      types[i] = m_types[m_types.length - 1];
    }
    SubProgram result = new SubProgram(getGPConfiguration(), types,
                                       getSubReturnType(), subChildTypes);
    return result;
  }

  /**
   * Adaptation of the arity so that it represents a value within the interval
   * [m_arityMin, m_arityMax].
   *
   * @author Klaus Meffert
   * @since 3.4
   */
//  public void dynamizeArity() {
//    int arity = 3
//        + getGPConfiguration().getRandomGenerator().nextInt(15 - 3 + 1);
//    setArity(arity);
//    Class atype = m_types[0];
//    m_types = new Class[arity];
//    for (int i = 0; i < arity; i++) {
//      m_types[i] = atype;
//    }
//    m_subtrees = arity;
//  }
}
