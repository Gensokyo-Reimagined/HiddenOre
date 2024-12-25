pluginManagement {
	repositories {
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
	versionCatalogs {
		create("hiddenlibs").from(files("gradle/hiddenlibs.versions.toml"))
	}
}

rootProject.name = "HiddenOre"
