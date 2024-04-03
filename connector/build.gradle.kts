plugins {
    `java-library`
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}

// pick-up the latest version from https://github.com/eclipse-edc/Connector/releases
val edcVersion = "0.5.1"

dependencies {
    implementation("org.eclipse.edc:data-plane-core:${edcVersion}")
    implementation("org.eclipse.edc:boot:${edcVersion}")
    implementation("org.eclipse.edc:control-plane-api-client:${edcVersion}")
    implementation("org.eclipse.edc:control-plane-api:${edcVersion}")
    implementation("org.eclipse.edc:control-plane-core:${edcVersion}")
    implementation("org.eclipse.edc:dsp:${edcVersion}")
    implementation("org.eclipse.edc:configuration-filesystem:${edcVersion}")
    implementation("org.eclipse.edc:vault-filesystem:${edcVersion}")
    implementation("org.eclipse.edc:management-api:${edcVersion}")
    implementation("org.eclipse.edc:transfer-data-plane:${edcVersion}")
    implementation("org.eclipse.edc:transfer-pull-http-receiver:${edcVersion}")
    implementation("org.eclipse.edc:iam-mock:${edcVersion}")
    implementation("org.eclipse.edc:data-plane-selector-api:${edcVersion}")
    implementation("org.eclipse.edc:data-plane-selector-core:${edcVersion}")
    implementation("org.eclipse.edc:data-plane-control-api:${edcVersion}")
    implementation("org.eclipse.edc:data-plane-public-api:${edcVersion}")
    implementation("org.eclipse.edc:data-plane-core:${edcVersion}")
    implementation("org.eclipse.edc:data-plane-http:${edcVersion}")
}

// configure the main class
application {
    mainClass.set("org.eclipse.edc.boot.system.runtime.BaseRuntime")
}

var distTar = tasks.getByName("distTar")
var distZip = tasks.getByName("distZip")

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    archiveFileName.set("connector.jar")
    dependsOn(distTar, distZip)
}

// Explicitly declare dependencies between tasks
