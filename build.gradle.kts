version = "1"

val javaVer = JavaVersion.VERSION_15
val koinVersion = "2.1.5"
val junitVersion = "5.6.2"
val exposedVersion = "0.24.1"
val jacksonVersion = "2.11.0"

plugins {
	kotlin("jvm") version "1.4.21"
	
	`java-library`
	`maven-publish`
	maven
	java
	signing
	
	base
}

buildscript {
	repositories {
		mavenCentral()
		google()
		jcenter()
	}
}

allprojects {
	apply(plugin = "idea")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	
	repositories {
		mavenCentral()
		jcenter()
		maven("https://dl.bintray.com/kotlin/kotlinx")
		maven {
			// change URLs to point to your repos, e.g. http://my.org/repo
			val releasesRepoUrl = uri("$buildDir/repos/releases")
			val snapshotsRepoUrl = uri("$buildDir/repos/snapshots")
			url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
		}
	}
	
	dependencies {
		//Main
		implementation(group = "rs.dusk.core", name = "network", version = "0.1.2")
		implementation(group = "rs.dusk.core", name = "utility", version = "0.1.2")
		
		implementation(kotlin("stdlib-jdk8"))
		implementation(kotlin("reflect"))
		implementation("io.netty:netty-all:4.1.44.Final")
		implementation(group = "com.displee", name = "rs-cache-library", version = "6.4")
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
	
	tasks {
		compileKotlin {
			kotlinOptions.jvmTarget = "1.8"
		}
		compileTestKotlin {
			kotlinOptions.jvmTarget = "1.8"
		}
	}
	
	tasks {
		task("generatePom") {
			doLast {
				maven.conf2ScopeMappings.apply {
					addMapping(
						0, configurations.getByName("implementation"),
						org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer.COMPILE
					)
					addMapping(
						0, configurations.getByName("compileOnly"),
						org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer.PROVIDED
					)
				}
				maven.pom {
					withGroovyBuilder {
						"project" {
							"build" {
								"plugins" {
									"plugin" {
										setProperty("artifactId", "maven-compiler-plugin")
										setProperty("version", "3.8.1")
										"configuration" {
											setProperty("compilerVersion", "$javaVer")
											setProperty("source", "$javaVer")
											setProperty("target", "$javaVer")
										}
									}
								}
							}
						}
					}
				}.writeTo("pom.xml")
			}
		}
	}
	
	publishing {
		publications {
			create<MavenPublication>("mavenJava") {
				artifactId = "my-library"
				from(components["java"])
				versionMapping {
					usage("java-api") {
						fromResolutionOf("runtimeClasspath")
					}
					usage("java-runtime") {
						fromResolutionResult()
					}
				}
				pom {
					name.set("Dusk")
					description.set("A concise description of my library")
					url.set("http://www.dusk.rs")
					properties.set(
						mapOf(
							"1337" to "true",
							"u" to "noob"
						)
					)
					licenses {
						license {
							name.set("The Apache License, Version 2.0")
							url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
						}
					}
					developers {
						developer {
							id.set("tyluur")
							email.set("itstyluur@violated.rs")
						}
					}
					scm {
						connection.set("scm:git:git://dusk.rs/dusk.git.git")
						developerConnection.set("scm:git:ssh://github.com/dusk-rs/dusk.git")
						url.set("http://github.com/dusk-rs/dusk")
					}
				}
			}
		}
	}
	
	base.archivesBaseName = "gradle"
	
	val webAssetPatterns = Action<CopySpec> {
		include("**/*.html", "**/*.png", "**/*.jpg")
	}
	
	tasks.register<Copy>("copyAppAssets") {
		into("$buildDir/inPlaceApp")
		from("src/main/webapp", webAssetPatterns)
	}
	
	tasks.register<Zip>("archiveDistAssets") {
		archiveFileName.set("distribution-assets.zip")
		destinationDirectory.set(file("$buildDir/dists"))
		
		from("distResources", webAssetPatterns)
	}
	
	tasks.register<Copy>("nestedSpecs") {
		into("$buildDir/explodedWar")
		exclude("**/*staging*")
		from("src/dist") {
			include("**/*.html", "**/*.png", "**/*.jpg")
		}
		from(sourceSets.main.get().output) {
			into("WEB-INF/classes")
		}
		into("WEB-INF/lib") {
			from(configurations.runtimeClasspath)
		}
	}
	
	val myZip by tasks.registering(Zip::class) {
		from("somedir")
	}
	
	val myOtherZip by tasks.registering(Zip::class) {
		archiveAppendix.set("wrapper")
		archiveClassifier.set("src")
		from("somedir")
	}
	
	tasks.register("echoNames") {
		doLast {
			println("Project name: ${project.name}")
			println(myZip.get().archiveFileName.get())
			println(myOtherZip.get().archiveFileName.get())
		}
	}
	
}
group = "dusk.rs"
description = "#1 rsps. no noobs allowed"