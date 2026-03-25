# JFXCentral 2 Copilot Instructions

This document provides comprehensive guidance for AI assistants working on the JFXCentral 2 codebase. It covers build system, project structure, tech stack, architecture patterns, and key conventions.

## 1. Build System

**Build Tool:** Maven (Maven Wrapper provided: `./mvnw` or `./mvnw.cmd` on Windows)

### Key Maven Commands

**Building & Testing:**
- `./mvnw clean install` - Full build with tests
- `./mvnw -B verify` - Standard CI build (used in GitHub Actions)
- `./mvnw clean package` - Build without installing
- `./mvnw test` - Run tests only

**Running the Application:**
- **Desktop (Desktop App):** Run `JFXCentral2App` class from IDE
- **Desktop (via Maven):** `./mvnw -pl app javafx:run` (runs from app module)
- **Web (JPro):** `./mvnw install` then `./mvnw -pl app jpro:run`
- **Mobile:** `./mvnw install && ./mvnw -pl app gluonfx:build gluonfx:package gluonfx:install`

**Single Test Execution:**
```bash
./mvnw -Dtest=StringUtilTest test
./mvnw -Dtest=StringUtilTest#getDomainNameFromValidUrl test
```

**Module-Specific Commands:**
```bash
./mvnw -pl components test              # Test components module only
./mvnw -pl app clean javafx:run         # Build and run desktop app
./mvnw -Pandroid gluonfx:build          # Build for Android
./mvnw -Pdesktop,mac gluonfx:build      # Build for macOS
```

### Key Properties
- **Java Version:** 25 (see `pom.xml: <java.version>25</java.version>`)
- **JavaFX Version:** 25.0.2
- **JUnit Version:** 5.10.0
- **JPro Version:** 2025.3.0
- **Ikonli Version:** 12.3.1

## 2. Project Structure

The project uses a **multi-module Maven structure** with 5 main modules:

### Module Overview

```
jfxcentral2/                   # Root POM (packaging: pom)
├── app/                       # Main desktop/web/mobile application (jar)
│   └── src/main/java/com/dlsc/jfxcentral2/app/
│       ├── JFXCentral2App.java        # Desktop entry point (431 lines)
│       ├── JFXCentral2MobileApp.java  # Mobile entry point
│       ├── JFXCentral2Server.java     # Web/JPro entry point
│       ├── RepositoryManager.java     # Git repo sync & data loading
│       ├── TrayIconManager.java       # System tray support
│       ├── pages/                     # Page implementations
│       │   ├── PageBase.java          # Abstract base for all pages
│       │   ├── CategoryPageBase.java  # Base for category pages
│       │   ├── DetailsPageBase.java   # Base for detail pages
│       │   ├── category/              # ~18 category pages (Blogs, Books, Tools, etc.)
│       │   └── details/               # ~14 detail pages (BlogDetailsPage, etc.)
│       ├── stage/                     # Stage/window management
│       ├── service/                   # Services
│       ├── filters/                   # Page filters
│       └── utils/                     # Utilities (RepositoryUpdater, VideoPane)
│
├── components/                # Reusable UI components library (jar)
│   └── src/main/java/com/dlsc/jfxcentral2/components/
│       ├── ControlBase.java           # Base class for sized/targeted controls
│       ├── ~70 component classes      # Views, boxes, fields (AvatarView, etc.)
│       ├── components/                # Main components (~50 files)
│       │   ├── filters/               # Filter views for different data types
│       │   ├── tiles/                 # Tile views for grid display
│       │   ├── detailsbox/            # Detail box views for item details
│       │   ├── headers/               # Header components
│       │   ├── hamburger/             # Mobile hamburger menu
│       │   ├── gridview/              # Grid view implementations
│       │   ├── overviewbox/           # Overview boxes
│       │   ├── topcontent/            # Top content area
│       │   ├── userprofile/           # User profile components
│       │   └── skins/                 # Skin implementations
│       ├── model/                     # Data models (Badge, CustomTab, Size, etc.)
│       ├── utils/                     # Utilities (StringUtil, SocialUtil, etc.)
│       ├── utilities/                 # Tool utilities (PaintPicker, CSSPlayground, etc.)
│       └── events/                    # Custom events
│   └── src/test/java/                 # Test files (utils, model)
│
├── sampler/                   # Demo/showcase module (jar)
│   └── Demonstrates component usage
│
├── mobile/                    # Mobile-specific code (jar)
│   └── Mobile app implementation details
│
├── iconfont/                  # Icon font resources (jar)
│   └── Icon definitions and resources
│
└── .github/workflows/         # CI/CD pipelines
    ├── build.yml              # Main build job (Linux, JDK 24)
    ├── macos.yml              # macOS build with notarization
    ├── ios.yml                # iOS build
    └── android.yml            # Android build
```

