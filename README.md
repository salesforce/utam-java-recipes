# utam-java-recipes

UI Test Automation Model (UTAM) is based on the popular Page Object model design pattern commonly used in UI tests. 

UTAM documentation is [here](https://utam.dev/)

This repository is a maven project with examples of UTAM compiler setup and UI tests written with UTAM.
Project uses Java 11 and maven 4.

## Initial setup

- Clone repository and run `mvn clean install`
```shell script
git clone https://github.com/salesforce/utam-java-recipes.git
cd utam-java-recipes
```
- Import this project as a maven project in the IDE of your choice. 

## Generate Page Objects

Module utam-preview is an example of Page Objects authoring and compilation. 
This Module also contains Salesforce Page Objects for default org setup that can be used to build Salesforce UI tests.

__IMPORTANT: Page objects and tests for Salesforce UI are compatible with application version 236__

To generate Page Objects, run maven build from the project root:
```shell script
mvn clean install
```

## Run Salesforce Web UI tests

Module utam-tests contains example of setup for UTAM Page Objects consumption, test utilities and examples of Salesforce UI tests.

Preconditions:

- Download chromedriver and geckodriver in the user home directory (returned by `System.getProperty("user.home")`) 
or set path from test with `System.setProperty("webdriver.chrome.driver", <path to chrome driver>)` and `System.setProperty("webdriver.gecko.driver", <path to gecko driver>)`

- To login to environment via UI at the beginning of the test, add env.properties file to the [utam-tests test resources root](https://github.com/salesforce/utam-java-recipes/tree/main/utam-tests/src/test/resources) 

Content of the file should look like this, where "sandbox" is name of the environment (env file can reference more than one):

```properties
sandbox.url=https://sandbox.salesforce.com/
sandbox.username=my.user@salesforce.com
sandbox.password=secretPassword
# sometimes after login URL changes
sandbox.redirectUrl=https://lightningapp.lightning.test1234.salesforce.com/
```
Then in the login method inside test, provide prefix of your environment as a parameter
```java
TestEnvironment testEnvironment = getTestEnvironment("sandbox");

  @BeforeTest
  public void setup() {
    setupChrome();
    loginToHomePage(testEnvironment);
  }
```

- To run tests use your IDE, for example in Intellij IDEA click on the class or method and choose option to run a particular test. 
Salesforce UI tests examples are located in [utam-tests module](https://github.com/salesforce/utam-java-recipes/tree/main/utam-tests/src/test/java/utam/examples/salesforce/web)

- Example of UTAM setup for Web tests is in the [base class](https://github.com/salesforce/utam-java-recipes/blob/main/utam-tests/src/test/java/utam/base/UtamWebTestBase.java) 

## Run SFDX scratch org test 

Module force-app contains setup of scratch org - custom components and permissions. 
It is a JavaScript module and is not included in the maven project. Before running UI tests 

### Prerequisites
- Node >= 14.x.x
- Yarn >= 1.x.x
- Enable Dev Hub in your Trailhead Playground
- Install Salesforce CLI
- Authorize your hub org and provide it with an alias (**myhuborg** in the command below). 
  Use the login credentials generated from your Trailhead Playground.
  ```shell script
  sfdx auth:web:login -d -a myhuborg
  ```

> See Trailhead 
> [Quick Start: Lightning Web Components](https://trailhead.salesforce.com/content/learn/projects/quick-start-lightning-web-components/)
> for Dev Hub and CLI setup information 

### Scratch org setup

To setup scratch org and login via URL, run following commands from the project root:

1. If you already had utam-recipes org, delete previously created org 
```shell script
sfdx force:org:delete -u utam-recipes
```
2. Create a scratch org and provide it with an alias **utam-recipes**:
 ```shell script
sfdx force:org:create -s -f config/project-scratch-def.json -a utam-recipes  --durationdays 30
```
3. Push force-app to your scratch org:
```shell script
sfdx force:source:push
```
4. Assign the **utam** permission set to the default user:
```shell script
sfdx force:user:permset:assign -n utam
```
> Tip: if this step throws an error `Permission set not found in target org`, run `sfdx plugins:install user` and repeat steps 1-4

5. Generate login URL for your scratch org, run:
```shell script
sfdx force:org:open -p /lightning -r --json
```
This command will print out JSON to your terminal, copy result.url from JSON printed into terminal and copy it to env.properties file  
```properties
# scratch org can login by Url
scratchOrg.sfdx.url=https://<scratch-org-name>.cs22.my.salesforce.com/secur/frontdoor.jsp?sid=<generated-sid>
```
> Tip: If you want to open scratch org in your local browser, run `sfdx force:org:open`

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
- Also if use IDE to execute test, please set the environment variables JAVA_HOME, ANDROID_HOME or ADROID_SDK_ROOT and PATH that includes /usr/local/bin for carthage.
- To execute test, open the project from IDE (Eclipse or Intellij), then choose testSetDataConnection to Run As TestNG Test. For test on Android, make sure to start an emulator before run. Otherwise test will fail for throwing a SessionNotCreatedException. The error will be similar as "org.openqa.selenium.SessionNotCreatedException: Unable to create a new remote session. Please check the server log for more details. Original error: An unknown server-side error occurred while processing the command. Original error: Could not find a connected Android device in 20054ms.".
- Install appropriate version of chromedriver based on the instruction on this [site](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/web/chromedriver.md). Otherwise will hit error like this: "No Chromedriver found that can automate Chrome '74.0.3729'."