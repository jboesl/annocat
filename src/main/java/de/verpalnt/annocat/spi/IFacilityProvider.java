package de.verpalnt.annocat.spi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * @author PaL
 *         Date: 04.06.12
 *         Time: 23:09
 */
public interface IFacilityProvider
{

  @Nullable
  <T> T getFirst(@Nonnull Class<T> pAnnoInterpreterType, @Nonnull AnnotatedElement pAnnotatedElement);

  @Nonnull
  <T> List<? extends T> getAll(@Nonnull Class<T> pAnnoInterpreterType, @Nonnull AnnotatedElement pAnnotatedElement);

}
