import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val junitVersion = "5.6.2"
val koinVersion = "2.1.5"
val jacksonVersion = "2.11.0"

buildscript {
	repositories {
		jcenter()
	}
	
	dependencies {
		classpath(kotlin("gradle-plugin", version = "1.4.10"))
	}
}

plugins {
	kotlin("jvm") version "1.4.10"
}

allprojects {
	apply(plugin = "kotlin")
	apply(plugin = "idea")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	
	group = "rs.dusk"
	version = "0.0.1"
	
	java.sourceCompatibility = JavaVersion.VERSION_14
	
	repositories {
		mavenCentral()
		mavenLocal()
		jcenter()
	}
	
	dependencies {
		// Dusk
		implementation(group = "rs.dusk.core", name = "network", version = "0.1.2")
		implementation(group = "rs.dusk.core", name = "utility", version = "0.1.2")
		
		// Kotlin
		implementation(kotlin("stdlib-jdk8"))
		implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.4.2")
		
		// Cache
		implementation(group = "com.displee", name = "rs-cache-library", version = "6.4")
		
		// Dependency Injection
		implementation(group = "org.koin", name = "koin-core", version = koinVersion)
		implementation(group = "org.koin", name = "koin-logger-slf4j", version = koinVersion)
		
		// Reflection
		implementation(group = "io.github.classgraph", name = "classgraph", version = "4.8.78")
		
		// Logging
		implementation("org.slf4j:slf4j-api:1.7.30")
		implementation("ch.qos.logback:logback-classic:1.2.3")
		implementation(
			group = "com.michael-bull.kotlin-inline-logger",
			name = "kotlin-inline-logger-jvm",
			version = "1.0.2"
		)
		
		// Utilities
		implementation("com.google.guava:guava:29.0-jre")
		implementation("org.apache.commons:commons-lang3:3.10")
		implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
		implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
		
		//Testing
		testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
		testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
		testImplementation(group = "org.koin", name = "koin-test", version = koinVersion)
	}
	
	tasks {
		compileKotlin {
			kotlinOptions.jvmTarget = "1.8"
		}
		compileTestKotlin {
			kotlinOptions.jvmTarget = "1.8"
		}
	}
	
}
dependencies {
	implementation(kotlin("stdlib-jdk8"))
}
repositories {
	mavenCentral()
}
val compileKotlin : KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}
val compileTestKotlin : KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}