### Data Flow
- **Data Source:** External Git repository (jfxcentral-data) cloned via `RepositoryManager`
- **Data Loading:** `DataRepository.getInstance()` (from external jfxcentral-data dependency)
- **Data Models:** Imported from `com.dlsc.jfxcentral.data.model.*` package

## 3. Tech Stack

### Core JavaFX & UI
- **JavaFX 25.0.2:** Core UI framework (modular components via `javafx-*` modules)
- **JPro 2025.3.0:** Web framework for running JavaFX in browser
- **GluonFX 1.0.25:** Mobile build system for Android/iOS via GraalVM

### UI Component Libraries
- **Ikonli 12.3.1:** Icon management (22+ icon packs: MaterialDesign, FontAwesome, BootstrapIcons, etc.)
- **ControlsFX:** Standard JavaFX controls extensions
- **GemsFX:** DLSC custom controls and utilities
- **AnimateFX 1.3.0:** Animation effects
- **CSSFX:** CSS live reload utility

### Mobile & Device
- **Gluon Attach 4.0.19:** Device APIs (display, lifecycle, storage, browser, video)
- **Gluon AttachExtended:** Extended device APIs

### Data & Version Control
- **JGit (Eclipse):** Git operations for repository management
- **Apache Commons Lang 3:** String utilities

### Testing
- **JUnit Jupiter 5.10.0:** Testing framework
- **Mockito 1.10.19:** Mocking library

### Build & Dev
- **Maven 3.x** with wrapper
- **GraalVM (Gluon):** For native mobile compilation
- **JavaFX Maven Plugin 0.0.8:** Desktop builds
- **Gluon FX Maven Plugin 1.0.25:** Mobile builds

### Routing & Navigation
- **JPro Routing Core (0.6.0-SNAPSHOT):** URL-based page routing and navigation

## 4. Architecture

### Overall Pattern: MVC-like with Pages and Components

```
Application Layer
├── JFXCentral2App (Desktop Entry)
├── JFXCentral2MobileApp (Mobile Entry)
└── JFXCentral2Server (Web Entry)
            ↓
Navigation/Routing Layer
├── JPro Routing (URL ↔ Page mapping)
├── PagePath utilities
└── View routing
            ↓
Page Layer (app/pages/)
├── PageBase (abstract, extends View)
│   ├── CategoryPageBase<T>
│   │   └── ~18 category pages (BlogsCategoryPage, BooksCategoryPage, etc.)
│   ├── DetailsPageBase<T extends ModelObject>
│   │   └── ~14 detail pages (BlogDetailsPage, ToolDetailsPage, etc.)
│   └── Standalone pages (StartPage, LoginPage, ErrorPage, etc.)
            ↓
Component Layer (components/)
├── ControlBase (extends Control)
│   ├── Responsive sizing (Size enum: SMALL, MEDIUM, LARGE)
│   ├── Target support (DESKTOP, BROWSER, MOBILE)
│   └── CSS pseudo-class styling
├── Specialized Components
│   ├── FilterView<T> - Search and filter UI
│   ├── TileViewBase<T> - Grid item rendering
│   ├── DetailsBoxBase - Detail view containers
│   ├── HeaderView - Content headers
│   └── Various UI elements (AvatarView, CommentsView, CustomImageView, etc.)
            ↓
Model Layer (components/model/ + external data)
├── Local models (Badge, CustomTab, Size, Target, etc.)
├── Remote models (DataRepository from jfxcentral-data)
│   ├── Blog, Book, Company, Download, Library, etc.
│   └── Extends ModelObject base
            ↓
Data Access Layer
├── RepositoryManager - Manages Git sync
├── DataRepository - External data provider (jfxcentral.data package)
└── Git-based updates via JGit
```

