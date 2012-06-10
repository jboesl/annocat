package de.verpalnt.annocat.spi;

import de.verpalnt.annocat.api.AnnoCat;
import de.verpalnt.annocat.api.AnnotationNotSupportedException;
import de.verpalnt.annocat.api.IAnnotationSupplier;
import de.verpalnt.annocat.api.ICategoryFacilityFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.*;
import java.util.*;

/**
 * @author PaL
 *         Date: 09.06.12
 *         Time: 21:36
 */
public class FacilityFinder
{

  private static final Set<Class<? extends Annotation>> SYSTEM_ANNOTATIONS = new HashSet<>(Arrays.asList(
      Documented.class, Inherited.class, Retention.class, Target.class,
      Deprecated.class, Override.class, SafeVarargs.class, SuppressWarnings.class));

  private final AnnotationCategoryFacilityFactory annoCatFacilityFactory = new AnnotationCategoryFacilityFactory();

  protected FacilityFinder()
  {
  }

  @Nonnull
  <T> List<T> getFacilities(IAnnotationSupplier<Iterable<Annotation>> pAnnoSupplier,
                            Class<T> pRequestedFacilityClass)
  {
    return _getFacilities(pAnnoSupplier, pRequestedFacilityClass, new HashSet<Annotation>());
  }

  private <T> List<T> _getFacilities(IAnnotationSupplier<Iterable<Annotation>> pAnnoSupplier,
                                     final Class<T> pRequestedFacilityClass, final Set<Annotation> pVisited)
  {
    Iterable<Annotation> annotations = pAnnoSupplier.get();
    if (annotations == null)
      return Collections.emptyList();
    return _getFacilities(annotations, pRequestedFacilityClass, pVisited);
  }

  private <T> List<T> _getFacilities(Iterable<Annotation> pAnnotations,
                                     final Class<T> pRequestedFacilityClass, final Set<Annotation> pVisited)
  {
    List<T> facilities = new ArrayList<>();
    for (Annotation annoCattedAnnotation : pAnnotations)
    {
      if (processAnnotation(annoCattedAnnotation.annotationType()))
        facilities.addAll(_getFacilities(annoCattedAnnotation, pRequestedFacilityClass, pVisited));
    }
    return facilities;
  }

  private <T> Collection<T> _getFacilities(final Annotation pAnnoCattedAnnotation,
                                           final Class<T> pRequestedFacilityClass, Set<Annotation> pVisited)
  {

    Collection<T> customFacilities = getCustomFacilities(pAnnoCattedAnnotation, pRequestedFacilityClass);
    if (customFacilities != null)
      return customFacilities;

    if (pAnnoCattedAnnotation.annotationType().equals(AnnoCat.class))
    {
      if (pRequestedFacilityClass.equals(ICategoryFacilityFactoryProvider.class))
        //noinspection unchecked
        return (Collection<T>) Collections.singleton(annoCatFacilityFactory.createFacility(pAnnoCattedAnnotation));
      return Collections.emptyList();
    }

    if (pVisited.contains(pAnnoCattedAnnotation)) // don't cycle
      return Collections.emptyList();

    Collection<ICategoryFacilityFactoryProvider> facilityFactoryProviders = _getFacilities(
        Arrays.asList(pAnnoCattedAnnotation.annotationType().getAnnotations()),
        ICategoryFacilityFactoryProvider.class,
        new HashSet<Annotation>(pVisited)
        {{
            add(pAnnoCattedAnnotation);
          }}
    );

    List<T> facilities = new ArrayList<>();
    for (ICategoryFacilityFactoryProvider facilityFactoryProvider : facilityFactoryProviders)
      facilities.addAll(_getFacilities(facilityFactoryProvider.get(), pAnnoCattedAnnotation, pRequestedFacilityClass));
    return facilities;
  }

  private <T> Collection<T> _getFacilities(Iterable<ICategoryFacilityFactory> pFacilityFactories,
                                           final Annotation pAnnoCattedAnnotation, final Class<T> pRequestedFacilityClass)
  {
    List<T> facilities = new ArrayList<>();
    for (ICategoryFacilityFactory facilityFactory : pFacilityFactories)
    {
      T facility = createFacility(facilityFactory, pAnnoCattedAnnotation, pRequestedFacilityClass);
      if (facility != null)
        facilities.add(facility);
    }
    return facilities;
  }

  @Nullable
  protected <T> Collection<T> getCustomFacilities(Annotation pAnnoCattedAnnotation, Class<T> pRequestedFacilityClass)
  {
    return null;
  }

  protected boolean processAnnotation(Class<? extends Annotation> pAnnotationCls)
  {
    return !SYSTEM_ANNOTATIONS.contains(pAnnotationCls);
  }

  @Nullable
  protected <T> T createFacility(ICategoryFacilityFactory pFacilityFactory,
                                 Annotation pAnnoCattedAnnotation, Class<T> pRequestedFacilityClass)
  {
    Object facility;
    try
    {
      facility = pFacilityFactory.createFacility(pAnnoCattedAnnotation);
    }
    catch (AnnotationNotSupportedException e)
    {
      return null;
    }
    if (facility != null && pRequestedFacilityClass.isAssignableFrom(facility.getClass()))
      return pRequestedFacilityClass.cast(facility);
    return null;
  }

}
