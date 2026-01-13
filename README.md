# Test Automation Framework – Login Functionality

## Overview

This repository contains a UI test automation framework for validating the login functionality of a web application.

The framework is designed to demonstrate:
- clear test intent
- maintainable automation architecture
- reliable execution in both local and CI environments
- readable reporting suitable for technical review

A publicly available demo application is used to ensure no real services, credentials, or user data are exercised.

---

## Tech Stack

- **Language:** Java 21
- **Test Framework:** TestNG
- **UI Automation:** Selenium WebDriver
- **Driver Management:** WebDriverManager
- **Build Tool:** Maven
- **Logging:** SLF4J with Logback
- **Reporting:** Allure (HTML)
- **CI:** GitHub Actions
- **Browser:** Chrome
  - Headless in CI
  - Headed when running locally

---

## Architecture & Design Decisions

- The framework follows the **Page Object Model (POM)** to separate test intent from UI interaction logic.
- Page classes encapsulate Selenium interactions, improving readability and reducing duplication.
- Test classes focus on behaviour and assertions, not UI mechanics.
- Assertion logic is centralised via reusable helpers to ensure consistent, readable failure output.
- Browser setup (headless mode, window size, and Chrome security prompts) is handled centrally via a driver factory.
- Explicit waits are preferred; implicit waits are kept minimal to reduce flakiness.
- A `testng.xml` file is used **only** to provide meaningful suite and test names for reporting purposes.
- Test discovery remains annotation-based.

---

## Test Coverage

The following login scenarios are covered:

- Successful login with valid credentials
- Failed login with an invalid username
- Failed login with an invalid password
- Validation of success and error banners and their messages

The tests focus on functional correctness rather than visual styling or layout.

---

## How to Run Tests

### Prerequisites

- Java 21+
- Maven 3.8+
- Chrome (installed locally for headed runs)

### Run Tests Locally (Headed)

By default, tests run in headed mode when executed locally:

```bash
mvn test
