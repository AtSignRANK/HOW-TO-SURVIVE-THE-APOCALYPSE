//마인크래프트 플러그인 Gradle
 plugins {
    kotlin("jvm") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") // 버킷 레포
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT") // 버킷
}

tasks {
    // 코틀린 컴파일 옵션
    compileKotlin {
        kotlinOptions.jvmTarget = "11" // JDK 버전
    }

    // Scala를 제외한 여러 언어 공통
    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}