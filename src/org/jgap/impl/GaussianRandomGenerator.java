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

/**
 * Gaussian deviation serving as basis for randomly finding a number.
 *
 * @author Klaus Meffert
 * @since 1.1
 */
public class GaussianRandomGenerator
    implements RandomGenerator {

  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.14 $";

  //delta for distinguishing whether a value is to be interpreted as zero
  private static final double DELTA = 0.0000001;

  private Random rn;

  /**
   * Mean of the gaussian deviation
   */
  private double m_mean;

  /**
   * Standard deviation of the gaussian deviation
   */
  private double m_standardDeviation;

  public GaussianRandomGenerator() {
    this(1.0d);
  }

  /**
   * Constructor speicifying the (obliagtory) standard deviation
   * @param a_standardDeviation the standard deviation to use
   */
  public GaussianRandomGenerator(double a_standardDeviation) {
    super();
    init();
    setGaussianStdDeviation(a_standardDeviation);
  }

  /**
   * Initializations on construction
   */
  private void init() {
    rn = new Random();
  }

  public void setGaussianMean(double a_mean) {
    m_mean = a_mean;
  }

  public void setGaussianStdDeviation(double a_standardDeviation) {
    if (a_standardDeviation <= 0.00000d) {
      throw new IllegalArgumentException(
          "Standard deviation must be greater 0!");
    }
    m_standardDeviation = a_standardDeviation;
  }

  public double getGaussianStdDeviation() {
    return m_standardDeviation;
  }

  /**
   * @return positive integer value
   */
  public int nextInt() {
    return Math.abs(Math.min(Integer.MAX_VALUE - 1,
                    (int) Math.round(nextGaussian() * Integer.MAX_VALUE)));
  }

  /**
   * @param ceiling the upper boundary excluded
   * @return positive integer value between 0 and (ceiling - 1)
   */
  public int nextInt(int ceiling) {
    return Math.min(ceiling - 1,
                    (int) Math.round(nextGaussian() * ceiling/12));
  }

  /**
   * @return positive long value
   */
  public long nextLong() {
    long result = Math.min(Long.MAX_VALUE, (long) (nextGaussian() * Long.MAX_VALUE / 12));
    return result;
  }

  public double nextDouble() {
    return nextGaussian();
  }

  public float nextFloat() {
    return (float) (nextGaussian());
  }

  public boolean nextBoolean() {
    return nextGaussian() >= 0.5d;
  }

  /**
   * @return the next randomly distributed gaussian with current standard
   * deviation, will be greater/equal zero
   */
  private double nextGaussian() {
    //scale to [0..1[
    double r = (rn.nextGaussian() + 5.8d ) / (5.8d*2.0d);
    return r;
  }
}
