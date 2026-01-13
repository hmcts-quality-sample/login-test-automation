# Test Automation Framework – Login & Secure Area

## Overview

This repository contains a **deliberately scoped UI test automation framework** built as part of a
**Senior SDET / Developer in Test assessment**.

The goal of this project is **not exhaustive test coverage**, but to clearly demonstrate:

- Sound automation architecture and design decisions
- Maintainable Page Object Model (POM) implementation
- Robust synchronisation and session handling
- Clear expression of test intent using Given–When–Then
- CI-friendly execution with transparent reporting
- Pragmatic trade-offs appropriate for a real-world codebase

A **public demo application** is used for this purpose:  
https://the-internet.herokuapp.com

This ensures:
- No interaction with real services or data
- Predictable, repeatable behaviour
- Focus remains on test design rather than application complexity

---

## Tech Stack

- **Language:** Java 21
- **Test Framework:** TestNG
- **UI Automation:** Selenium WebDriver
- **Driver Management:** WebDriverManager
- **Build Tool:** Maven
- **Logging:** SLF4J + Logback
- **Reporting:** Allure Reports (HTML)
- **CI/CD:** GitHub Actions
- **Browser:** Chrome
  - Headless in CI
  - Headed locally (configurable)

---

## Architecture & Design Decisions

### Page Object Model (POM)

The framework enforces a **strict Page Object Model separation**:

- `LoginPage`
  - Login form interaction
  - Validation errors
  - Login page state checks
- `SecurePage`
  - Secure-area validation
  - Success banner behaviour
  - Logout and session state

Tests **do not reference locators directly**.  
All UI knowledge is encapsulated within page objects to ensure:

- Readable test intent
- Minimal coupling to DOM structure
- Easier maintenance as the UI evolves

---

### Driver Management

- Browser creation is centralised in `DriverFactory`
- Chrome configuration is applied once and reused consistently:
  - Headless execution for CI
  - Deterministic window sizing
  - Password manager and security prompt suppression
- Environment-driven behaviour:
  - `HEADLESS=true` → CI execution
  - Default → headed local execution

This mirrors how real-world test frameworks scale across environments.

---

### Synchronisation Strategy

The framework deliberately avoids relying on implicit timing assumptions.

- Explicit waits are used for:
  - Secure page readiness
  - URL transitions
  - Clickable elements (e.g. logout)
- Page readiness is hardened by validating:
  - Expected URL
  - Required DOM elements

This prevents false positives caused by early interactions and reflects
production-grade Selenium usage.

---

### Assertions & Diagnostics

- Assertion messages are:
  - Centralised in `constants.Strings`
  - Prefixed with `ASSERT:` to clearly separate test failures from application messages
- Text validation uses **contains**, not exact matching, to reduce brittleness
- Failures are designed to be:
  - Immediately understandable in CI logs
  - Clearly visible in Allure reports

The intent is fast diagnosis rather than verbose output.

---

## Test Coverage

The implemented tests focus on **behavioural correctness**, not UI styling.

### Authentication

- Valid login displays the expected success banner
- Invalid username produces the correct validation error
- Invalid password produces the correct validation error
- Credentials are validated as case-sensitive

### Authorization

- Direct navigation to the secure page without authentication is denied
- Secure content cannot be accessed after logout
- Session enforcement is explicitly validated

### Post-login Behaviour

- Secure page remains accessible after refresh
- Success banner is shown only on initial login
- Banner state does not incorrectly persist

### Logout

- User is redirected to the login page
- Logout confirmation message is displayed
- Session is invalidated immediately

---

## How to Run Tests

### Prerequisites

- Java 21+
- Maven 3.8+
- Chrome (for local execution)

---

### Run Tests Locally (Headed)

```bash
mvn test
```

## Running the CI Workflow Manually (GitHub Actions)

In addition to running automatically on `push` and `pull_request`, the CI workflow can be
triggered manually from the GitHub Actions UI.

This is useful for:

- Re-running tests without creating a new commit
- Demonstrating CI execution during assessment
- Generating a fresh Allure report on demand

---

### How to Trigger the Workflow Manually

1. Open the repository on GitHub
2. Navigate to **Actions**
3. Select the workflow:  
   **CI + Publish Allure Report (GitHub Pages)**
4. Click **Run workflow**
5. Choose the branch (`main`)
6. Click **Run workflow**

The workflow will start immediately and run the full pipeline:

- Build
- Test execution (headless Chrome)
- Allure report generation
- GitHub Pages publication (on `main` branch only)

---

### Viewing the Allure Report

Once the workflow completes successfully:

1. Go to **Actions**
2. Open the completed workflow run
3. Scroll to the **Deploy** job
4. Click the GitHub Pages URL shown in the deployment step

The Allure HTML report is published as a static site and remains accessible until the next deployment.

---

### Notes

- Manual runs behave exactly the same as automated runs
- Reports are always generated, even if tests fail
- Deployment to GitHub Pages occurs only from the `main` branch

This ensures reproducibility and prevents accidental publication from feature branches.