### Key Architectural Concepts

**1. Responsive Design**
- **Size Property:** Enum-based (SMALL, MEDIUM, LARGE) binding through hierarchy
- **Target Property:** Enum-based (DESKTOP, BROWSER, MOBILE)
- **CSS PseudoClasses:** `:sm`, `:md`, `:lg`, `:sm-md`, `:md-lg`, `:desktop`, `:browser`, `:mobile`
- All components inherit from `ControlBase` which manages these

**2. Page-Based Navigation**
- All pages extend `View` (from JPro routing)
- Pages are registered in `JFXCentral2App.createMapping()`
- URL paths map to page classes via JPro routing
- Each page type has category, detail, or standalone variants

**3. Generic Data Binding**
- `CategoryPageBase<T>` - Takes generic data type, provides filtering and grid display
- `DetailsPageBase<T extends ModelObject>` - Shows details for any model type
- `TileViewBase<T>` - Generic tile rendering for items in grid
- `DetailsBoxBase` - Generic detail box for additional info

**4. Multi-Target Support**
- Same codebase runs on Desktop, Web (browser), and Mobile
- Conditional logic via `Target` property and `isMobile()` checks
- Layout adjustments via `Size` property

## 5. Key Conventions

### Naming Conventions

**Views & Components**
- Suffix `View` for display-only components (e.g., `CommentsView`, `CreditsView`, `AvatarView`)
- Suffix `Box` for container/section components (e.g., `BlogOverviewBox`, `DetailsContentPane`)
- Suffix `Pane` for layout containers (e.g., `DetailsContentPane`, `PrettyScrollPane`)
- Suffix `Page` for routable/navigable pages (e.g., `BlogsCategoryPage`, `BlogDetailsPage`)
- Suffix `Base` for abstract base classes (e.g., `PageBase`, `ControlBase`, `CategoryPageBase`)
- Suffix `Filter` for filtering components (e.g., `BlogsFilterView`, `SearchFilterView`)
- Suffix `Tile` for grid items (e.g., `BlogTileView`, `BookTileView`)
- Suffix `Header` for header components (e.g., `BlogDetailHeader`, `TopMenuBar`)

**Packages**
- `pages/` - Page implementations (organized by type: category/, details/)
- `components/` - Reusable UI components organized by function (filters/, tiles/, detailsbox/, etc.)
- `utils/` - Utility/helper methods (static)
- `model/` - Data model classes
- `service/` - Service classes for business logic
- `stage/` - Window/stage management

**Patterns**
- Pages ending in "Page" → extend PageBase
- Categories ending in "CategoryPage" → extend CategoryPageBase<T>
- Details ending in "DetailsPage" → extend DetailsPageBase<T>
- Models ending in "Box" → for detail display sections

### UI Component Building

**Approach: Programmatic (NOT FXML)**
- All UI is built in Java code, NOT FXML files
- No `.fxml` files in the codebase
- Components built via JavaFX API directly
- Layout uses VBox, HBox, StackPane, etc.

**Example Pattern (from BlogDetailsPage):**
```java
public class BlogDetailsPage extends DetailsPageBase<Blog> {
    @Override
    public Node content() {
        Blog blog = getItem();
        
        // Create header component
        BlogDetailHeader header = new BlogDetailHeader(blog);
        header.sizeProperty().bind(sizeProperty());
        
        // Create overview component
        BlogOverviewBox blogOverviewBox = new BlogOverviewBox(blog);
        blogOverviewBox.sizeProperty().bind(sizeProperty());
        
        // Create details pane with boxes
        DetailsContentPane detailsContentPane = createContentPane();
        detailsContentPane.getCenterNodes().add(blogOverviewBox);
        detailsContentPane.getDetailBoxes().setAll(createDetailBoxes());
        
        return wrapContent(header, detailsContentPane);
    }
}
```

