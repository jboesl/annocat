package de.verpalnt.annocat.api;

import java.lang.annotation.Annotation;

/**
 * @author PaL
 *         Date: 11.06.12
 *         Time: 00:20
 */
public class FixedCategoryFacilityFactory<T> implements ICategoryFacilityFactory
{

  private T fixedResult;

  public FixedCategoryFacilityFactory(T pFixedResult)
  {
    fixedResult = pFixedResult;
  }

  @Override
  public Object createFacility(Annotation pAnnotation) throws AnnotationNotSupportedException
  {
    return fixedResult;
  }
}
