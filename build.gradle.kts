import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.dokka.gradle.DokkaTask

val ossrhUsername: String? by ext
val ossrhPassword: String? by ext

description = "The best RuneScape Private Server of All Time."

plugins {
	`maven-publish`
	signing
	kotlin("multiplatform") version "1.3.61"
	id("org.jetbrains.dokka") version "0.10.1"
	id("com.github.ben-manes.versions") version "0.27.0"
	id("net.researchgate.release") version "2.8.1"
}

tasks.withType<DependencyUpdatesTask> {
	rejectVersionIf {
		listOf("alpha", "beta", "rc", "cr", "m", "eap", "pr").any {
			candidate.version.contains(it, ignoreCase = true)
		}
	}
}

val dokka by tasks.existing(DokkaTask::class) {
	outputFormat = "javadoc"
	outputDirectory = "$buildDir/docs/javadoc"
}

val javadocJar by tasks.registering(Jar::class) {
	group = LifecycleBasePlugin.BUILD_GROUP
	description = "Assembles a jar archive containing the Javadoc API documentation."
	archiveClassifier.set("javadoc")
	dependsOn(dokka)
	from(dokka.get().outputDirectory)
}

repositories {
	mavenCentral()
	jcenter()
}

val koinVersion = "2.1.5"
val junitVersion = "5.6.2"
val exposedVersion = "0.24.1"
val jacksonVersion = "2.11.0"
val duskCoreVersion = "1.0.0"

group = "rs.dusk"
version = "1.0.0"

kotlin {
	sourceSets {
		named("commonMain") {
			dependencies {
				//Main
				implementation(group = "rs.dusk.core", name = "network", version = duskCoreVersion)
				implementation(group = "rs.dusk.core", name = "utility", version = duskCoreVersion)
				implementation(group = "com.github.User", name = "Repo:Tag")
				
				implementation(kotlin("stdlib-jdk8"))
				implementation(kotlin("reflect"))
				implementation("io.netty:netty-all:4.1.44.Final")
				implementation(group = "com.displee", name = "rs-cache-library", version = "6.7")
				implementation(group = "org.yaml", name = "snakeyaml", version = "1.26")
				implementation(group = "io.github.classgraph", name = "classgraph", version = "4.8.78")
				implementation(
					group = "com.michael-bull.kotlin-inline-logger",
					name = "kotlin-inline-logger-jvm",
					version = "1.0.2"
				)
				implementation(group = "org.koin", name = "koin-core", version = koinVersion)
				implementation(group = "org.koin", name = "koin-logger-slf4j", version = koinVersion)
				implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-core", version = "1.3.7")
				
				//Logging
				implementation("org.slf4j:slf4j-api:1.7.30")
				implementation("ch.qos.logback:logback-classic:1.2.3")
				
				//Utilities
				implementation("com.google.guava:guava:29.0-jre")
				implementation("org.apache.commons:commons-lang3:3.10")
				implementation("com.google.code.gson:gson:2.7")
				implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
				implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
				implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
				implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
				implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
				implementation("org.postgresql:postgresql:42.2.12")
				implementation("com.zaxxer:HikariCP:3.4.5")
				implementation("it.unimi.dsi:fastutil:8.3.1")
				
				//Testing
				testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
				testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
				testImplementation(group = "org.koin", name = "koin-test", version = koinVersion)
				testImplementation(group = "io.mockk", name = "mockk", version = "1.10.0")
			}
		}
		
		named("commonTest") {
			dependencies {
				implementation(kotlin("test-common"))
				implementation(kotlin("test-annotations-common"))
			}
		}
	}
	
	jvm {
		compilations.named("main") {
			kotlinOptions {
				jvmTarget = "1.8"
				freeCompilerArgs = listOf("-Xno-call-assertions", "-Xno-receiver-assertions", "-Xno-param-assertions")
			}
			
			dependencies {
				implementation(kotlin("stdlib-jdk8"))
				implementation("org.slf4j:slf4j-api:1.7.30")
			}
		}
		
		compilations.named("test") {
			kotlinOptions {
				jvmTarget = "1.8"
				freeCompilerArgs = listOf("-Xno-call-assertions", "-Xno-receiver-assertions", "-Xno-param-assertions")
			}
			
			dependencies {
				implementation(kotlin("test"))
				implementation(kotlin("test-junit"))
				implementation("org.slf4j:slf4j-jdk14:1.7.30")
			}
		}
		
		mavenPublication {
			artifact(javadocJar.get())
		}
	}
}

publishing {
	repositories {
		maven {
			if (project.version.toString().endsWith("SNAPSHOT")) {
				setUrl("https://oss.sonatype.org/content/repositories/snapshots")
			} else {
				setUrl("https://oss.sonatype.org/service/local/staging/deploy/maven2")
			}
			
			credentials {
				username = ossrhUsername
				password = ossrhPassword
			}
		}
	}
	
	publications.withType<MavenPublication> {
		pom {
			name.set(project.name)
			description.set(project.description)
			url.set("https://github.com/dusk-rs/dusk")
			inceptionYear.set("2019")
			
			licenses {
				license {
					name.set("ISC License")
					url.set("https://opensource.org/licenses/isc-license.txt")
				}
			}
			
			developers {
				developer {
					name.set("Tyluur")
					url.set("https://www.tyluur.tech")
				}
			}
			
			scm {
				connection.set("scm:git:https://github.com/dusk-rs/dusk")
				developerConnection.set("scm:git:git@github.com:tyluur/dusk.git")
				url.set("https://github.com/dusk-rs/dusk")
			}
			
			issueManagement {
				system.set("GitHub")
				url.set("https://github.com/dusk-rs/dusk/issues")
			}
			
			ciManagement {
				system.set("GitHub")
				url.set("https://github.com/dusk-rs/dusk/actions?query=workflow%3Aci")
			}
			
			contributors {
				contributor {
					name.set("Greg Hib")
					url.set("https://github.com/greghib")
				}
			}
		}
	}
}

signing {
	useGpgCmd()
	sign(publishing.publications)
}

tasks.afterReleaseBuild {
	dependsOn(tasks.publish)
}