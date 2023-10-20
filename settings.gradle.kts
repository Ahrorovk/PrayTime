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
include(":features")
include(":features:praytime")
include(":core")
include(":domain")
include(":data")
include(":data:model")
include(":data:local")
include(":data:repository")
include(":common")
include(":common:components")