**Size and Target Binding Pattern:**
- All UI components get size/target properties bound to Page level
- Enables responsive styling via CSS pseudo-classes
- Example: `component.sizeProperty().bind(sizeProperty())`

**CSS Styling:**
- Location: `src/main/resources/` in each module
- Applied via `getStyleClass().add("class-name")`
- Pseudo-classes: `:sm`, `:md`, `:lg`, `:desktop`, `:browser`, `:mobile`
- Example: `.my-component:lg { font-size: 16px; } .my-component:sm { font-size: 12px; }`

### Data Loading & Fetching

**Pattern: DataRepository Singleton**
```java
// Get data from repository
DataRepository repo = DataRepository.getInstance();
List<Blog> blogs = repo.getItems(Blog.class);  // Get all items of type
Blog blog = repo.getByID(Blog.class, itemId);  // Get by ID

// In DetailsPageBase:
public DetailsPageBase(ObjectProperty<Size> size, Class<? extends T> clazz, String itemId) {
    super(size, Mode.DARK);
    setItem(DataRepository.getInstance().getByID(clazz, itemId));
}
```

**Initial Data Loading:**
- `RepositoryManager.updateRepository()` - Clones/syncs jfxcentral-data Git repo
- Runs on app startup in `JFXCentral2App`
- Uses JGit for clone/pull operations
- Fallback to Gitee mirror if GitHub unavailable
- Network connectivity check before attempting sync

**Category Page Pattern:**
```java
public class BlogsCategoryPage extends CategoryPageBase<Blog> {
    @Override
    protected Callback<Blog, TileViewBase<Blog>> getTileViewProvider() {
        return BlogTileView::new;
    }
    @Override
    protected SearchFilterView<Blog> createSearchFilterView() {
        return new BlogsFilterView();
    }
}
```

### Package Structure Conventions

**app module:**
```
app/src/main/java/com/dlsc/jfxcentral2/app/
├── JFXCentral2App.java          # Main app entry point
├── JFXCentral2MobileApp.java    # Mobile variant
├── JFXCentral2Server.java       # Web variant (JPro)
├── RepositoryManager.java       # Git & data sync
├── TrayIconManager.java         # System tray
├── pages/                       # All page implementations
│   ├── PageBase.java
│   ├── CategoryPageBase.java
│   ├── DetailsPageBase.java
│   ├── category/                # 18 category pages
│   └── details/                 # 14 detail pages
├── stage/                       # Stage management
├── service/                     # Business services
├── filters/                     # Page-level filters
└── utils/                       # App utilities
```

**components module:**
```
components/src/main/java/com/dlsc/jfxcentral2/
├── components/                  # Main UI components
│   ├── *View.java               # Display components (70+ classes)
│   ├── filters/                 # Search/filter components by type
│   ├── tiles/                   # Grid tile renderers by type
│   ├── detailsbox/              # Detail section boxes by type
│   ├── headers/                 # Header components
│   ├── hamburger/               # Mobile menu
│   ├── skins/                   # Skin implementations
│   ├── gridview/                # Grid views
│   ├── overviewbox/             # Overview boxes
│   ├── topcontent/              # Top bar components
│   └── userprofile/             # User profile
├── model/                       # Data models (Badge, CustomTab, Size, Target, etc.)
├── utils/                       # Utility classes (StringUtil, SocialUtil, etc.)
├── utilities/                   # Tool utilities (PaintPicker, CSSPlayground, etc.)
└── events/                      # Custom events
```

## 6. Testing

### Test Structure
- **Location:** `src/test/java/` in each module (currently components module has most tests)
- **Framework:** JUnit Jupiter 5.10.0
- **Test Files:** Follow naming pattern `*Test.java` or `*Tests.java`
- **Location Paths:** `components/src/test/java/com/dlsc/jfxcentral2/{utils,model}/`

### Test Examples

