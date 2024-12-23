@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.mia.kotlin.jvm)
//	`java-library`
	alias(libs.plugins.kotlinx.serialization)
	alias(libs.plugins.mia.papermc)
	alias(libs.plugins.mia.copyjar)
	alias(libs.plugins.mia.publication)
	alias(libs.plugins.mia.autoversion)
}


repositories {
	maven("https://repo.mineinabyss.com/releases")
	maven("https://repo.mineinabyss.com/snapshots")
	maven("https://maven.enginehub.org/repo")
	maven("https://repo.nexomc.com/releases")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

dependencies {
	compileOnly(hiddenlibs.minecraft.plugin.worldguard)
	compileOnly(hiddenlibs.minecraft.plugin.geary.papermc)
	compileOnly("com.nexomc:nexo:0.4.0")
}
