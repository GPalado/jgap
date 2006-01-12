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

import java.lang.reflect.*;
import org.jgap.*;

/**
 * Default clone handler supporting IApplicationData as well as implementations
 * of Cloneable (for the latter: in case the clone-method is accessible via
 * reflection).
 *
 * @author Klaus Meffert
 * @since 2.6
 */
public class DefaultCloneHandler
    implements ICloneHandler {
  /**
   * Handles all implementations of IApplicationData as well as all of
   * java.lang.Cloneable (for which the clone-method is accessible via
   * reflection. This is not the case for package protected classes, e.g.).
   * @param a_clazz the class to check for whether it is handles
   * @return true in case class is clonable via this handler
   *
   * @author Klaus Meffert
   * @since 2.6
   */
  public boolean isHandlerFor(Class a_clazz) {
    if (IApplicationData.class.isAssignableFrom(a_clazz)) {
      return true;
    }
    if (Cloneable.class.isAssignableFrom(a_clazz)) {
      // Ensure access via reflection is possible
      try {
        Method m = a_clazz.getMethod("clone", new Class[] {});
        if (!m.isAccessible()) {
          return false;
        }
      }
      catch (Exception ex) {
        return false;
      }
      return true;
    }
    else {
      return false;
    }
  }

  /**
   *
   * @param a_objToClone the object to clone
   * @param a_params not considered here
   * @return Object
   *
   * @author Klaus Meffert
   * @since 2.6
   */
  public Object doClone(Object a_objToClone, Object a_params) {
    if (IApplicationData.class.isAssignableFrom(a_objToClone.getClass())) {
      try {
        return ( (IApplicationData) a_objToClone).clone();
      }
      catch (CloneNotSupportedException cex) {
        throw new IllegalStateException(cex.getMessage());
      }
    }
    // Support Cloneable interface by look for clone() method via
    // introspection.
    // ----------------------------------------------------------
    try {
      Method cloneMethod = a_objToClone.getClass().getMethod(
          "clone", new Class[] {});
      return cloneMethod.invoke(a_objToClone, new Object[] {});
    }
    catch (NoSuchMethodException nex) {
      throw new IllegalStateException(nex.getMessage());
    }
    catch (IllegalAccessException iex) {
      throw new IllegalStateException(iex.getMessage());
    }
    catch (InvocationTargetException tex) {
      throw new IllegalStateException(tex.getMessage());
    }
  }
}