**Unit Test (StringUtilTest.java):**
```java
package com.dlsc.jfxcentral2.utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {
    @Test
    void getDomainNameFromValidUrl() {
        String url = "https://www.google.com";
        String expectedDomain = "google";
        String actual = StringUtil.getDomainName(url);
        assertEquals(expectedDomain, actual, "Domain mismatch in url: " + url);
    }
    
    @Test
    void getDomainNameFromInvalidUrl() {
        String url = "not a valid url";
        assertNull(StringUtil.getDomainName(url));
    }
}
```

### Test Modules
- **components/src/test/java/**
    - `utils/` - Utility method tests (StringUtilTest, SocialUtilTest, etc.)
    - `model/` - Model tests (BadgeTest, CustomTabTest, etc.)
- Mockito available for mocking
- No integration tests for UI components currently

### Running Tests
```bash
./mvnw test                              # All tests
./mvnw -pl components test               # Components module tests
./mvnw -Dtest=StringUtilTest test        # Specific test class
./mvnw -Dtest=StringUtilTest#getDomainNameFromValidUrl test  # Specific test
```

## 7. Configuration & Properties

### Key Configuration Files

**pom.xml (Root)**
- Module definitions (components, sampler, app, iconfont, mobile)
- Common dependency management
- Plugin repositories (Gluon, Sandec)

**app/pom.xml**
- Main class properties: `JFXCentral2App` (desktop), `JFXCentral2Server` (web), `JFXCentral2MobileApp` (mobile)
- Gluon profiles for platform-specific builds
- JavaFX Maven plugin for desktop
- Gluon FX plugin for mobile builds

**jpro.conf** (app/src/main/resources/)
- JPro web framework configuration

**log4j2.xml** (app/src/main/resources/)
- Logging configuration

**Other Configuration Files**
- `conveyor.conf` - Packaging configuration (at root)
- `.mvn/` - Maven wrapper settings
- `.github/workflows/` - CI/CD workflows

### Environment Variables

**Build Variables:**
- `JAVA_HOME` - Set for GraalVM during mobile builds
- `GRAALVM_HOME` - Gluon GraalVM location
- `GITHUB_RUN_NUMBER` - Used for Android version code in CI
- `GITHUB_TOKEN` - GitHub access for Sonar analysis

**Signing (iOS/Android):**
- `provided.keystore.path` - Keystore file path
- `provided.keystore.password` - Keystore password
- `provided.key.alias` - Key alias
- `provided.key.alias.password` - Key password

## 8. CI/CD: GitHub Actions Workflows

### build.yml (Main Build)
- **Trigger:** On every push (except release commits)
- **Runs on:** Ubuntu latest with JDK 24
- **Job:** `./mvnw -B verify`
- **Analysis:** SonarQube integration (`SONAR_TOKEN`)
- **Purpose:** Standard Maven verify (compile, test, package)

### macos.yml (macOS Build)
- **Purpose:** Build native macOS app (`.pkg`)
- **Tools:** Gluon GraalVM setup, code signing, notarization
- **Build Command:** `./mvnw -Pdesktop,mac gluonfx:build gluonfx:package`
- **Steps:** Import certificates → Build → Notarize with Apple → Upload artifact

### ios.yml (iOS Build)
- **Purpose:** Build iOS app
- **Provisioning:** Manages provisioning profiles
- **Code Signing:** Uses Apple certificates
- **Output:** .app bundle and .ipa file

### android.yml (Android Build)
- **Purpose:** Build Android APK/AAB
- **Build Command:** `./mvnw -Pandroid gluonfx:build gluonfx:package`
- **Signing:** Keystore-based signing
- **Output:** APK and AAB (App Bundle) files

### Build Profiles Used
- `desktop` - Desktop builds
- `mac` - macOS specific (with signing)
- `ios` - iOS specific
- `android` - Android specific
- `browser` - Web (JPro) builds

## 9. Key External Dependencies

### Data Repository
- **Module:** jfxcentral-data (external Git repo)
- **Location:** Cloned to local directory managed by `RepositoryManager`
- **Source:** GitHub (primary) or Gitee mirror (fallback)
- **Models:** Blog, Book, Company, Download, Learn, Library, Person, Tip, Tool, Tutorial, Video, etc.
- **Access:** Via `DataRepository.getInstance()` singleton

### External Libraries
- **GemsFX:** DLSC utility controls
- **Ikonli:** Multi-pack icon system
- **ControlsFX:** Extended JavaFX controls
- **JGit:** Git operations
- **Apache Commons:** Lang3 utilities
- **Gluon Attach:** Mobile device APIs

## 10. Important Entry Points

**Desktop App:** `JFXCentral2App.java` (431 lines)
- `main(String[] args)` - Entry point
- `start(Stage stage)` - JavaFX start method
- `createMapping()` - Page → URL route registration
- Data initialization via `RepositoryManager`

**Mobile App:** `JFXCentral2MobileApp.java`
- Mobile-specific startup
- Uses Gluon Attach for device features

**Web Server:** `JFXCentral2Server.java`
- Minimal entry for JPro web framework
- Likely minimal configuration needed

**Data Manager:** `RepositoryManager.java`
- `updateRepository()` - Sync data from Git
- Network availability checks
- Fallback mirror handling

## 11. Development Guidelines

### Adding New Pages
1. Create class in `app/pages/category/` or `app/pages/details/`
2. Extend `CategoryPageBase<T>` or `DetailsPageBase<T>`
3. Implement abstract methods: `getCategoryTitle()`, `getCategoryDescription()`, etc.
4. Register route in `JFXCentral2App.createMapping()`
5. Implement `content()` method returning wrapped layout

### Adding New Components
1. Create in `components/src/main/java/com/dlsc/jfxcentral2/components/`
2. Extend `ControlBase` for responsive components
3. Support Size and Target properties
4. Add CSS styling in `src/main/resources/`
5. Use pseudo-classes for responsive design

### Adding Tests
1. Create in appropriate `src/test/java/` directory
2. Use `@Test` annotation from `org.junit.jupiter.api`
3. Run with `./mvnw -Dtest=YourTestClass test`

### Building for Different Targets
```bash
# Desktop
./mvnw -pl app javafx:run

# Web (JPro)
./mvnw install && ./mvnw -pl app jpro:run

# Mobile (Android)
./mvnw -Pandroid gluonfx:build gluonfx:package

# Mobile (iOS)
./mvnw -Pios gluonfx:build gluonfx:package

# macOS
./mvnw -Pdesktop,mac gluonfx:build gluonfx:package
```

## 12. Performance & Optimization Tips

- Use `CachingSupplier` for expensive operations (see tests in components)
- Leverage incremental loading via `IncrementalLoading` from JPro routing
- Size properties filter layouts early - don't render hidden content
- Use object pooling for frequently created components
- Lazy-load detail pages until needed

## Summary Table

| Aspect | Details |
|--------|---------|
| **Build Tool** | Maven 3.x with wrapper (./mvnw) |
| **Java Version** | 25 |
| **UI Framework** | JavaFX 25.0.2 |
| **Testing** | JUnit 5.10.0, Mockito |
| **Web Framework** | JPro 2025.3.0 |
| **Mobile** | Gluon FX 1.0.25, GraalVM |
| **Icons** | Ikonli 12.3.1 (22+ packs) |
| **Modules** | app, components, sampler, mobile, iconfont |
| **Entry Points** | JFXCentral2App, JFXCentral2MobileApp, JFXCentral2Server |
| **UI Building** | Programmatic Java (NO FXML) |
| **Routing** | JPro Routing with URL mapping |
| **Data** | External Git repository (jfxcentral-data) |
| **Data Access** | Singleton `DataRepository.getInstance()` |
| **CI/CD** | GitHub Actions (build, macOS, iOS, Android) |
| **Responsive** | Size enum (SMALL/MEDIUM/LARGE) + Target enum (DESKTOP/BROWSER/MOBILE) |

---

**Last Updated:** Based on codebase snapshot (Java 25, JavaFX 25.0.2, JPro 2025.3.0)
