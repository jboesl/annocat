package de.verpalnt.annocat.api;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author PaL
 *         Date: 10.06.12
 *         Time: 14:15
 */
public interface IAnnotationSupplier
{

  List<Annotation> get();

}
