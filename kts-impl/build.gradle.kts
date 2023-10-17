import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure

plugins {
	kotlin("jvm")
	distribution
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("com.palantir.git-version") version "3.0.0"
}

val gitVersion: Closure<String> by extra
val versionDetails: Closure<VersionDetails> by extra

version = identifiedVersion()

repositories {
	mavenCentral()
}

dependencies {
	api(rootProject)

	runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:1.9.0")
	runtimeOnly("org.jetbrains.kotlin:kotlin-script-runtime:1.9.0")
	testImplementation(kotlin("script-runtime"))
}

tasks.shadowJar {
	mergeServiceFiles()

	archiveFileName.set("firestone.jar")

	manifest {
		attributes["Main-Class"] = "cn.taskeren.firestone.kt.MainKt"
		attributes["Implementation-Title"] = project.name
		attributes["Implementation-Version"] = project.version
	}
}

distributions {
	main {
		distributionBaseName.set("firestone-kts")

		contents {
			from(tasks.shadowJar)
			from("../LICENSE")
			from("../firestone.bat")
		}
	}
}

/**
 * Versioning the project in GT: New Horizons style.
 */
fun identifiedVersion(): String {
	val details = versionDetails()
	val isDirty = gitVersion().endsWith(".dirty")
	var branchName = details.branchName ?: "master"
	if(branchName.startsWith("origin/")) {
		branchName = branchName.substring(7)
	}
	branchName = branchName.replace(Regex("[^a-zA-Z0-9-]+"), "-")
	var identifiedVersion = details.lastTag ?: details.gitHash
	if(details.commitDistance > 0) {
		identifiedVersion += "-${branchName}.${details.commitDistance}+${details.gitHash}"
		if(isDirty) {
			identifiedVersion += "-dirty"
		}
	} else if(isDirty) {
		identifiedVersion += "-${branchName}+${details.gitHash}-dirty"
	}
	return identifiedVersion
}
