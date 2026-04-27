plugins {
    application
    java
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.swing")
}

dependencies {
    implementation("com.yahoofinance-api:YahooFinanceAPI:3.17.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}

application {
    mainClass.set("citi.App")
}

tasks.test {
    useJUnitPlatform()
}
