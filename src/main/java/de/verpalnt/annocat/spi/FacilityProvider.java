package de.verpalnt.annocat.spi;

import javax.annotation.Nonnull;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * @author PaL
 *         Date: 03.06.12
 *         Time: 18:32
 */
public class FacilityProvider implements IFacilityProvider
{

  private final FacilityFinder facilityFinder;

  public FacilityProvider()
  {
    facilityFinder = new FacilityFinder();
  }

  @Override
  public <T> T getFirst(@Nonnull Class<T> pFacilityClass, @Nonnull AnnotatedElement pAnnotatedElement)
  {
    List<? extends T> all = getAll(pFacilityClass, pAnnotatedElement);
    return all.isEmpty() ? null : all.get(0);
  }

  @Nonnull
  @Override
  public <T> List<? extends T> getAll(@Nonnull Class<T> pFacilityClass, @Nonnull AnnotatedElement pAnnotatedElement)
  {
    return facilityFinder.getFacilities(pAnnotatedElement, pFacilityClass);
  }

}
