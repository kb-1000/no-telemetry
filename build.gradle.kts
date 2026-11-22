/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 */
plugins {
	// Minecraft 26.1+ is unobfuscated, so the non-remapping Loom is used and no refmaps are generated.
	id("net.fabricmc.fabric-loom") version "1.19-SNAPSHOT"
	`maven-publish`
}

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.
	maven(url = "https://maven.minecraftforge.net") {
		name = "Forge"
		metadataSources {
			ignoreGradleMetadataRedirection()
			mavenPom()
		}
	}

	maven(url = "https://maven.neoforged.net/releases") {
		name = "NeoForge"
		metadataSources {
			ignoreGradleMetadataRedirection()
			mavenPom()
		}
	}
}

val archives_base_name = providers.gradleProperty("archives_base_name").get()
val mod_version = providers.gradleProperty("mod_version").get()
val maven_group = providers.gradleProperty("maven_group").get()
val minecraft_version = providers.gradleProperty("minecraft_version").get()
val loader_version = providers.gradleProperty("loader_version").get()
val forge_version = providers.gradleProperty("forge_version").get()
val neofml_version = providers.gradleProperty("neofml_version").get()

base.archivesName.set(archives_base_name)
version = mod_version
group = maven_group

dependencies {
	// To change the versions, see the gradle.properties file
	minecraft("com.mojang:minecraft:${minecraft_version}")
	implementation("net.fabricmc:fabric-loader:${loader_version}")
	compileOnly("net.minecraftforge:javafmllanguage:${forge_version}") {
		isTransitive = false
	}
	compileOnly("net.minecraftforge:fmlloader:${forge_version}") {
		isTransitive = false
	}
	compileOnly("net.neoforged.fancymodloader:loader:${neofml_version}") {
		isTransitive = false
	}
	compileOnly("org.apache.maven:maven-artifact:3.9.16") {
		isTransitive = false
	}
}

tasks.processResources {
	// Captured at configuration time; reading project.version inside filesMatching is deprecated in Gradle 9.
	val modVersion = project.version.toString()
	inputs.property("version", modVersion)

	filesMatching("fabric.mod.json") {
		expand("version" to modVersion)
	}
}

tasks.withType<JavaCompile> {
	// ensure that the encoding is set to UTF-8, no matter what the system default is
	// this fixes some edge cases with special characters not displaying correctly
	// see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
	// If Javadoc is generated, this must be specified in that task too.
	options.encoding = "UTF-8"

	// The oldest supported version still uses Java 16.
	options.release.set(16)
}

java {
	sourceCompatibility = JavaVersion.VERSION_16
	targetCompatibility = JavaVersion.VERSION_16

	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()
    withJavadocJar()
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${archives_base_name}"}
	}

	manifest {
		attributes(
			"MixinConfigs" to "no-telemetry-forge.mixins.json",
			"Implementation-Version" to project.version,
		)
	}
}

// configure the maven publication
publishing {
	publications {
		create("mavenJava", MavenPublication::class) {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
