import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("com.expediagroup.graphql") version "7.0.0-alpha.0"
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.spring") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
}

group = "gql.client.bug"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.expediagroup:graphql-kotlin-spring-client:7.0.0-alpha.0") {
        exclude("com.expediagroup", "graphql-kotlin-client-jackson")
    }
    implementation("com.expediagroup:graphql-kotlin-client-serialization:7.0.0-alpha.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

graphql {
    client {
        packageName = "gql.client.bug"
        schemaFile = file("${project.projectDir}/src/main/resources/gql/test-schema.graphql")
        queryFileDirectory = "${project.projectDir}/src/main/resources/gql/queries"
        serializer = com.expediagroup.graphql.plugin.gradle.config.GraphQLSerializer.KOTLINX
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
