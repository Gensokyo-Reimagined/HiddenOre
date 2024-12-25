plugins {
	id("java")
	id("maven-publish")
	id("com.gradleup.shadow") version "8.3.5"
}

repositories {
	maven("https://repo.papermc.io/repository/maven-public/")
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
	compileOnly(hiddenlibs.paperapi)
	compileOnly(hiddenlibs.minecraft.plugin.nexo)
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			groupId = "plugins"
			artifact(tasks.shadowJar) {
				artifactId = "HiddenOre"
			}
		}
	}

	repositories {
		maven {
			name = "gensokyoReimagined"
			url = uri("https://repo.gensokyoreimagined.net")
			credentials {
				username = project.findProperty("gensokyoUser")?.toString() ?: System.getenv("GENSOKYOUSER")
				password = project.findProperty("gensokyoToken")?.toString() ?: System.getenv("GENSOKYOTOKEN")
			}
		}
	}
}
