# utam-java-recipes

Maven project with examples of compiler setup and testing with UTAM Java.
Project uses Java 11 and maven 4.

## Initial setup
- Clone repository and run `mvn clean install`
- Import this project as maven project in the IDE of your choice

## Run UI tests
- Before running tests: make sure you have chromedriver and geckodriver in the user home directory
- To login to environment at the beginning of the test, add env.properties file to the [test resources root](https://github.com/salesforce/utam-java-recipes/tree/main/utam-tests/src/test/resources) 

For example:
```properties
sandbox.url=https://sandbox.salesforce.com/
sandbox.username=my.user@salesforce.com
sandbox.password=secretPassword
```
Then in the login method inside test, provide prefix of your environment as a parameter
```java
@Test
public void myTest() {
    // create instance of the driver and setup UtamLoader
    setupChrome();
    // login to the environment "sandbox" with credentials from properties
    login("sanbox", "home/page/url");
    // ...
}
```

- To run tests use your IDE, in Intellij for example developer would right click on the class or method and choose option `Run myTest()`

## utam-pageobjects
Module with an example of setup for Page Objects authoring and compilation.

- JSON files are located under resources and should be named `<pageObjectName>.utam.json`
- Imperative extensions should be located under java sources in the package with same prefix as a generated 
Page Object, but with "utils" instead "pageobjects", for example utilities for `utam.mynamespace.pageobjects.MyComponent` 
should be under `utam.mynamespace.utils.MyUtils`, there is no naming convention for a class name with the utilities.

## utam-tests
Example of setup for UTAM Page Objects consumption. 
Tests from this module can be run by TestNG runner from IDE.


