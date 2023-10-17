plugins {
	kotlin("jvm")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(rootProject)

	implementation("org.mozilla:rhino:1.7.14")
}