pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Tasbih Farzun"
include (":app")
include(":core")
include(":domain")
include(":data")
include(":model")
include(":local")
include(":remote")
include(":common")
include(":common:components")
include(":features")
include(":features:prayertimes")
