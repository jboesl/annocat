package de.verpalnt.annocat.api;

/**
 * @author PaL
 *         Date: 10.06.12
 *         Time: 20:20
 */
public class AnnotationNotSupportedException extends RuntimeException
{
  public AnnotationNotSupportedException()
  {
  }

  public AnnotationNotSupportedException(String message)
  {
    super(message);
  }

  public AnnotationNotSupportedException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public AnnotationNotSupportedException(Throwable cause)
  {
    super(cause);
  }

  public AnnotationNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
  {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
