package utam.examples.salesforce.web;

import java.util.function.Supplier;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utam.base.UtamWebTestBase;
import utam.core.framework.base.ContainerElementPageObject;
import utam.core.framework.base.PageObject;
import utam.core.framework.consumer.Contained;
import utam.core.framework.consumer.Container;
import utam.core.framework.consumer.ContainerElement;
import utam.core.selenium.element.LocatorBy;
import utam.tests.pageobjects.UtamChild;
import utam.tests.pageobjects.UtamParent;

/**
 * Example of combining UTAM page objects with other page objects implementations. Uses deprecated
 * methods because not officially supported.
 *
 * @author elizaveta.ivanova
 * @since Jan 2022
 */
public class CompatibilityTests extends UtamWebTestBase {

  @BeforeTest
  public void setup() {
    setupChrome();
  }

  @Test
  public void testLoadExternalPageObjectFromUtam() {
    getDriver().get("https://utam.dev");
    UtamParent utamParent = from(UtamParent.class);
    ContainerElement containerElement =
        utamParent.getContainer(ContainerElementPageObject.class).getContainerElement();
    ScopedPageObject portalMenu = getCompatibleInsideUtam(containerElement, ScopedPageObject.class);
    assert portalMenu.getFoundElement() != null;
  }

  @Test
  public void testLoadUtamPageObjectFromExternal() {
    getDriver().get("https://utam.dev");
    ScopePageObject scopePageObject = new ScopePageObject(getDriver());
    UtamChild utamChild = getUtamInsideCompatible(scopePageObject, UtamChild.class, ".menu");
    assert utamChild.getGrammarMenuItem() != null;
  }

  @AfterTest
  public final void tearDown() {
    quitDriver();
  }

  private <T extends MyExternalCompatiblePageObject> T getCompatibleInsideUtam(
      ContainerElement containerElement, Class<T> clazz) {
    try {
      T instance = clazz.newInstance();
      containerElement.setScope(instance);
      return instance;
    } catch (Exception e) {
      throw new AssertionError(e);
    }
  }

  private <T extends MyExternalCompatiblePageObject, S extends PageObject>
      S getUtamInsideCompatible(T externalPageObject, Class<S> utamPageObject, String cssStr) {
    return loader.create(externalPageObject, utamPageObject, LocatorBy.byCss(cssStr));
  }

  /** simulates Salesforce legacy framework compatible with UTAM */
  abstract static class MyExternalCompatiblePageObject implements Contained, Container {

    WebElement utamScope;
    WebElement selfRoot;

    @Override
    public Supplier<SearchContext> getScope() {
      return () -> selfRoot;
    }

    @Override
    public void setScope(Supplier<SearchContext> scopeSupplier) {
      this.utamScope = (WebElement) scopeSupplier.get();
    }

    @Override
    public void setRoot(Supplier<SearchContext> rootSupplier) {
      throw new UnsupportedOperationException();
    }
  }

  /** simulates external page object */
  static class ScopedPageObject extends MyExternalCompatiblePageObject {

    public ScopedPageObject() {}

    Object getFoundElement() {
      return utamScope.findElement(By.cssSelector(".menu"));
    }
  }

  /** simulates external page object */
  static class ScopePageObject extends MyExternalCompatiblePageObject {

    ScopePageObject(WebDriver driver) {
      this.selfRoot = driver.findElement(By.cssSelector("body"));
    }
  }
}
