package de.adito.annocat.spi;

import de.adito.annocat.api.AnnoCat;
import de.adito.annocat.api.FixedCategoryFacilityFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author PaL
 *         Date: 11.06.12
 *         Time: 00:16
 */
@AnnoCat(FixedValid.Factory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FixedValid
{

  boolean value();


  static class Factory extends FixedCategoryFacilityFactory
  {
    public Factory()
    {
      super(FixedValid.class, new ITestVerifier()
      {
        @Override
        public boolean isValid(Object pObj)
        {
          return Boolean.TRUE.equals(pObj);
        }
      });
    }
  }

}
