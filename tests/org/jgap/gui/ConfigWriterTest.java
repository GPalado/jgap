/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licencing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package org.jgap.gui;
import org.jgap.*;
import org.jgap.impl.*;
import org.jgap.event.*;

import junit.framework.*;
import junitx.util.*;

import java.util.*;
import java.io.*;
/**
 * Tests for ConfigWriter class
 * @author Siddhartha Azad
 */
public class ConfigWriterTest
    extends TestCase {

  /** String containing the CVS revision. Read out via reflection!*/
  private final static String CVS_REVISION = "$Revision: 1.2 $";


  public static Test suite() {
    TestSuite suite = new TestSuite(ConfigWriterTest.class);
    return suite;
  }

  /**
   * Get mock config data, use the ConfigWriter to write a property file
   * retrieve the written file, load it and check if the contents got
   * written correctly.
   * @author Siddhartha Azad.
   * */
  public void testConfigData_0() throws Exception {
      IConfigInfo ici = new MockIConfigInfo();
  	  ConfigWriter.instance().write(ici);
  	  // read the file and check the values
  	  int totalProps = 0;
  	  
  	  Properties props = new Properties();
  	  try {
  	  	  props.load(new FileInputStream("jgapTmp.con"));
  	  	  ConfigData cd = ici.getConfigData();
  	  	  String nsPrefix = cd.getNS() + ".";
  	  	  String name;
  	  	  ArrayList values;
  	  	  for (int i = 0; i < cd.getNumLists(); i++) {
  	  	  	  name = cd.getListNameAt(i);
	  		  values = cd.getListValuesAt(i);
	  		  int idx = 0;
	  		  for (Iterator iter = values.iterator(); iter.hasNext(); idx++) {
	  		  	  // append an index for same key elements
	  		  	  String tmpName = nsPrefix + name + "[" + idx + "]";
	  		  	  try {
	  		  	  	  assertEquals(props.getProperty(tmpName), (String)iter.next());	  
	  		  	  }	
	  		  	  catch(Exception ex) {
	  		  	  	  ex.printStackTrace();
	  		  	  	  fail();
	  		  	  }
	  		  	  totalProps++;
	  		  }
  		}
  		// test the TextField properties
  		String value = "", tmpName = "";
  		for (int i = 0; i < cd.getNumTexts(); i++) {
  	      	name = cd.getTextNameAt(i);
  	      	value = cd.getTextValueAt(i);
  	      	tmpName = nsPrefix + name;
  	      	try {
		  	    assertEquals(props.getProperty(tmpName), value);
	        }
	        catch(Exception ex) {
	            ex.printStackTrace();
	            fail();
	        }
	        totalProps++;
  	    }
  		assertEquals(totalProps, props.size());
  	}
  	catch(IOException ioEx) {
  		ioEx.printStackTrace();
  		fail();
  	}
  }
  /**
   * Test the reading of a config file and loading of the m_populatioSize
   * variable of the Configuration Configurable.
   * @author Siddhartha Azad.
   * */
  public void testConfigReader_0() throws Exception {
  	Configuration config;
	try {
	  config = new CustomConfiguration("jgapTest.con");
	}
	catch(Exception ex) {
	  ex.printStackTrace();
	  return;
    }
	// Some setup so that the Configuration doesn't throw an Exception. We do
	// not set the population size of the Configuration since this is supposed
	// to be read directly from the config file.
	// set up a sample chromosome
	Gene[] sampleGenes = new Gene[3];
	sampleGenes[0] = new IntegerGene(60, 100);
	sampleGenes[1] = new IntegerGene(1, 50);
	sampleGenes[2] = new IntegerGene(100, 150);
	Chromosome sampleChromosome = new Chromosome(sampleGenes);
	Genotype population;
	FitnessFunction fitFunc = new MockFitnessFunction();
	try {
	  config.setFitnessFunction(fitFunc);
	  // The higher the value, the better
	  config.setFitnessEvaluator(new DefaultFitnessEvaluator());
	  config.setSampleChromosome(sampleChromosome);
	  BestChromosomesSelector bestChromsSelector = new 
	  BestChromosomesSelector(1.0d);
	  bestChromsSelector.setDoubletteChromosomesAllowed(false);
	  config.addNaturalSelector(bestChromsSelector, true);
	  config.setRandomGenerator(new StockRandomGenerator());
	  config.setEventManager(new EventManager());
      config.addGeneticOperator(new CrossoverOperator());
	  config.addGeneticOperator(new MutationOperator(15));
	  population = Genotype.randomInitialGenotype(config);
	}
	catch(InvalidConfigurationException icEx) {
	  icEx.printStackTrace();
	  fail();
    }
	catch(Exception ex) {
	  ex.printStackTrace();
	  fail();
	}
	assertEquals(config.getPopulationSize(), 35);
  }	
}	