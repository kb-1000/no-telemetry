import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.FilterReader
import java.io.Reader
import java.io.StringReader

plugins {
	id("fabric-loom") version "1.0-SNAPSHOT"
	id("io.github.juuxel.loom-quiltflower") version "1.8.0"
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

sourceSets {
	val headers by creating {
		java {
			compileClasspath += main.get().compileClasspath
		}
	}
	main {
		java {
			compileClasspath += headers.output
		}
	}
}

dependencies {
	// To change the versions, see the gradle.properties file
	minecraft("com.mojang:minecraft:${minecraft_version}")
	mappings("net.fabricmc:yarn:$minecraft_version+build.$yarn_mappings:v2")
	modImplementation("net.fabricmc:fabric-loader:${loader_version}")
	compileOnly("net.minecraftforge:javafmllanguage:${forge_version}") {
		isTransitive = false
	}
	compileOnly("net.minecraftforge:fmlloader:${forge_version}") {
		isTransitive = false
	}
	compileOnly("org.apache.maven:maven-artifact:3.8.7") {
		isTransitive = false
	}
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

tasks.javadoc {
	classpath += sourceSets["headers"].output
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
/*object O {
	class RefmapModifier(reader: Reader) : FilterReader(run {
		val text = reader.readText()
		val gson = GsonBuilder().setPrettyPrinting().create()
		val tree = gson.fromJson(text, JsonObject::class.java)
		for (mappings in arrayOf(tree.getAsJsonObject("mappings"), tree.getAsJsonObject("data").getAsJsonObject("named:intermediary"))) {
			mappings.getAsJsonObject("de/kb1000/notelemetry/mixin/OptionsScreenMixin").addProperty("Lnet/minecraft/class_7845\$class_7939;method_47612(Lnet/minecraft/class_339;)Lnet/minecraft/class_339;", "Lnet/minecraft/class_7845\$class_7939;method_47612(Lnet/minecraft/class_339;)Lnet/minecraft/class_339;")
		}
		StringReader(gson.toJson(tree))
	})
}*/

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${archives_base_name}"}
	}

	manifest {
		attributes(
			"MixinConfigs" to "no-telemetry.mixins.json",
			"Implementation-Version" to project.version,
		)
	}

	/*filesMatching("no-telemetry-refmap.json") {
		filter(O.RefmapModifier::class.java)
	}*/
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
