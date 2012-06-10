package de.verpalnt.annocat.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes the categories the annotated annotation belongs to.<p/>
 * The categories aren't directly provided as it could be done with <tt>enums</tt> but is instead specified by the
 * implementation of an {@link ICategoryFacilityFactory}.
 *
 * @author PaL
 *         Date: 03.06.12
 *         Time: 18:26
 */
@AnnoCat(AnnoCat.AnnotationCategory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface AnnoCat
{

  /**
   * @return all categories the annotated annotations belongs to represented by <tt>ICategoryFacilityFactory</tt>
   *         classes. The Factories <b>must</b> have a public no-argument constructor.
   */
  Class<? extends ICategoryFacilityFactory>[] value();


  /**
   *
   */
  public class AnnotationCategory extends AbstractCategoryFacilityFactory<AnnoCat, ICategoryFacilityFactoryProvider>
  {
    public AnnotationCategory()
    {
      super(AnnoCat.class, ICategoryFacilityFactoryProvider.class);
    }

    @Override
    public ICategoryFacilityFactoryProvider createFacilityFactoryProvider(final AnnoCat pAnnotation)
    {
      return new ICategoryFacilityFactoryProvider()
      {
        @Override
        public List<ICategoryFacilityFactory> create()
        {
          List<ICategoryFacilityFactory> factories = new ArrayList<>();
          for (Class<? extends ICategoryFacilityFactory> factoryCls : pAnnotation.value())
          {
            ICategoryFacilityFactory facilityFactory;
            try
            {
              facilityFactory = factoryCls.newInstance();
            }
            catch (InstantiationException e)
            {
              throw new RuntimeException(MessageFormat.format(
                  "Can't create factory for class {0}. FacilityFactories must have a no-args constructor.",
                  factoryCls));
            }
            catch (IllegalAccessException e)
            {
              throw new RuntimeException(MessageFormat.format(
                  "Can't create factory for class {0}. FacilityFactories must have a public constructor.",
                  factoryCls));
            }
            factories.add(facilityFactory);
          }
          return factories;
        }
      };
    }
  }

}
