package de.verpalnt.annocat.spi;

import de.verpalnt.annocat.api.IAnnotationSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author PaL
 *         Date: 04.06.12
 *         Time: 23:09
 */
public interface IFacilityProvider
{

  @Nullable
  <T> T getFirst(IAnnotationSupplier<Iterable<Annotation>> pAnnoSupplier, Class<T> pAnnoInterpreterType);

  @Nonnull
  <T> List<? extends T> getAll(IAnnotationSupplier<Iterable<Annotation>> pAnnoSupplier, Class<T> pAnnoInterpreterType);

}
