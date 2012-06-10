package de.verpalnt.annocat.spi;

import de.verpalnt.annocat.api.AbstractCategoryFacilityFactory;
import de.verpalnt.annocat.api.AnnoCat;
import de.verpalnt.annocat.api.ICategoryFacilityFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class AnnotationCategoryFacilityFactory extends AbstractCategoryFacilityFactory
{
  private static final Object SYNCJECT = new Object();
  private final Map<Class<? extends ICategoryFacilityFactory>, ICategoryFacilityFactory> cached = new HashMap<>();

  public AnnotationCategoryFacilityFactory()
  {
    register(AnnoCat.class, new Creator<AnnoCat>()
    {
      @Override
      public Object create(AnnoCat pAnnotation)
      {
        return _createFacilityFactoryProvider(pAnnotation);
      }
    });
  }

  private ICategoryFacilityFactoryProvider _createFacilityFactoryProvider(final AnnoCat pAnnotation)
  {
    return new ICategoryFacilityFactoryProvider()
    {
      @Override
      public List<ICategoryFacilityFactory> get()
      {
        synchronized (SYNCJECT)
        {
          List<ICategoryFacilityFactory> factories = new ArrayList<>();
          for (Class<? extends ICategoryFacilityFactory> factoryCls : pAnnotation.value())
          {
            ICategoryFacilityFactory facilityFactory = cached.get(factoryCls);
            if (facilityFactory == null)
            {
              try
              {
                facilityFactory = factoryCls.newInstance();
              }
              catch (InstantiationException e)
              {
                throw new RuntimeException(MessageFormat.format(
                    "Can''t create factory for class ''{0}''. FacilityFactories must have a no-args constructor.",
                    factoryCls));
              }
              catch (IllegalAccessException e)
              {
                throw new RuntimeException(MessageFormat.format(
                    "Can''t create factory for class ''{0}''. FacilityFactories must have a public constructor.",
                    factoryCls));
              }
              cached.put(factoryCls, facilityFactory);
            }
            factories.add(facilityFactory);
          }
          return factories;
        }
      }
    };
  }
}
