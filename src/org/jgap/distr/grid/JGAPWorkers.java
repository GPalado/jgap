/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licencing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.distr.grid;

import org.apache.log4j.*;
import org.homedns.dade.jcgrid.*;
import org.homedns.dade.jcgrid.worker.*;
import org.apache.commons.cli.*;
import org.homedns.dade.jcgrid.cmd.MainCmd;

/**
 * A worker receives work units from a JGAPServer and sends back computed
 * solutions to a JGAPServer.
 *
 * @author Klaus Meffert
 * @since 3.01
 */
public class JGAPWorkers {
  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.2 $";

  private final static String className = JGAPWorkers.class.getName();

  private static Logger log = Logger.getLogger(className);

  public JGAPWorkers(GridNodeWorkerConfig a_config, Class a_workerClass,
                    Class a_workerFeedbackClaas)
      throws Exception {
    // Start all required workers.
    // ---------------------------
    GridWorker[] gw = new GridWorker[a_config.getWorkerCount()];
    for (int i = 0; i < a_config.getWorkerCount(); i++) {
      // Instantiate worker via reflection.
      // ----------------------------------
      gw[i] = new GridWorker();
      gw[i].setNodeConfig( (GridNodeGenericConfig) a_config.clone());
      ( (GridNodeGenericConfig) gw[i].getNodeConfig()).
          setSessionName(a_config.getSessionName() + "_" + i);
      ( (GridNodeGenericConfig) gw[i].getNodeConfig()).
          setWorkingDir(a_config.getWorkingDir() + "_" + i);
      Worker myWorker = (Worker) a_workerClass.newInstance();
      // Instantiate worker feedback.
      // ----------------------------
      GridWorkerFeedback myWorkerFeedback = (GridWorkerFeedback)
          a_workerFeedbackClaas.newInstance();
      gw[i].setWorker(myWorker);
      gw[i].setWorkerFeedback(myWorkerFeedback);
      // Start single worker.
      // --------------------
      gw[i].start();
    }
    // Wait for shutdown of workers.
    // -----------------------------
    for (int i = 0; i < a_config.getWorkerCount(); i++) {
      gw[i].waitShutdown();
    }
  }

  /**
   * Convenience method to start your workers. For possible parameters besides
   * the two mentioned below see method parseCommonOptions in class
   * org.homedns.dade.jcgrid.cmd.MainCmd. The most important parameters are:
   * -n <session Name without spaces>
   * -s <server IP address>
   * -d <working directory>
   *
   * @param args first parameter: name of your worker class (instance of
   * interface org.homedns.dade.jcgrid.worker.Worker), second parameter:
   * name of your worker feedback class (instance of interface
   * org.homedns.dade.jcgrid.worker.GridWorkerFeedback).
   * @throws Exception
   *
   * @author Klaus Meffert
   * @since 3.2
   */
  public static void main(String[] args)
      throws Exception {
    if (args.length < 2) {
      System.out.println("Provide at least two parameters with the first two:");
      System.out.println(
          "  1. Full name of your worker class (including package)");
      System.out.println(
          "  2. Full name of your worker feedback class (including package)");
      System.exit(1);
    }
    MainCmd.setUpLog4J("worker", true);
    GridNodeWorkerConfig config = new GridNodeWorkerConfig();
    Options options = new Options();
    CommandLine cmd = MainCmd.parseCommonOptions(options, config, args);
    Class workerClass = Class.forName(args[0]);
    Class workerFeedbackClass = Class.forName(args[1]);
    // Start worker(s).
    // ----------------
    new JGAPWorkers(config, workerClass, workerFeedbackClass);
  }
}
