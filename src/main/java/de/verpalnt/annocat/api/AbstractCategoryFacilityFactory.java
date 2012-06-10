package de.verpalnt.annocat.api;

import java.lang.annotation.Annotation;

/**
 * An abstraction of ICategoryFacilityFactory so that one doesn't have to implement the methods
 * <tt>getProcessableAnnotationClass</tt> and <tt>getFacilityClass</tt>. The implementation's constructors still have to
 * be public with no arguments.
 *
 * @author PaL
 *         Date: 06.06.12
 *         Time: 00:01
 */
public abstract class AbstractCategoryFacilityFactory<A extends Annotation, T> implements ICategoryFacilityFactory<A, T>
{

  private Class<A> processableAnnotationClass;
  private Class<T> facilityClass;

  protected AbstractCategoryFacilityFactory(Class<A> processableAnnotationClass, Class<T> facilityClass)
  {
    this.processableAnnotationClass = processableAnnotationClass;
    this.facilityClass = facilityClass;
  }

  @Override
  public Class<? extends A> getProcessableAnnotationClass()
  {
    return processableAnnotationClass;
  }

  @Override
  public Class<? extends T> getFacilityClass()
  {
    return facilityClass;
  }
}
