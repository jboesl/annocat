//package de.verpalnt.annocat.spi;
//
//import com.google.common.base.Function;
//import com.google.common.base.Predicates;
//import com.google.common.base.Supplier;
//import com.google.common.collect.Iterables;
//import com.google.common.collect.Lists;
//import de.verpalnt.annocat.api.AnnoCat;
//import de.verpalnt.annocat.api.ICategoryFacilityFactory;
//import de.verpalnt.annocat.api.ICategoryFacilityFactoryProvider;
//
//import java.lang.annotation.Annotation;
//import java.lang.annotation.Documented;
//import java.lang.annotation.Retention;
//import java.lang.annotation.Target;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @author PaL
// *         Date: 09.06.12
// *         Time: 21:36
// */
//class BasicFacilityFinder
//{
//
//  BasicFacilityFinder()
//  {
//  }
//
//  <T> Iterable<T> getFacilities(Supplier<Iterable<Annotation>> pAnnoSupplier,
//                                Class<T> pRequestedFacilityClass)
//  {
//    return _getFacilities(pAnnoSupplier, pRequestedFacilityClass, new HashSet<Annotation>());
//  }
//
//  private <T> Iterable<T> _getFacilities(Supplier<Iterable<Annotation>> pAnnoSupplier,
//                                         final Class<T> pRequestedFacilityClass, final Set<Annotation> pVisited)
//  {
//    Iterable<Annotation> annotations = pAnnoSupplier.get();
//    if (annotations == null)
//      return Collections.emptyList();
//    return Iterables.concat(Iterables.transform(annotations, new Function<Annotation, Iterable<T>>()
//    {
//      @Override
//      public Iterable<T> apply(final Annotation pAnnoCattedAnnotation)
//      {
//        if (Arrays.asList(Documented.class, Retention.class, Target.class).contains(pAnnoCattedAnnotation.annotationType()))
//          return Collections.emptyList();
//        return _getFacilities(pAnnoCattedAnnotation, pRequestedFacilityClass, pVisited);
//      }
//    }));
//  }
//
//  private <T> Iterable<T> _getFacilities(final Annotation pAnnoCattedAnnotation,
//                                         final Class<T> pRequestedFacilityClass, Set<Annotation> pVisited)
//  {
//    if (pAnnoCattedAnnotation.annotationType().equals(AnnoCat.class))
//    {
//      if (pRequestedFacilityClass.equals(ICategoryFacilityFactoryProvider.class))
//        //noinspection unchecked
//        return (Iterable<T>) Collections.singleton(
//            new AnnoCat.AnnotationCategory().createFacilityFactoryProvider((AnnoCat) pAnnoCattedAnnotation));
//      return Collections.emptyList();
//    }
//
//    if (pVisited.contains(pAnnoCattedAnnotation))
//      return Collections.emptyList();
//
//    Iterable<ICategoryFacilityFactoryProvider> facilityFactoryProviders = _getFacilities(
//        new Supplier<Iterable<Annotation>>()
//        {
//          @Override
//          public Iterable<Annotation> get()
//          {
//            return Lists.newArrayList(pAnnoCattedAnnotation.annotationType().getAnnotations());
//          }
//        }, ICategoryFacilityFactoryProvider.class, new HashSet<Annotation>(pVisited)
//        {{
//            add(pAnnoCattedAnnotation);
//          }}
//    );
//    return Iterables.concat(Iterables.transform(facilityFactoryProviders, new Function<ICategoryFacilityFactoryProvider, Iterable<T>>()
//    {
//      @Override
//      public Iterable<T> apply(ICategoryFacilityFactoryProvider input)
//      {
//        return _getFacilities(input.create(), pAnnoCattedAnnotation, pRequestedFacilityClass);
//      }
//    }));
//  }
//
//  private <T> Iterable<T> _getFacilities(Iterable<ICategoryFacilityFactory> pFacilityFactories,
//                                         final Annotation pAnnoCattedAnnotation, final Class<T> pRequestedFacilityClass)
//  {
//    return Iterables.filter(Iterables.transform(pFacilityFactories,
//        new Function<ICategoryFacilityFactory, T>()
//        {
//          @Override
//          public T apply(ICategoryFacilityFactory pCategoryFacilityFactory)
//          {
//            if (pRequestedFacilityClass.isAssignableFrom(pCategoryFacilityFactory.getFacilityClass()) &&
//                pAnnoCattedAnnotation.annotationType().equals(pCategoryFacilityFactory.getProcessableAnnotationClass()))
//            {
//              //noinspection unchecked
//              return _createFacility((ICategoryFacilityFactory<Annotation, T>) pCategoryFacilityFactory,
//                  pAnnoCattedAnnotation, pRequestedFacilityClass);
//            }
//            return null;
//          }
//        }), Predicates.notNull());
//  }
//
//  private <A extends Annotation, T> T _createFacility(ICategoryFacilityFactory<A, T> pFacilityFactory,
//                                                      A pAnnoCattedAnnotation, Class<T> pRequestedFacilityClass)
//  {
//    return pRequestedFacilityClass.cast(pFacilityFactory.createFacilityFactoryProvider(pAnnoCattedAnnotation));
//  }
//
//}
