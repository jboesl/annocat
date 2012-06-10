package de.verpalnt.annocat.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author PaL
 *         Date: 10.06.12
 *         Time: 23:27
 */
public class AnnotationSuppliers
{

  private AnnotationSuppliers()
  {
  }

  public static IAnnotationSupplier fromField(Class pCls, String pFieldName)
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

  public static IAnnotationSupplier fromField(Field pField)
  {
    return fromAnnotations(Arrays.asList(pField.getAnnotations()));
  }

  public static IAnnotationSupplier fromClass(Class pCls)
  {
    return fromAnnotations(Arrays.asList(pCls.getAnnotations()));
  }

  public static IAnnotationSupplier fromMethod(Method pMethod)
  {
    return fromAnnotations(Arrays.asList(pMethod.getAnnotations()));
  }

  public static IAnnotationSupplier fromAnnotations(final List<Annotation> pAnnotations)
  {
    final List<Annotation> annos = new ArrayList<>(pAnnotations);
    return new IAnnotationSupplier()
    {
      @Override
      public List<Annotation> get()
      {
        return new ArrayList<>(annos);
      }
    };
  }

  public static IAnnotationSupplier empty()
  {
    return new IAnnotationSupplier()
    {
      @Override
      public List<Annotation> get()
      {
        return Collections.emptyList();
      }
    };
  }

}
