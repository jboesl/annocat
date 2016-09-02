package de.adito.annocat.api;

import java.lang.annotation.Annotation;

/**
 * @author PaL
 *         Date: 11.06.12
 *         Time: 00:20
 */
public class FixedCategoryFacilityFactory extends AbstractCategoryFacilityFactory
{
  public FixedCategoryFacilityFactory(Class<? extends Annotation> pAnnotation, final Object pFixedResult)
  {
    register(pAnnotation, new Creator<Annotation>()
    {
      @Override
      public Object create(Annotation pAnnotation)
      {
        return pFixedResult;
      }
    });
  }

}
