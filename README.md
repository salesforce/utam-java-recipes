# utam-java-recipes

Maven project with examples of compiler setup and testing with UTAM Java.
Project uses Java 11 and maven 4.

## Initial setup
- Clone repository and run `mvn clean install`
- Import this project as maven project in the IDE of your choice

## Run UI tests

### Desktop
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

### Mobile
- Follow the instruction at [Get Started for Mobile](https://utam.dev/guide/get_started_utam#get-started-for-mobile) to setup your local simulator/emulator.
- Make sure Appium and Nodejs are installed under /usr/local.
- Update following System Environment Variables according to your development machine setup if needed:
For iOS test, configure the application bundleid, test device name and the full path for the test application:

```java
System.setProperty("app.bundleid", "com.salesforce.chatter");
System.setProperty("ios.device", "iPhone 8 Plus");
System.setProperty("ios.app", getUserHomePath() + "SApp.app");
```

For Android test, configure the application bundleid, the full path for the test application and application initial activity:

```java
System.setProperty("app.bundleid", "com.salesforce.chatter");
System.setProperty("android.app", getUserHomePath() + "SApp.apk");
System.setProperty("app.activity", "com.salesforce.chatter.Chatter");
```

- Download the [debug build](https://developer.salesforce.com/tools/mobile-debugging) for SalesforceApp iOS and Android.
- Also if use IDE to execute test, please set the environment varilables ANDROID_HOME or ADROID_SDK_ROOT and PATH that includes /usr/local/bin for carthage.
- To execute test, open the project from IDE (Eclipse or Intellij), then choose testSetDataConnection to Run As TestNG Test. For test on Android, make sure to start an emulator before run. Otherwise test will fail for throwing a SessionNotCreatedException. The error will be similar as "org.openqa.selenium.SessionNotCreatedException: Unable to create a new remote session. Please check the server log for more details. Original error: An unknown server-side error occurred while processing the command. Original error: Could not find a connected Android device in 20054ms.".

## utam-pageobjects
Module with an example of setup for Page Objects authoring and compilation.

- JSON files are located under resources and should be named `<pageObjectName>.utam.json`
- Imperative extensions should be located under java sources in the package with same prefix as a generated 
Page Object, but with "utils" instead "pageobjects", for example utilities for `utam.mynamespace.pageobjects.MyComponent` 
should be under `utam.mynamespace.utils.MyUtils`, there is no naming convention for a class name with the utilities.

## utam-tests
Example of setup for UTAM Page Objects consumption. 
Tests from this module can be run by TestNG runner from IDE.


