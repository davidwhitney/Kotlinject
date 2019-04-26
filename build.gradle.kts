plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.31"
    maven
}

repositories {
    jcenter()
    google()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.31")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.0-M1")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.3.30")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.3.30")
}

sourceSets {
    getByName("main").java.srcDirs("src/com")
    getByName("test").java.srcDirs("test/com")
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType(Test::class) {
    useJUnitPlatform()
}