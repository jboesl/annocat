package de.verpalnt.annocat.api;

import java.lang.annotation.Annotation;

/**
 * Describes a mini-factory for the object (T) that knows how to process the given annotation (A).
 *
 * @author PaL
 *         Date: 03.06.12
 *         Time: 18:30
 */
public interface ICategoryFacilityFactory
{

  /**
   * This is the creation part where the facility is created.
   *
   * @param pAnnotation the annotation for which this factory was used.
   * @return
   */
  Object createFacility(Annotation pAnnotation) throws AnnotationNotSupportedException;

}
