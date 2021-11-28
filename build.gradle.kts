plugins {
	id("fabric-loom") version "0.10-SNAPSHOT"
	id("io.github.juuxel.loom-quiltflower-mini") version "1.1.0"
	id("maven-publish")
}

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.
	maven(url = "https://maven.minecraftforge.net") {
		name = "Forge"
	}
}

val archives_base_name: String by project.ext
val mod_version: String by project.ext
val maven_group: String by project.ext
val minecraft_version: String by project.ext
val yarn_mappings: String by project.ext
val loader_version: String by project.ext
val forge_version: String by project.ext

base.archivesName.set(archives_base_name)
version = mod_version
group = maven_group

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${minecraft_version}")
	mappings("net.fabricmc:yarn:${yarn_mappings}:v2")
	modImplementation("net.fabricmc:fabric-loader:${loader_version}")
	compileOnly("net.minecraftforge:javafmllanguage:${forge_version}")
}

tasks.processResources {
	inputs.property("version", project.version)

	filesMatching("fabric.mod.json") {
		expand("version" to project.version)
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
			"MixinConfigs" to "no-telemetry.mixins.json",
			"Implementation-Version" to project.version
		)
	}
}

// configure the maven publication
publishing {
	publications {
		val mavenJava by creating(MavenPublication::class) {
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
