/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licencing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.impl.job;

import java.io.*;

/**
 * Interface for jobs of any kind.
 *
 * @author Klaus Meffert
 * @since 3.2
 */
public interface IJob
    extends Serializable {
  /** String containing the CVS revision. Read out via reflection!*/
  final static String CVS_REVISION = "$Revision: 1.1 $";

  /**
   * Executes a job.
   *
   * @param a_data generic input parameters, as needed
   * @throws Exception in case of any error
   */
  void execute(Object a_data)
      throws Exception;
}