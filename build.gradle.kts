description = "The best RuneScape Private Server of All Time."

val kotlinVersion = "1.3.5"
val jdkVersion = "1.8"
val koinVersion = "2.1.5"
val junitVersion = "5.6.2"
val exposedVersion = "0.24.1"
val jacksonVersion = "2.11.0"
val duskCoreVersion = "1.0.0"

plugins {
	base
	kotlin("multiplatform") version Versions.kotlin apply false
	id("kotlinx.benchmark") version Versions.kotlinBenchmark apply false
	id("org.jetbrains.dokka") version Versions.dokka apply false
	id("org.jetbrains.kotlin.plugin.allopen") version Versions.kotlin apply false
}

tasks.withType<DependencyUpdatesTask> {
	rejectVersionIf {
		listOf("alpha", "beta", "rc", "cr", "m", "eap", "pr", "dev").any {
			candidate.version.contains(it, ignoreCase = true)
		}
	}
}

subprojects {
	repositories {
		mavenCentral()
		jcenter()
		maven("https://dl.bintray.com/kotlin/kotlinx")
	}
	
	apply(plugin = "kotlin")
	apply(plugin = "idea")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	
	"rs.dusk"
	"1.0.0"
	
	apply {
		plugin("org.jetbrains.kotlin.jvm")
	}
	
	repositories {
		mavenCentral()
	}
	
	val implementation by configurations
	
	dependencies {
		implementation(kotlin("stdlib-jdk8"))
		implementation("rs.dusk.core", "network", duskCoreVersion)
		implementation("rs.dusk.core", "utility", duskCoreVersion)
		
		implementation(kotlin("stdlib-jdk8"))
		implementation(kotlin("reflect"))
		implementation("io.netty:netty-all:4.1.44.Final")
		implementation("com.displee", "rs-cache-library", "6.7")
		implementation("org.yaml", "snakeyaml", "1.26")
		implementation("io.github.classgraph", "classgraph", "4.8.78")
		implementation(
			"com.michael-bull.kotlin-inline-logger",
			"kotlin-inline-logger-jvm",
			"1.0.2"
		)
		implementation("org.koin", "koin-core", koinVersion)
		implementation("org.koin", "koin-logger-slf4j", koinVersion)
		implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.7")
		
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
		testImplementation("org.koin", "koin-test", koinVersion)
		testImplementation("io.mockk", "mockk", "1.10.0")
	}
	
	tasks.withType<KotlinCompile> {
		kotlinOptions.jvmTarget = jdkVersion
	}
	
	plugins.withType<MavenPublishPlugin> {
		apply(plugin = "org.gradle.signing")
		
		plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper> {
			apply(plugin = "org.jetbrains.dokka")
			
			val dokka by tasks.existing(DokkaTask::class) {
				outputFormat = "javadoc"
				outputDirectory = "$buildDir/docs/javadoc"
			}
			
			val javadocJar by tasks.registering(Jar::class) {
				LifecycleBasePlugin.BUILD_GROUP
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
						userossrhUsername
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
}

val ossrhUsername : String? by ext
val ossrhPassword : String? by ext