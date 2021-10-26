# utam-java-recipes

Maven project with examples of compiler setup and testing with UTAM Java.
To setup import as maven project and run `mvn clean install`.
Project uses Java 11 and maven 4.


## utam-pageobjects
Module with an example of setup for Page Objects authoring and compilation.

- JSON files are located under resources and should be named `<pageObjectName>.utam.json`
- Imperative extensions should be located under java sources in the package with same prefix as a generated 
Page Object, but with "utils" instead "pageobjects", for example utilities for `utam.mynamespace.pageobjects.MyComponent` 
should be under `utam.mynamespace.utils.MyUtils`, there is no naming convention for a class name with the utilities.

## utam-tests
Example of setup for UTAM Page Objects consumption. 
Tests from this module can be run by TestNG runner from IDE.


