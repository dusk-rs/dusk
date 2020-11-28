plugins {
    `java-library`
}

dependencies {
    implementation(project(":cache"))
    implementation(project(":utility"))
    implementation(project(":network"))
}
tasks.withType<Test> {
    jvmArgs("-XX:-OmitStackTraceInFastThrow")
}
tasks.withType<Test> {
    useJUnitPlatform()
}