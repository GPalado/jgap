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

/**
 * The and operation.
 *
 * @author Klaus Meffert
 * @since 3.0
 */
public class And
    extends MathCommand {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.1 $";

  public And(final GPConfiguration a_conf)
      throws InvalidConfigurationException {
    super(a_conf, 2, CommandGene.BooleanClass);
  }

  protected CommandGene newGeneInternal() {
    try {
      CommandGene gene = new And(getGPConfiguration());
      return gene;
    } catch (InvalidConfigurationException iex) {
      throw new IllegalStateException(iex.getMessage());
    }
  }

  public String toString() {
    return "&1 && &2";
  }

  public boolean execute_boolean(ProgramChromosome c, int n, Object[] args) {
    return c.execute_boolean(n, 0, args) && c.execute_boolean(n, 1, args);
  }

}