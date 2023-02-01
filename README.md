# utam-java-recipes

UI Test Automation Model (UTAM) is based on the popular Page Object model design pattern commonly used in UI tests. UTAM documentation is [here](https://utam.dev/).

## Initial setup

This repository is a Maven project with examples of UTAM compiler setup and UI tests written with UTAM.

The project uses Java 11 and Maven 4.

1. Clone the repository and install the Maven project.

```shell script
git clone https://github.com/salesforce/utam-java-recipes.git
cd utam-java-recipes
mvn clean install
```

2. Import this project as a Maven project in the IDE of your choice.

## Dependency from Salesforce page objects

In the main pom.xml of this project you will notice dependency from Salesforce page objects:
```xml
<dependency>
    <groupId>com.salesforce.utam</groupId>
    <artifactId>salesforce-pageobjects</artifactId>
    <version>${salesforce.pageobjects.version}</version>
</dependency>
```

Dependency version should match the Salesforce application version that is deployed on the environment under test. 
In this repository version will always be pointing to the latest released Saleforce application deployed on production.
If test environment is not yet updated, please find matching version in [maven central](https://mvnrepository.com/artifact/com.salesforce.utam/salesforce-pageobjects)

## Dependency from UTAM framework

In the main pom.xml of this project you will notice dependency from utam-core, version should be latest 
and compatible with Salesforce page objects artifact (see previous section) if your tests use it:
```xml
<dependency>
   <groupId>com.salesforce.utam</groupId>
   <artifactId>utam-core</artifactId>
   <version>${utam.framework.version}</version>
</dependency>
```
utam-preview module has dependency from UTAM compiler, version should be same as for utam-core:
```xml
<dependency>
    <groupId>com.salesforce.utam</groupId>
    <artifactId>utam-compiler</artifactId>
    <version>${utam.framework.version}</version>
    <scope>runtime</scope>
    <type>jar</type>
</dependency>
```

## Generate Page Objects

The utam-preview module is an example of page objects authoring and compilation. This module also contains Salesforce page objects for default org setup that can be used to build Salesforce UI tests.

__IMPORTANT: Page objects and tests for the Salesforce UI are compatible with application Spring'23__.

> Note: These recipes are designed to work with a generic Salesforce org. If your org has customizations, you might need to modify page objects or tests locally to avoid errors.

To generate page objects, run this maven command from the project root:
```shell script
mvn clean install
```

## Run Salesforce Web UI tests

The utam-tests module contains examples of setup for UTAM page objects usage, test utilities, and Salesforce UI tests.

Preconditions:

- Download chromedriver and geckodriver in the user home directory (returned by `System.getProperty("user.home")`) 
or set the path from a test with `System.setProperty("webdriver.chrome.driver", <path to chrome driver>)` and `System.setProperty("webdriver.gecko.driver", <path to gecko driver>)`

- To log in to a Salesforce org (environment) via the UI at the beginning of the test, add an `env.properties` file to the [utam-tests test resources root](https://github.com/salesforce/utam-java-recipes/tree/main/utam-tests/src/test/resources).

The content of the file should look like this, where "sandbox" is the name of the environment. An `env.properties` file can reference more than one environment.

```properties
sandbox.url=https://sandbox.salesforce.com/
sandbox.username=my.user@salesforce.com
sandbox.password=secretPassword
# sometimes after login URL changes
sandbox.redirectUrl=https://lightningapp.lightning.test1234.salesforce.com/
```

In the login method inside a test, provide the prefix of your environment as a parameter. In this `env.properties` file, the prefix is `sandbox`.

```java
TestEnvironment testEnvironment = getTestEnvironment("sandbox");

  @BeforeTest
  public void setup() {
    setupChrome();
    loginToHomePage(testEnvironment);
  }
```

To run tests in an IDE, for example in IntelliJ IDEA, click on the class or method and choose the option to run a particular test.

Salesforce UI test examples are located in the [utam-tests module](https://github.com/salesforce/utam-java-recipes/tree/main/utam-tests/src/test/java/utam/examples/salesforce/web).

An example of UTAM setup for web tests is in the [base class](https://github.com/salesforce/utam-java-recipes/blob/main/utam-tests/src/test/java/utam/base/UtamWebTestBase.java).

## Run SFDX scratch org test

The force-app module contains custom components and permissions for a scratch org. 
It's a JavaScript module and isn't included in the Maven project.

Before running UI tests, you must complete these prerequisites.

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
> for Dev Hub and CLI setup information. 

### Scratch org setup

To set up a scratch org and login via URL, run the following commands from the project root:

1. If you already had utam-recipes org, delete the previously created org 
```shell script
sfdx force:org:delete -u utam-recipes
```
2. Create a scratch org and provide it with an alias **utam-recipes**:
 ```shell script
sfdx force:org:create -s -f config/project-scratch-def.json -a utam-recipes
```
3. Push force-app to your scratch org:
```shell script
sfdx force:source:push
```
4. Assign the **utam** permission set to the default user:
```shell script
sfdx force:user:permset:assign -n utam
```
> Tip: if this step throws an error `Permission set not found in target org`, run `sfdx plugins:install user` and repeat steps 1-4.

5. Generate a login URL for your scratch org:
```shell script
sfdx force:org:open -p /lightning -r --json
```
This command will print out JSON to your terminal. Copy result.url from the printed JSON and copy it to the `env.properties` file.  
```properties
# scratch org can log in by URL
scratchOrg.sfdx.url=https://<scratch-org-name>.cs22.my.salesforce.com/secur/frontdoor.jsp?sid=<generated-sid>
```
> Tip: If you want to open the scratch org in your local browser, run `sfdx force:org:open`.

## Run Salesforce Mobile test

- Follow the instructions at [Get Started for Mobile](https://utam.dev/guide/get_started_utam#get-started-for-mobile) to set up your local simulator/emulator.
- Make sure Appium and Nodejs are installed in user home.
- Update the following System Environment Variables according to your development machine setup if needed:
For an iOS test, configure the application bundleid, test device name and the full path for the test application:

```java
System.setProperty("app.bundleid", "com.salesforce.chatter");
System.setProperty("ios.device", "iPhone 8 Plus");
System.setProperty("ios.app", getUserHomePath() + "SApp.app");
```

For an Android test, configure the application bundleid, the full path for the test application and application initial activity:

```java
System.setProperty("app.bundleid", "com.salesforce.chatter");
System.setProperty("android.app", getUserHomePath() + "SApp.apk");
System.setProperty("app.activity", "com.salesforce.chatter.Chatter");
```

- Download the [debug build](https://developer.salesforce.com/tools/mobile-debugging) for SalesforceApp iOS and Android.
- Also if you're using an IDE to execute tests, set the environment variables JAVA_HOME, ANDROID_HOME or ANDROID_SDK_ROOT and PATH that includes `/usr/local/bin` for carthage.
- To execute a test, open the project from your IDE (Eclipse or Intellij), then choose testSetDataConnection to Run As TestNG Test. For a test on Android, make sure to start an emulator before the test run. Otherwise, the test will fail for throwing a SessionNotCreatedException. The error will be similar to "org.openqa.selenium.SessionNotCreatedException: Unable to create a new remote session. Please check the server log for more details. Original error: An unknown server-side error occurred while processing the command. Original error: Could not find a connected Android device in 20054ms.".
- Install the appropriate version of chromedriver based on the instructions on this [site](https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/web/chromedriver.md). Otherwise, you will get an error like this: "No Chromedriver found that can automate Chrome '74.0.3729'."
