/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licencing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.gp.function;

import org.jgap.*;
import org.jgap.gp.*;
import org.jgap.gp.impl.*;

/**
 * The Lesser Than (x < y) operation.
 *
 * @author Klaus Meffert
 * @since 3.2
 */
public class LesserThan
    extends MathCommand {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.1 $";

  private Class m_type;

  public LesserThan(final GPConfiguration a_conf, Class a_type)
      throws InvalidConfigurationException {
    this(a_conf, a_type, 0, null);
  }

  public LesserThan(final GPConfiguration a_conf, Class a_type,
                    int a_subReturnType,
                    int[] a_subChildTypes)
      throws InvalidConfigurationException {
    super(a_conf, 2, CommandGene.BooleanClass, a_subReturnType, a_subChildTypes);
    m_type = a_type;
  }

  public String toString() {
    return "&1 < &2";
  }

  /**
   * @return textual name of this command
   *
   * @author Klaus Meffert
   * @since 3.2
   */
  public String getName() {
    return "LesserThan";
  }

  public boolean execute_boolean(ProgramChromosome c, int n, Object[] args) {
    if (m_type == CommandGene.BooleanClass) {
      return!c.execute_boolean(n, 0, args) && c.execute_boolean(n, 1, args);
    }
    else if (m_type == CommandGene.IntegerClass) {
      return c.execute_int(n, 0, args) < c.execute_int(n, 1, args);
    }
    else if (m_type == CommandGene.LongClass) {
      return c.execute_long(n, 0, args) < c.execute_long(n, 1, args);
    }
    else if (m_type == CommandGene.DoubleClass) {
      return c.execute_double(n, 0, args) < c.execute_double(n, 1, args);
    }
    else if (m_type == CommandGene.FloatClass) {
      return c.execute_float(n, 0, args) < c.execute_float(n, 1, args);
    }
//    else if (m_type == CommandGene.VoidClass) {
//      // We could support Comparables
//      return c.execute_object(n, 0, args).equals(c.execute_object(n, 1, args));
//    }
    throw new UnsupportedOperationException("Unsupported type " + m_type +
        " for LesserThan-command!");
  }

  public Class getChildType(IGPProgram a_ind, int a_index) {
    return m_type;
  }
}