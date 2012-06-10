package de.verpalnt.annocat.spi;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author PaL
 *         Date: 03.06.12
 *         Time: 18:32
 */
public class FacilityProvider implements IFacilityProvider
{

  @Override
  public <T> T getFirst(IAnnotationSupplier<Iterable<Annotation>> pAnnoSupplier, Class<T> pFacilityClass)
  {
    List<? extends T> all = getAll(pAnnoSupplier, pFacilityClass);
    return all.isEmpty() ? null : all.get(0);
  }

  @Nonnull
  @Override
  public <T> List<? extends T> getAll(IAnnotationSupplier<Iterable<Annotation>> pAnnoSupplier, Class<T> pFacilityClass)
  {
    return new BasicFacilityFinder().getFacilities(pAnnoSupplier, pFacilityClass);
    //return Lists.newArrayList();
  }

}
