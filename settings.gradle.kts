pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "music_helper"
include(":app")
include(":common:common-api")
include(":common:common-impl")
include(":feature-auth:feature-auth-api")
include(":feature-auth:feature-auth-impl")
include(":feature-listens:feature-listens-api")
include(":feature-listens:feature-listens-impl")
include(":feature-apps:feature-apps-api")
include(":feature-apps:feature-apps-impl")
include(":feature-permission:feature-permission-api")
include(":feature-permission:feature-permission-impl")
include(":feature-analysis:feature-analysis-api")
include(":feature-analysis:feature-analysis-impl")
include(":feature-stats:feature-stats-api")
include(":feature-stats:feature-stats-impl")
include(":feature-settings:feature-settings-api")
include(":feature-settings:feature-settings-impl")
include(":feature-onboarding:feature-onboarding-api")
include(":feature-onboarding:feature-onboarding-impl")
