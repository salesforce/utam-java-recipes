# utam-java-recipes

Maven project with examples of compiler setup and testing with UTAM Java.
Project uses Java 11 and maven 4.

## Initial setup
- Clone repository and run `mvn clean install`
- Import this project as maven project in the IDE of your choice

## utam-preview module

This is an example of setup for Page Objects authoring and compilation. 
This Module also contains Salesforce Page Objects for default org setup that can be used to build Salesforce UI tests.

## utam-tests module
Example of setup for UTAM Page Objects consumption along with test utilities and examples of Salesforce UI tests.
Tests from this module can be run by TestNG runner from IDE.
__IMPORTANT: Page objects and tests for Salesforce UI are compatible with application version 236__

## Run UI tests

### Web
- Before running tests: make sure you have chromedriver and/or geckodriver in the user home directory or set path from test.
- To login to environment at the beginning of the test, add env.properties file to the [test resources root](https://github.com/salesforce/utam-java-recipes/tree/main/utam-tests/src/test/resources) 
To use ligin to UI methods, properties file has to have following properties, where "sandbox" is name of the environment as file can reference more than one.

```properties
sandbox.url=https://sandbox.salesforce.com/
sandbox.username=my.user@salesforce.com
sandbox.password=secretPassword

myOrg2sandbox.url=https://test1234.salesforce.com/
# sometimes after login URL changes
myOrg2sandbox.redirectUrl=https://lightningapp.lightning.test1234.salesforce.com/
myOrg2sandbox.username=test.user@test.com
myOrg2sandbox.password=justPassword

# scratch org can login by Url
sfdxScratchOrg.loginUrl=https://<scratch-org-name>.cs22.my.salesforce.com/secur/frontdoor.jsp?sid=<generated-sid>
```
Then in the login method inside test, provide prefix of your environment as a parameter
```java
TestEnvironment testEnvironment = getTestEnvironment("sandobox");

  @BeforeTest
  public void setup() {
    setupChrome();
    loginToHomePage(testEnvironment);
  }
```

- To run tests use your IDE, in Intellij for example developer would right click on the class or method and choose option `Run myTest()`

### Mobile
- Follow the instruction at [Get Started for Mobile](https://utam.dev/guide/get_started_utam#get-started-for-mobile) to setup your local simulator/emulator.
- Make sure Appium and Nodejs are installed in user home.
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



