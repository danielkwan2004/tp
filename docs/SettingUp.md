---
layout: page
title: Setting up and getting started
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Setting up the project in your computer

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**  
Follow the steps in this guide precisely. Deviating from them may cause build or runtime issues.
</div>

### 1. Fork and clone the repository

1. Fork this repository to your own GitHub account.  
2. Clone the fork to your local machine:

   ```bash
   git clone https://github.com/<your-username>/zettel.git
   cd zettel
   ```

### 2. Ensure the correct Java version

Zettel requires **Java 17 or later**.

Check your version:

```bash
java -version
```

If you do not have Java 17 installed, download it from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)  
or use your system’s package manager, for example:

```bash
sudo apt install openjdk-17-jdk
```

### 3. Importing into IntelliJ IDEA (recommended)

1. Open **IntelliJ IDEA**.  
2. Select **File → New → Project from Existing Sources**.  
3. Choose the folder containing the cloned repository.  
4. Configure IntelliJ to use **JDK 17**.
    - Follow the [SE-EDU guide on configuring the JDK](https://se-education.org/guides/tutorials/intellijJdk.html).
5. **Import as a Gradle project**.  
   - Follow the [SE-EDU guide on importing Gradle projects](https://se-education.org/guides/tutorials/intellijImportGradleProject.html).

### 4. Verify setup

Run the main entry point class:

```bash
seedu.zettel.Zettel
```

Try a few commands:

```
new -t "Test" -b "Hello world"
list
exit
```

Then run the test suite:

- In IntelliJ: open `src/test/java` and right-click → **Run Tests in tp.test**.  
- Or via Gradle (if applicable):

  ```bash
  ./gradlew test
  ```

--------------------------------------------------------------------------------------------------------------------

## Before writing code

### 1. Configure the coding style

Follow the [SE-EDU IntelliJ code style guide](https://se-education.org/guides/tutorials/intellijCodeStyle.html)  
to maintain consistent formatting.

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**  
You can optionally enable Checkstyle integration to detect issues as you type.  
See [Using Checkstyle](https://se-education.org/guides/tutorials/checkstyle.html).
</div>

### 2. Learn the architecture

Before modifying or extending the app, review the  
[Architecture section](DeveloperGuide.md#architecture) in the Developer Guide.  
It explains how Zettel’s core components (`Zettel`, `UI`, `Parser`, `Command`, `Storage`, and `Note`) interact.

### 3. Continuous Integration (CI)

GitHub Actions are preconfigured in `.github/workflows`.  
Every push and pull request automatically:

- Compiles the code  
- Runs the full JUnit test suite  
- Reports build status on GitHub

No extra setup required.

--------------------------------------------------------------------------------------------------------------------

## Optional Developer Tutorials

To get comfortable with the Zettel codebase, you can try:

- **Tracing Code Flow** — follow how a `new` command is parsed and executed.  
- **Adding a New Command** — add a command like `count` following the Command Pattern.  
- **Removing Unused Fields** — practice safe refactoring using IntelliJ’s tools.

--------------------------------------------------------------------------------------------------------------------
