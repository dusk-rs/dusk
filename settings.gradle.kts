rootProject.name = "dusk"

pluginManagement {
	repositories {
		google()
		jcenter()
		mavenCentral()
		gradlePluginPortal()
		maven("https://dl.bintray.com/kotlin/kotlinx")
	}
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "kotlin-multiplatform") {
				useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
			}
		}
	}
}