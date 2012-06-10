package de.verpalnt.annocat.spi;

import de.verpalnt.annocat.api.IAnnotationSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author PaL
 *         Date: 04.06.12
 *         Time: 23:09
 */
public interface IFacilityProvider
{

  @Nullable
  <T> T getFirst(@Nonnull Class<T> pAnnoInterpreterType, @Nonnull IAnnotationSupplier pAnnoSupplier);

  @Nonnull
  <T> List<? extends T> getAll(@Nonnull Class<T> pAnnoInterpreterType, @Nonnull IAnnotationSupplier pAnnoSupplier);

}
