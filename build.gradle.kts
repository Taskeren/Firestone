plugins {
	kotlin("jvm") version "1.9.0"
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
	// logging
	api("org.slf4j:slf4j-api:2.0.7")
	api("org.slf4j:slf4j-simple:2.0.7")

	// support for json
	api("com.google.code.gson:gson:2.10.1")

	implementation(kotlin("test"))
}