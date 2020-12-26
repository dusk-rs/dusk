group = "dusk.rs"
description = "#1 rsps. no noobs allowed"

plugins {
	`java-library`
	`maven-publish`
	signing
	
/*	base
	id("com.github.ben-manes.versions") version "0.36.0"
	id("kotlinx.benchmark")
	id("kotlinx.benchmark")
	id("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.5")
	id("org.jetbrains.kotlin:kotlin-serialization:$1.3.5")*/
}

buildscript {
	repositories {
		mavenCentral()
		google()
		jcenter()
	}
}

repositories {
	mavenCentral()
	jcenter()
	maven("https://dl.bintray.com/kotlin/kotlinx")
}

java {
	withJavadocJar()
	withSourcesJar()
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
				name.set("My Library")
				description.set("A concise description of my library")
				url.set("http://www.example.com/library")
				properties.set(mapOf(
					"myProp" to "value",
					"prop.with.dots" to "anotherValue"
				))
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						id.set("johnd")
						name.set("John Doe")
						email.set("john.doe@example.com")
					}
				}
				scm {
					connection.set("scm:git:git://example.com/my-library.git")
					developerConnection.set("scm:git:ssh://example.com/my-library.git")
					url.set("http://example.com/my-library/")
				}
			}
		}
	}
	repositories {
		maven {
			// change URLs to point to your repos, e.g. http://my.org/repo
			val releasesRepoUrl = uri("$buildDir/repos/releases")
			val snapshotsRepoUrl = uri("$buildDir/repos/snapshots")
			url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
		}
	}
}

if (project.hasProperty("signing.keyId")) {
	apply("plugin: 'signing'")
	signing {
		sign(publishing.publications["mavenJava"])
	}
}

tasks.javadoc {
	if (JavaVersion.current().isJava9Compatible) {
		(options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
	}
}
