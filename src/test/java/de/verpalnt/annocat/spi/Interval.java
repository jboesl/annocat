package de.verpalnt.annocat.spi;

import de.verpalnt.annocat.api.AbstractCategoryFacilityFactory;
import de.verpalnt.annocat.api.AnnoCat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author PaL
 *         Date: 11.06.12
 *         Time: 00:53
 */
@AnnoCat(Interval.Factory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Interval
{

  int min() default 0;

  int max() default 0;

  boolean openInterval() default false;


  static class Factory extends AbstractCategoryFacilityFactory
  {
    public Factory()
    {
      register(Interval.class, new Creator<Interval>()
      {
        @Override
        public Object create(final Interval pAnnotation)
        {
          return new ITestVerifier()
          {
            @Override
            public boolean isValid(Object pObj)
            {
              if (pObj instanceof Integer)
              {
                Integer val = (Integer) pObj;
                if (pAnnotation.openInterval())
                  return pAnnotation.min() <= val && pAnnotation.max() >= val;
                else
                  return pAnnotation.min() < val && pAnnotation.max() > val;
              }
              return false;
            }
          };
        }
      });
    }
  }

}
