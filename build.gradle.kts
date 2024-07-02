import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.polarbookshop"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.2"
extra["testcontainersVersion"] = "1.17.3"

dependencies {
	implementation("org.springframework.cloud:spring-cloud-starter-gateway")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.session:spring-session-data-redis")
	runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.111.Final:osx-aarch_64")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}

tasks.withType<BootBuildImage> {
	imageName.set(project.name)
	environment = mapOf("BP_JVM_VERSION" to "21.*")

	docker {
		publishRegistry {
			username.set(project.findProperty("registryUsername") as String?)
			password.set(project.findProperty("registryToken") as String?)
			url.set(project.findProperty("registryUrl") as String?)
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
