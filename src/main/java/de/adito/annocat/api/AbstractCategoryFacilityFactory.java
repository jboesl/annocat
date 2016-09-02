package de.adito.annocat.api;

import java.lang.annotation.Annotation;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstraction of ICategoryFacilityFactory so that one doesn't have to implement the methods
 * <tt>getProcessableAnnotationClass</tt> and <tt>getFacilityClass</tt>. The implementation's constructors still have to
 * be public with no arguments.
 *
 * @author PaL
 *         Date: 06.06.12
 *         Time: 00:01
 */
public abstract class AbstractCategoryFacilityFactory implements ICategoryFacilityFactory
{
  protected Map<Class<? extends Annotation>, Creator> creatorMap = new HashMap<>();

  protected AbstractCategoryFacilityFactory()
  {
  }

  protected final <A extends Annotation> void register(Class<? extends A> pAnnotationCls, Creator<A> pCreator)
  {
    creatorMap.put(pAnnotationCls, pCreator);
  }

  @Override
  public final Object createFacility(Annotation pAnnotation) throws AnnotationNotSupportedException
  {
    Creator creator = creatorMap.get(pAnnotation.annotationType());
    if (creator == null)
      throw new AnnotationNotSupportedException(MessageFormat.format(
          "Annotation ''{0}'' is not supported by factory ''{1}''.", pAnnotation, this));
    //noinspection unchecked
    return creator.create(pAnnotation);
  }

  protected interface Creator<A extends Annotation>
  {
    Object create(A pAnnotation);
  }

}
