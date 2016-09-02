package de.adito.annocat.api;

import de.adito.annocat.spi.AnnotationCategoryFacilityFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes the categories the annotated annotation belongs to.<p/>
 * The categories aren't directly provided as it could be done with <tt>enums</tt> but is instead specified by the
 * implementation of an {@link ICategoryFacilityFactory}.
 *
 * @author PaL
 *         Date: 03.06.12
 *         Time: 18:26
 */
@AnnoCat(AnnotationCategoryFacilityFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface AnnoCat
{

  /**
   * @return all categories the annotated annotations belongs to represented by <tt>ICategoryFacilityFactory</tt>
   *         classes. The Factories <b>must</b> have a public no-argument constructor.
   */
  Class<? extends ICategoryFacilityFactory>[] value();


}
