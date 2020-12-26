import jdk.nashorn.internal.runtime.Debug.id

group = "dusk.rs"
description = "#1 rsps. no noobs allowed"

val ossrhUsername : String? by ext
val ossrhPassword : String? by ext

plugins {
	base
	id("com.github.ben-manes.versions") version "0.36.0"
	
	jdk.nashorn.internal.runtime.Debug.id("kotlinx.benchmark")
	id("org.jetbrains.dokka") version "0.11.0-dev-59"
	classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
	classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
}

buildscript {
	ext.kotlin_version = '1.3.71'
	
	repositories {
		mavenCentral()
		google()
		jcenter()
	}
	dependencies {
		id("java")
		id("com.github.ben-manes.versions") version "0.24.0"
		
		id("kotlinx.benchmark") apply true
		id("org.jetbrains.dokka") apply true
		id("org.jetbrains.kotlin.plugin.allopen") version "1.3.5" apply true
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
		classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
	}
}

repositories {
	mavenCentral()
	jcenter()
	maven("https://dl.bintray.com/kotlin/kotlinx")
}

dependencies {
	compile("com.squareup.okio:okio:2.3.0")
}

plugins.withType<MavenPublishPlugin> {
	apply(plugin = "org.gradle.signing")
	
	plugins.withType<KotlinMultiplatformPluginWrapper> {
		apply(plugin = "org.jetbrains.dokka")
		
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
		
		configure<KotlinMultiplatformExtension> {
			explicitApi()
			
			jvm {
				mavenPublication {
					artifact(javadocJar.get())
				}
			}
			
			js {
				browser()
				nodejs()
			}
		}
	}
	
	configure<PublishingExtension> {
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
				url.set("https://github.com/dusk-rs/dusk")
				inceptionYear.set("2020")
				
				licenses {
					license {
						name.set("ISC License")
						url.set("https://opensource.org/licenses/isc-license.txt")
					}
				}
				
				developers {
					developer {
						name.set("Tyluur")
						url.set("https://www.dusk.rs/tyluur")
					}
				}
				
				contributors {
					contributor {
						name.set("Tyluur")
					}
					
				}
				
				scm {
					connection.set("scm:git:https://github.com/dusk-rs/dusk")
					developerConnection.set("scm:git:git@github.com:dusk-rs/dusk.git")
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
			}
		}
		
		configure<SigningExtension> {
			useGpgCmd()
			sign(publications)
		}
	}
}