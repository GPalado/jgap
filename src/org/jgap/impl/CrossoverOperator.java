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

/**
 * The crossover operator randomly selects two Chromosomes from the
 * population and "mates" them by randomly picking a gene and then
 * swapping that gene and all subsequent genes between the two
 * Chromosomes. The two modified Chromosomes are then added to the
 * list of candidate Chromosomes. 
 *
 * This CrossoverOperator supports both fixed and dynamic crossover rates.
 * A fixed rate is one specified at construction time by the user. This 
 * operation is performed 1/m_crossoverRate as many times as there are 
 * Chromosomes in the population. A dynamic rate is one determined by 
 * this class if no fixed rate is provided. 
 *
 * @author Neil Rotstan
 * @author Klaus Meffert
 * @since 1.0
 */
public class CrossoverOperator
    implements GeneticOperator {

  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.6 $";

   /**
   * The current crossover rate used by this crossover operator.
   */
  protected int m_crossoverRate;

  /**
   * Calculator for dynamically determining the crossover rate. If set to
   * null the value of m_crossoverRate will be used instead.
   */
  private IUniversalRateCalculator m_crossoverRateCalc;

   /**
   * Constructs a new instance of this CrossoverOperator without a specified
   * crossover rate, this results in dynamic crossover rate being turned off.
   * This means that the crossover rate will be fixed at populationsize\2.
   *
   * @author Chris Knowles
   * @since 2.0
   */
  public CrossoverOperator() {
      //set the default crossoverRate to be populationsize\2
      m_crossoverRate = 2;
      setCrossoverRateCalc(null);
  }

  /**
   * Constructs a new instance of this MutationOperator with a specified
   * crossover rate calculator, which results in dynamic crossover being turned
   * on.
   * @param a_crossoverRateCalculator calculator for dynamic crossover rate
   *        computation
   *
   * @author Chris Knowles
   * @since 2.0
   */
  public CrossoverOperator(IUniversalRateCalculator a_crossoverRateCalculator) {
    setCrossoverRateCalc(a_crossoverRateCalculator);
  }

  /**
   * Constructs a new instance of this CrossoverOperator with the given
   * crossover rate.
   *
   * @param a_desiredCrossoverRate The desired rate of crossover.
   *
   * @author Chris Knowles
   * @since 2.0
   */
  public CrossoverOperator(int a_desiredCrossoverRate) {
    m_crossoverRate = a_desiredCrossoverRate;
    setCrossoverRateCalc(null);
  }

  /**
   * The operate method will be invoked on each of the genetic operators
   * referenced by the current Configuration object during the evolution
   * phase. Operators are given an opportunity to run in the order that
   * they are added to the Configuration. Implementations of this method
   * may reference the population of Chromosomes as it was at the beginning
   * of the evolutionary phase and/or they may instead reference the
   * candidate Chromosomes, which are the results of prior genetic operators.
   * In either case, only Chromosomes added to the list of candidate
   * chromosomes will be considered for natural selection. Implementations
   * should never modify the original population, but should first make copies
   * of the Chromosomes selected for modification and operate upon the copies.
   *
   * @param a_population The population of chromosomes from the current
   *                     evolution prior to exposure to any genetic operators.
   *                     Chromosomes in this array should never be modified.
   * @param a_candidateChromosomes The pool of chromosomes that are candidates
   *                               for the next evolved population. Only these
   *                               chromosomes will go to the natural
   *                               phase, so it's important to add any
   *                               modified copies of Chromosomes to this
   *                               list if it's desired for them to be
   *                               considered for natural selection.
   * @author Neil Rotstan
   * @author Klaus Meffert
   * @since 2.0 (earlier versions referenced the Configuration object)
   */
  public void operate(final Population a_population,
                      final List a_candidateChromosomes) {
    
    // Work out the number of crossovers that should be performed
    int numCrossovers = 0;
    if (m_crossoverRateCalc == null){
        numCrossovers = a_population.size() / m_crossoverRate;
    }
    else{
        numCrossovers = a_population.size() / m_crossoverRateCalc.calculateCurrentRate();
    }
    
    RandomGenerator generator = Genotype.getConfiguration().getRandomGenerator();
    // For each crossover, grab two random chromosomes, pick a random
    // locus (gene location), and then swap that gene and all genes
    // to the "right" (those with greater loci) of that gene between
    // the two chromosomes.
    // --------------------------------------------------------------
    for (int i = 0; i < numCrossovers; i++) {
      Chromosome firstMate = (Chromosome)
          a_population.getChromosome(generator.nextInt(a_population.size())).clone();
      Chromosome secondMate = (Chromosome)
          a_population.getChromosome(generator.nextInt(a_population.size())).clone();
      Gene[] firstGenes = firstMate.getGenes();
      Gene[] secondGenes = secondMate.getGenes();
      int locus = generator.nextInt(firstGenes.length);
      // Swap the genes.
      // ---------------
      Object firstAllele;
      for (int j = locus; j < firstGenes.length; j++) {
        firstAllele = firstGenes[j].getAllele();
        firstGenes[j].setAllele(secondGenes[j].getAllele());
        secondGenes[j].setAllele(firstAllele);
      }
      // Add the modified chromosomes to the candidate pool so that
      // they'll be considered for natural selection during the next
      // phase of evolution.
      // -----------------------------------------------------------
      a_candidateChromosomes.add(firstMate);
      a_candidateChromosomes.add(secondMate);
    }
  }
  
  /**
   * Sets the crossover rate calculator
   * @param a_crossoverRateCalculator The new calculator
   *
   * @author Chris Knowles
   * @since 2.0
   */
  private void setCrossoverRateCalc(IUniversalRateCalculator a_crossoverRateCalculator){
      this.m_crossoverRateCalc = a_crossoverRateCalculator;
  }
}
