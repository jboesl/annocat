package de.adito.annocat.spi;

import de.adito.annocat.api.AnnotatedElements;
import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

/**
 * @author PaL
 *         Date: 11.06.12
 *         Time: 00:12
 */
public class Test_FacilityProvider
{
  @FixedValid(false)
  Void annotated1;

  @NotNull
  @Interval(min = 0, max = 100, openInterval = true)
  Integer annotated2;

  @Test
  public void testGetFirst() throws Exception
  {
    ITestVerifier first = new FacilityProvider().getFirst(ITestVerifier.class, _getSuppliers());
    Assert.assertNotNull(first);
    Assert.assertFalse(first.isValid(""));
    Assert.assertEquals("de.adito.annocat.spi.FixedValid$Factory$1", first.getClass().getName());
  }

  @Test
  public void testGetAll() throws Exception
  {
    List<? extends ITestVerifier> all = new FacilityProvider().getAll(ITestVerifier.class, _getSuppliers());
    Assert.assertEquals(3, all.size());
    for (ITestVerifier verifier : all)
      Assert.assertFalse(verifier.isValid(null));
  }

  private AnnotatedElement _getSuppliers()
  {
    return AnnotatedElements.concat(
        AnnotatedElements.fromField(Test_FacilityProvider.class, "annotated1"),
        AnnotatedElements.fromField(Test_FacilityProvider.class, "annotated2")
    );
  }

}
