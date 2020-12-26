rootProject.name = "dusk"

pluginManagement {
	repositories {
		maven("https://dl.bintray.com/kotlin/kotlinx")
		gradlePluginPortal()
	}
	resolutionStrategy {
		eachPlugin {
			if (requested.id.id == "kotlin-multiplatform") {
				useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
			}
		}
	}
}
/*enable here when you need other plugins.
pluginManagement {
	repositories {
		google()
		jcenter()
		mavenCentral()
		gradlePluginPortal()
	}
}*/