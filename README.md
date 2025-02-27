# Marketplace App

A modern Android application built with Kotlin and Jetpack Compose that allows users to search for
products, view search results, and see detailed product information. The project leverages a clean
architecture, using MVVM along with Paging3, Hilt for dependency injection, Ktor for networking, and
Material3 for theming. Comprehensive tests and a CI/CD pipeline using GitHub Actions is configured
to generate code coverage and documentation reports.

## Features

- **Search:**
    - Search screen with a search bar to input queries.
    - Reactive UI updates via ViewModels and state flows.

- **Product Listing and Details:**
    - Paginated list of products using Paging3.
    - Detail screen showing product images, pricing, and attributes.
    - Adaptive two-pane layout for larger screens (list-detail panel).

- **Theming and UI:**
    - Custom Material3 theme with dynamic light/dark modes.
    - Responsive Compose layouts and adaptive navigation.

- **Networking:**
    - Network operations implemented using Ktor.
    - Custom network client with error handling and logging.

- **Dependency Injection:**
    - Hilt is used throughout the app for dependency injection.

- **Connectivity Observation:**
    - Real-time network connectivity monitoring with error banners when offline.

- **Testing and Documentation:**
    - Code coverage reports generated via Jacoco.
    - Documentation generated via Dokka.

## Project Structure

The project is organized into several modules grouped into two main categories: Core and Feature,
along with the App Module that ties everything together.

![Screenshot 2025-02-27 at 6 53 40 AM](https://github.com/user-attachments/assets/a4fd9a99-7035-47b9-9a87-cf61ada2651b)

### App Module

The App Module is the entry point of the application. It ties together the core and feature modules
and configures application-wide settings such as dependency injection and theming. Key components
include:

- **MainActivity:**
  Hosts the main UI using Jetpack Compose. It sets up the theme ([MarketplaceAppTheme]), observes
  network connectivity, and handles navigation between screens using [NavHost].
  The MainActivity is annotated with [@AndroidEntryPoint] to integrate Hilt for dependency
  injection.

- **Application Class:**
  The application class (e.g., [MarketPlaceApplication]) is annotated with [@HiltAndroidApp] to
  initialize Hilt and its dependency container, making DI available throughout the app.

- **Util:**  
  Includes utility classes such as the connectivity observer ([AndroidConnectivityObserver]).

### Core Modules

These modules provide the foundational functionality and services used across the application:

- **Data:**  
  Contains network client implementations (e.g., [KtorNetworkClient]).

- **Domain:**  
  Defines utility wrappers such as [Resource] and [UiText].

- **Logging:**  
  Provides logging interfaces ([Logger]) and implementations (e.g., [StdLoggerImpl]) for consistent
  logging across the app.

- **Navigation & Theming:**  
  Contains the application's routing logic (e.g., [Route]) and theming configurations (
  e.g., [MarketplaceAppTheme], color schemes, [Typography]).

### Feature Modules

These modules encapsulate specific application features and UI components:

- **Data:**
  Contains repositories (e.g., [SearchRepositoryImpl]), DTOs, and mappers.

- **Domain:**
  Defines business models, use cases (e.g., [SearchUseCase]).

- **Presentation:**  
  Houses UI screens, components, and built entirely using Jetpack Compose (
  e.g., [SearchScreen], [ListScreen], [DetailScreen], [ProductItem], [SearchBar], [AdaptiveListDetailPanel])
  and interacts with ViewModels (e.g., [SearchScreenViewModel], [ListScreenViewModel]) to display
  data and respond to user interactions.

## Convention Plugins

The project uses convention plugins (located in the build-logic directory) to standardize build
configurations across modules. These plugins configure tasks like Jacoco and Dokka, ensuring
consistent settings and simplifying maintenance.

## Getting Started

### Prerequisites

- **Android Studio** Ladybug Feature Drop or newer.
- **Kotlin:** 1.7+ (as per project configuration).
- **Gradle:** Version specified in the project.
- **Device/Emulator:** Android device or emulator running API level 21 or above.

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/yourusername/marketplace-app.git
   cd marketplace-app
   ```

2. **Open in Android Studio:**

    - Open Android Studio.
    - Select "Open an existing project" and choose the repository folder.
    - Let Gradle sync and build the project.

### Running the App

- Use Android Studio's Run/Debug feature to deploy the app to an emulator or connected device.

## Documentation Generation

- **Dokka:**  
  To generate the project documentation locally, run:
  ```bash
  ./gradlew dokkaHtmlMultiModule
  ```
  The HTML documentation will be generated in `build/dokka/htmlMultiModule`.
  
- **Jacoco:**  
  To generate a code coverage report locally, run:
  ```bash
  ./gradlew jacocoProjectCoverageReport
  ```
  The report can be found at `build/reports/jacoco/jacocoProjectCoverageReport/html`.

## Continuous Integration (CI)

This project uses GitHub Actions for CI. The workflow performs the following tasks:

- **Sets up JDK 11** and caches Gradle dependencies for faster builds.
- Runs Gradle tasks: `clean`, `build`, `jacocoTestReport`, and `dokkaHtmlMultiModule`.
- Uploads the Jacoco code coverage report and Dokka documentation as artifacts.

## Contributing

Contributions are welcome! Feel free to fork the repository, make changes, and submit a pull
request. For major changes, please open an issue first to discuss what you would like to change.

---
