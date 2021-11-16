# utam-java-recipes

Maven project with examples of compiler setup and testing with UTAM Java.
Project uses Java 11 and maven 4.

__IMPORTANT: Page objects and tests for Salesforce UI are compatible with application version 236__

Modules:
- utam-preview

This is an example of setup for Page Objects authoring and compilation. 
This Module also contains Salesforce Page Objects for default org setup that can be used to build Salesforce UI tests.

- utam-tests
Example of setup for UTAM Page Objects consumption along with test utilities and examples of Salesforce UI tests.
Tests from this module can be run by TestNG runner from IDE.

## Initial setup

- Clone repository and run `mvn clean install`
```shell script
git clone https://github.com/salesforce/utam-java-recipes.git
cd utam-java-recipes
mvn clean install
```
- Import this project as maven project in the IDE of your choice

## Run Salesforce Web UI tests

Preconditions:

- Download chromedriver and geckodriver in the user home directory (returned by `System.getProperty("user.home")`) 
or set path from test with `System.setProperty("webdriver.chrome.driver", <path to chrome driver>)` and `System.setProperty("webdriver.gecko.driver", <path to gecko driver>)`

- To login to environment via UI at the beginning of the test, add env.properties file to the [test resources root](https://github.com/salesforce/utam-java-recipes/tree/main/utam-tests/src/test/resources) 

Content of the file should look like this, where "sandbox" is name of the environment as file can reference more than one.

```properties
sandbox.url=https://sandbox.salesforce.com/
sandbox.username=my.user@salesforce.com
sandbox.password=secretPassword
# sometimes after login URL changes
sandbox.redirectUrl=https://lightningapp.lightning.test1234.salesforce.com/
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

- To run tests use your IDE, for example in Intellij IDEA click on the class or method and choose option to run a particular test

## Run SFDX scratch org test 

- Requirements:
    - Node >= 14.x.x
    - Yarn >= 1.x.x

- Follow the steps in the [Quick Start: Lightning Web Components](https://trailhead.salesforce.com/content/learn/projects/quick-start-lightning-web-components/) Trailhead project. 
The steps include:
    - Enable Dev Hub in your Trailhead Playground
    - Install Salesforce CLI

- Authorize your hub org and provide it with an alias (**myhuborg** in the command below). 
Use the login credentials generated from your Trailhead Playground.
```shell script
sfdx auth:web:login -d -a myhuborg
```
- Create a scratch org and provide it with an alias (**utam-recipes** in the command below):
 ```shell script
sfdx force:org:create -s -f config/project-scratch-def.json -a utam-recipes
```

- Push the app to your scratch org:
```shell script
sfdx force:source:push
```

- Assign the **utam** permission set to the default user:
```shell script
sfdx force:user:permset:assign -n utam
```
> Tip: if this step throws an error `Permission set not found in target org`, run `sfdx plugins:install user` and re-create org again:
> - find created org `sfdx force:org:list --all`
> - delete previously created org with `sfdx force:org:delete`, it will prompt you to delete first org from list, 
> or specify org alias or email `sfdx force:org:delete -u utam-recipes`
> - run command that creates org

- Open the scratch org, this command should open your org in the browser
```shell script
sfdx force:org:open
```

- Generate login URL for your scratch org, run:
```shell script
sfdx force:org:open -p /lightning -r --json
```
Copy result.url from JSON printed into terminal and copy it to env.properties file  
```properties
# scratch org can login by Url
sfdxScratchOrg.sfdxUrl=https://<scratch-org-name>.cs22.my.salesforce.com/secur/frontdoor.jsp?sid=<generated-sid>
```

## Run Salesforce Mobile test
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



