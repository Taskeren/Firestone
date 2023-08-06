plugins {
	kotlin("jvm") version "1.9.0"
	application
	id("com.github.johnrengelman.shadow") version "8.1.1"
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8

	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.mozilla:rhino:1.7.14")

	// logging
	implementation("org.slf4j:slf4j-api:2.0.7")
	implementation("org.slf4j:slf4j-simple:2.0.7")

	// support for json
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

	implementation(kotlin("test"))
}

application {
	mainClass.set("cn.taskeren.firestone.FirestoneKt")
}