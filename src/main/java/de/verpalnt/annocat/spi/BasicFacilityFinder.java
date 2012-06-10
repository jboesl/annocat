package de.verpalnt.annocat.spi;

import de.verpalnt.annocat.api.AnnoCat;
import de.verpalnt.annocat.api.ICategoryFacilityFactory;
import de.verpalnt.annocat.api.ICategoryFacilityFactoryProvider;

import java.lang.annotation.*;
import java.util.*;

/**
 * @author PaL
 *         Date: 09.06.12
 *         Time: 21:36
 */
class BasicFacilityFinder
{

  private static final Set<Class<? extends Annotation>> SYSTEM_ANNOTATIONS = new HashSet<>(Arrays.asList(
      Documented.class, Inherited.class, Retention.class, Target.class,
      Deprecated.class, Override.class, SafeVarargs.class, SuppressWarnings.class));

  BasicFacilityFinder()
  {
  }

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
    List<T> facilities = new ArrayList<>();
    for (Annotation annoCattedAnnotation : annotations)
    {
      if (!SYSTEM_ANNOTATIONS.contains(annoCattedAnnotation.annotationType()))
        facilities.addAll(_getFacilities(annoCattedAnnotation, pRequestedFacilityClass, pVisited));
    }
    return facilities;
  }

  private <T> Collection<T> _getFacilities(final Annotation pAnnoCattedAnnotation,
                                           final Class<T> pRequestedFacilityClass, Set<Annotation> pVisited)
  {
    if (pAnnoCattedAnnotation.annotationType().equals(AnnoCat.class))
    {
      if (pRequestedFacilityClass.equals(ICategoryFacilityFactoryProvider.class))
        //noinspection unchecked
        return (Collection<T>) Collections.singleton(
            new AnnoCat.AnnotationCategory().createFacilityFactoryProvider((AnnoCat) pAnnoCattedAnnotation));
      return Collections.emptyList();
    }

    if (pVisited.contains(pAnnoCattedAnnotation))
      return Collections.emptyList();

    Collection<ICategoryFacilityFactoryProvider> facilityFactoryProviders = _getFacilities(
        new IAnnotationSupplier<Iterable<Annotation>>()
        {
          @Override
          public Iterable<Annotation> get()
          {
            return Arrays.asList(pAnnoCattedAnnotation.annotationType().getAnnotations());
          }
        }, ICategoryFacilityFactoryProvider.class, new HashSet<Annotation>(pVisited)
        {{
            add(pAnnoCattedAnnotation);
          }}
    );

    List<T> facilities = new ArrayList<>();
    for (ICategoryFacilityFactoryProvider facilityFactoryProvider : facilityFactoryProviders)
      facilities.addAll(_getFacilities(facilityFactoryProvider.create(), pAnnoCattedAnnotation, pRequestedFacilityClass));
    return facilities;
  }

  private <T> Collection<T> _getFacilities(Iterable<ICategoryFacilityFactory> pFacilityFactories,
                                           final Annotation pAnnoCattedAnnotation, final Class<T> pRequestedFacilityClass)
  {
    List<T> facilities = new ArrayList<>();
    for (ICategoryFacilityFactory facilityFactory : pFacilityFactories)
    {
      if (pRequestedFacilityClass.isAssignableFrom(facilityFactory.getFacilityClass()) &&
          pAnnoCattedAnnotation.annotationType().equals(facilityFactory.getProcessableAnnotationClass()))
      {
        //noinspection unchecked
        facilities.add(_createFacility((ICategoryFacilityFactory<Annotation, T>) facilityFactory,
            pAnnoCattedAnnotation, pRequestedFacilityClass));
      }
    }
    return facilities;
  }

  private <A extends Annotation, T> T _createFacility(ICategoryFacilityFactory<A, T> pFacilityFactory,
                                                      A pAnnoCattedAnnotation, Class<T> pRequestedFacilityClass)
  {
    return pRequestedFacilityClass.cast(pFacilityFactory.createFacilityFactoryProvider(pAnnoCattedAnnotation));
  }

}
