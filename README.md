# Test Automation Framework – Login Functionality

## Overview

This repository contains a test automation framework for validating the login functionality of a web
application.  
The framework demonstrates UI-level automation, clean test design, and CI-friendly execution using
modern Java testing tools.

A public demo application is used to avoid testing against any real services or user data.

---

## Tech Stack

- **Language:** Java 17
- **Test Framework:** TestNG
- **UI Automation:** Selenium WebDriver
- **Driver Management:** WebDriverManager
- **Build Tool:** Maven
- **Logging:** SLF4J with Logback
- **Reporting:** ExtentReports
- **CI:** GitHub Actions
- **Browser:** Chrome (headless in CI)

---

## Architecture & Design Decisions

- The framework follows the **Page Object Model (POM)** to separate test intent from UI interaction
  logic.
- Selenium interactions are encapsulated within page classes to improve readability and
  maintainability.
- Explicit waits are preferred over implicit waits to reduce flakiness and improve determinism.
- Browser configuration (headless mode, window size, credential prompts) is handled centrally during
  driver setup.
- Reporting is generated as static HTML to allow easy review without additional tooling.

---

## Test Coverage

The following login scenarios are covered:

- Successful login with valid credentials
- Failed login with invalid credentials
- Validation of error messaging on unsuccessful login

The tests focus on functional correctness rather than visual styling or layout.

---

## How to Run Tests

### Prerequisites

- Java 17+
- Maven 3.8+
- Chrome browser installed

### Run Tests Locally (Headed)

```bash
mvn test
