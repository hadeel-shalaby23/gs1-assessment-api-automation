# **GS1 API Automation Assessment**
## Overview

**This project is Cucumber Framework API Automation implemented using:**
- Java
- Cucumber
- Testng
- Maven
- REST Assured
- Object-Oriented Programming principles
- Intellij IDEA

**The project covers test scenarios of Books API (CRUD operations)**

**Not all scenarios are covered (as requested in the assessment document)**

**The API base URL is configured inside:**
src/test/resources/config.properties
the base url is configured (Local)

**The book endpoint is maintained separately inside:**
Endpoints.java

## Response Validation

Assertion on returned response code, body and message

**Database assertion should be added (but there is no database connection as this is a Fake API)**

## **Test Execution**

Run the file TestRunner.java

OR

Run using Maven from root using command:
mvn clean test

### Running Tests by Tag

Run only positive scenarios:
mvn test -Dcucumber.filter.tags="@positive"

Run only negative scenarios:
mvn test -Dcucumber.filter.tags="@negative"

## **Requirements**
- Java JDK 17 or newer
- Intellij IDEA
- Maven

## Reports

Cucumber reports are generated under the Maven target directory.

target/cucumber-report.html

cucumber-report.json
