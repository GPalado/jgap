package org.jgap.distr.grid.gp;

import org.homedns.dade.jcgrid.message.*;
import org.jgap.*;
import org.jgap.event.*;
import org.jgap.gp.*;
import org.jgap.gp.impl.*;
import org.jgap.gp.terminal.*;
import org.jgap.impl.*;

import com.thoughtworks.xstream.*;

/**
 * <p> </p>
 *
 * <p> </p>
 *
 * <p> </p>
 *
 * <p> </p>
 *
 * @author Klaus Meffert
 * @since 4.0
 */
public class JGAPGPXStream
    extends XStream {
  public JGAPGPXStream() {
    super();
    init(this);
  }

  protected void init(XStream a_xstream) {
    // Use aliases to reduce storage capacity.
    // ---------------------------------------
    a_xstream.alias("conf", Configuration.class);
    a_xstream.alias("gpconf", GPConfiguration.class);
    a_xstream.alias("gppop", GPPopulation.class);
    a_xstream.alias("gpprgbase", GPProgramBase.class);
    a_xstream.alias("gpprg", GPProgram.class);
    a_xstream.alias("basegpchrom", BaseGPChromosome.class);
    a_xstream.alias("prgchrom", ProgramChromosome.class);
    a_xstream.alias("cgene", CommandGene.class);
    a_xstream.alias("requestgp", JGAPRequestGP.class);
    a_xstream.alias("resultgp", JGAPResultGP.class);
    a_xstream.alias("factory", JGAPFactory.class);
    a_xstream.alias("stockrandomgen", StockRandomGenerator.class);
    a_xstream.alias("branchxover", BranchTypingCross.class);
    a_xstream.alias("defclonehandler", DefaultCloneHandler.class);
    a_xstream.alias("defcomphandler", DefaultCompareToHandler.class);
    a_xstream.alias("definit", DefaultInitializer.class);
    a_xstream.alias("gridmessworkreq", GridMessageWorkRequest.class);
    a_xstream.alias("gridmessworkres", GridMessageWorkResult.class);

    a_xstream.alias("eventman", EventManager.class);
    a_xstream.alias("defgpfiteval", DefaultGPFitnessEvaluator.class);
    a_xstream.alias("gpfitfunc", GPFitnessFunction.class);
    a_xstream.alias("tournsel", org.jgap.gp.impl.TournamentSelector.class);
    a_xstream.alias("void", java.lang.Void.class);
    a_xstream.alias("Jbool", java.lang.Boolean.class);
    a_xstream.alias("Jint", java.lang.Integer.class);
    a_xstream.alias("Jdouble", java.lang.Double.class);
    a_xstream.alias("Jfloat", java.lang.Float.class);
    a_xstream.alias("random", java.util.Random.class);
    a_xstream.alias("nop", NOP.class);
  }
}
