package de.adito.annocat.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author PaL
 *         Date: 10.06.12
 *         Time: 23:27
 */
public class AnnotatedElements
{

  private AnnotatedElements()
  {
  }

  public static AnnotatedElement fromField(Class pCls, String pFieldName)
  {
    try
    {
      Field field = pCls.getDeclaredField(pFieldName);
      return fromField(field);
    }
    catch (NoSuchFieldException e)
    {
      return empty();
    }
  }

  public static AnnotatedElement fromField(Field pField)
  {
    return pField;
  }

  public static AnnotatedElement fromClass(Class pCls)
  {
    return pCls;
  }

  public static AnnotatedElement fromMethod(Method pMethod)
  {
    return pMethod;
  }

  public static AnnotatedElement fromAnnotations(List<Annotation> pAnnotations)
  {
    if (pAnnotations == null)
      return null;
    return new _SimpleAnnotatedElement(pAnnotations.toArray(new Annotation[pAnnotations.size()]));
  }

  public static AnnotatedElement fromAnnotations(Annotation... pAnnotations)
  {
    if (pAnnotations == null)
      return null;
    return new _SimpleAnnotatedElement(pAnnotations);
  }

  public static AnnotatedElement empty()
  {
    return new _SimpleAnnotatedElement();
  }

  public static AnnotatedElement concat(AnnotatedElement... pAnnotatedElements)
  {
    if (pAnnotatedElements == null || pAnnotatedElements.length == 0)
      return empty();
    List<Annotation> annotations = new ArrayList<>();
    for (AnnotatedElement annotatedElement : pAnnotatedElements)
      annotations.addAll(Arrays.asList(annotatedElement.getAnnotations()));
    return fromAnnotations(annotations);
  }


  /**
   * AnnotatedElement-Impl
   */
  private static class _SimpleAnnotatedElement implements AnnotatedElement
  {
    private Annotation[] annotations;

    public _SimpleAnnotatedElement(Annotation... pAnnotations)
    {
      annotations = pAnnotations == null ? new Annotation[0] : pAnnotations;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> pAnnotationClass)
    {
      return getAnnotation(pAnnotationClass) != null;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> pAnnotationClass)
    {
      if (annotations.length != 0 && pAnnotationClass != null)
        for (Annotation annotation : annotations)
          if (pAnnotationClass.isAssignableFrom(annotation.annotationType()))
            //noinspection unchecked
            return (T) annotation;
      return null;
    }

    @Override
    public Annotation[] getAnnotations()
    {
      Annotation[] ans = new Annotation[this.annotations.length];
      System.arraycopy(annotations, 0, ans, 0, annotations.length);
      return ans;
    }

    @Override
    public Annotation[] getDeclaredAnnotations()
    {
      return getAnnotations();
    }
  }
}
