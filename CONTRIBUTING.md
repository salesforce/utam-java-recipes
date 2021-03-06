# Contributing to UTAM Java Recipes

We encourage the developer community to contribute to UTAM.

> **Note: It might take months before we can review a pull request. Please be patient!**

This guide has instructions to install, build, test, and contribute to the project.

-   [Code of Conduct](#code-of-conduct)
-   [Requirements](#requirements)
-   [Installation](#installation)
-   [Testing](#testing)
-   [Editor Configurations](#editor-configurations)
-   [Git Workflow](#git-workflow)

## Code of Conduct

The UTAM Java Recipes project has a [Code of Conduct](https://github.com/salesforce/utam-java-recipes/blob/master/CODE_OF_CONDUCT.md) to
which all contributors must adhere.

## Requirements

-   [Java JDK](https://www.oracle.com/java/technologies/downloads/#java11) >= 11
-   [Maven](https://yarnpkg.com/) >= 3.8.4

## Installation

[Set up SSH access to GitHub][setup-github-ssh] if you haven't done so already.

### 1) Fork the repository

We recommend that you [fork][fork-a-repo] the [salesforce/utam-java-recipes](https://github.com/salesforce/utam-java-recipes) repo.

After you fork the repo, [clone][clone-a-repo] your fork in your local workspace:

```bash
$ git clone git@github.com<YOUR-USERNAME>/utam-java-recipes.git
$ cd utam-java-recipes
```

### 2) Install dependencies

_We use [Maven](https://maven.apache.org/) for dependency management._

### 2) Building UTAM Java Recipes

```bash
$ mvn clean install -DskipTests
```

This command does several things:

1. It updates the dependencies of the project, downloading from the Maven Central repository,
   if necessary.
2. It generates the Java source code for the UTAM Page Objects
3. It compiles the Java code of the project.

The Maven project file will build two artifacts:

- `utam-preview`: library containing the example compiled UTAM Page Objects
- `utam-tests`: the sample tests used to demonstrate UTAM Page Objects

The sample tests will not be run when this command is executed.

## Testing

### Configuration

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

### Sample UTAM Tests

We use [TestNG](https://testng.org) for the tests in this repository.

Run tests by executing the following command from the repository's root:

```bash
$ mvn test
```

Tests can also be run and debugged using the TestNG integraation plugin for a number of Java-aware
IDEs. UTAM developers usually use [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/ide/).

## Editor Configurations

Configure your editor to use our lint and code style rules to speed up the code review process!

### Code Style

UTAM Java Recipies uses the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
Format settings definition files for importing into IDEs are available for
[Eclipse](https://github.com/google/styleguide/blob/gh-pages/eclipse-java-google-style.xml)
and [IntelliJ IDEA](https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml).

## Git Workflow

The process of submitting a pull request is straightforward and generally follows the same pattern each time:

1. [Fork the UTAM Java repo](#fork-the-utam-java-recipes-repo)
2. [Create a feature branch](#create-a-feature-branch)
3. [Make your changes](#make-your-changes)
4. [Rebase](#rebase)
5. [Check your submission](#check-your-submission)
6. [Create a pull request](#create-a-pull-request)
7. [Update the pull request](#update-the-pull-request)
8. [Commit Message Guidelines](#commit-message-conventions)

### Fork the UTAM Java Recipes repo

[Fork][fork-a-repo] the [salesforce/utam-java-recipes](https://github.com/salesforce/utam-java-recipes) repo. Clone your fork in your local
workspace and [configure][configuring-a-remote-for-a-fork] your remote repository settings.

```bash
$ git clone git@github.com:<YOUR-USERNAME>/utam-java-recipes.git
$ cd utam-java-recipes
$ git remote add upstream git@github.com:salesforce/utam-java-recipes.git
```

### Create a feature branch

```bash
$ git checkout main
$ git pull origin main
$ git checkout -b <name-of-the-feature>
```

### Make your changes

Modify the files, lint, format and commit your code using the following commands:

```bash
$ git add <path/to/file/to/commit>
$ git commit
$ git push origin <username>/<name-of-the-feature>
```

Commit your changes using a descriptive commit message that follows our [Commit Message Guidelines](#commit-message-conventions). Adherence
to these conventions is necessary because release notes will be automatically generated from these messages. NOTE:
optional use of _git cz_ command triggers interactive semantic commit, which prompts user with commit related questions,
such as commit type, scope, description, and breaking changes. Use of _git cz_ is optional but recommended to ensure
format consistency.

The above commands will commit the files into your feature branch. You can keep pushing new changes into the same branch
until you are ready to create a pull request.

### Check your submission

#### Test and lint your changes
Test your changes using the unit tests by running the following command:

```bash
mvn test
```

Note that a correct submission should contain passing unit tests for the modified code.

### Rebase

Sometimes your feature branch will get stale with respect to the master branch, and it will require a rebase. The
following steps can help:

```bash
$ git fetch upstream
$ git checkout main
$ git pull
$ git merge upstream/main
$ git push
$ git checkout <name-of-the-feature>
$ git rebase main
```

_note: If no conflicts arise, these commands will ensure that your changes are applied on top of the master branch. Any
conflicts will have to be manually resolved._

### Create a pull request

If you've never created a pull request before, follow [these instructions][creating-a-pull-request].

#### Pull Request Title

A pull request title should follow [conventional commit](#commit-message-conventions) format and is automatically validated by our CI.

```shell
ex:
commit-type(optional scope): commit description. ( NOTE: space between column and the message )

Types: build, chore, ci, docs, feat, fix, perf, proposal, refactor, release, revert, style, test, wip.
Scope: The scope should be the name of the documentation section affected (guide, tutorial, home, etc.)
```

### Update the pull request

```bash
$ git fetch origin
$ git rebase origin/${base_branch}

# If there were no merge conflicts in the rebase
$ git push origin ${feature_branch}

# If there was a merge conflict that was resolved
$ git push origin ${feature_branch} --force
```

_note: If more changes are needed as part of the pull request, just keep committing and pushing your feature branch as
described above and the pull request will automatically update._

### Commit Message Conventions

Git commit messages have to be formatted according to a well defined set of rules. This leads to **more readable
messages** that are easy to follow when looking through the **project history**.

#### Commit Message Format

Each commit message consists of a **header**, a **body** and a **footer**. The header has a special format that includes
a **type**, a **scope** and a **subject**:

```
<type>(<scope>): <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

The **header** is mandatory and the **scope** of the header is optional.

Any line of the commit message can't be longer than 100 characters! This allows the message to be easier to read on
GitHub as well as in various git tools.

Footer should contain a
[closing reference to an issue](https://help.github.com/articles/closing-issues-via-commit-messages/) if any.

```
docs(changelog): update change log to beta.5
```

```
fix(release): need to depend on latest rxjs and zone.js

The version in our package.json gets copied to the one we publish, and users need the latest of these.
```

#### Reverting a commit

If the commit reverts a previous commit, it should begin with `revert: `, followed by the header of the reverted commit.
In the body it should say: `This reverts commit <hash>.`, where the hash is the SHA of the commit being reverted.

#### Commit Type

Must be one of the following:

-   **build**: Changes that affect the build system or external dependencies (example scopes: gulp, broccoli, npm)
-   **chore**: Other changes that don't modify src or test files
-   **ci**: Changes to our CI configuration files and scripts (example scopes: Travis, Circle, BrowserStack, SauceLabs)
-   **docs**: Documentation only changes
-   **feat**: A new feature
-   **fix**: A bug fix
-   **perf**: A code change that improves performance
-   **refactor**: A code change that neither fixes a bug nor adds a feature
-   **revert**: Reverts a previous commit
-   **style**: Changes that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)
-   **test**: Adding missing tests or correcting existing tests

#### Commit Scope

The scope should be the name of the package affected, as perceived by the person reading the changelog.

There are currently a few exceptions to the "use package name" rule:

-   **packaging**: used for changes that change the Maven package definition, e.g. public path changes,
    pom.xml changes done to all packages, file/format changes, changes to dependencies, etc.
-   **changelog**: used for updating the release notes in CHANGELOG.md
-   none/empty string: useful for `style`, `test` and `refactor` changes that are done across all packages (e.g.
    `style: add missing semicolons`)

#### Commit Subject

The subject contains a succinct description of the change:

-   use the imperative, present tense: "change" not "changed" nor "changes"
-   don't capitalize first letter
-   no dot (.) at the end

#### Commit Body

Just as in the **subject**, use the imperative, present tense: "change" not "changed" nor "changes". The body should
include the motivation for the change and contrast this with previous behavior.

#### Commit Footer

The footer should contain any information about **Breaking Changes** and is also the place to reference GitHub issues
that this commit **closes**.

[clone-a-repo]: https://docs.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository
[fork-a-repo]: https://help.github.com/en/articles/fork-a-repo
[configuring-a-remote-for-a-fork]: https://help.github.com/en/articles/configuring-a-remote-for-a-fork
[markdown-guide]: https://www.markdownguide.org/
[setup-github-ssh]: https://help.github.com/articles/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent/
[creating-a-pull-request]: https://help.github.com/articles/creating-a-pull-request/
