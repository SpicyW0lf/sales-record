plugins {
	java
	jacoco
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
}



group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("org.liquibase:liquibase-core")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.testcontainers:postgresql:1.19.3")
	testImplementation("org.testcontainers:junit-jupiter")
}

jacoco {
	toolVersion = "0.8.11"
	reportsDirectory = layout.buildDirectory.dir("myDir")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}

tasks.jacocoTestReport {

	reports {
		xml.required = false
		csv.required = false
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			rule {
				element = "CLASS"
				limit {
					counter = "LINE"
					value = "COVEREDRATIO"
				}
				excludes = mutableListOf(
					"com.example.salesrecord.models.*",
					"com.example.salesrecord.models.configs.*",
					"com.example.salesrecord.models.DTO.*",
					"com.example.salesrecord.exception.*",
					"com.example.salesrecord.repositories.*",
					"com.example.salesrecord.typehandler.*"
				)
			}
		}
	}
}
