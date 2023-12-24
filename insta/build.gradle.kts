plugins {
	java
	id("org.springframework.boot") version "3.1.2"
	id("io.spring.dependency-management") version "1.1.2"
	id("com.diffplug.spotless") version "6.20.0"
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
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")

	// Password Strength Assessment
	implementation("com.nulab-inc:zxcvbn:1.8.2")

	// Mail
	implementation("org.springframework.boot:spring-boot-starter-mail:3.2.0")

	// Controller Tests
	testImplementation("io.rest-assured:rest-assured:5.3.1")




}

tasks.withType<Test> {
	useJUnitPlatform()
}

spotless {
	java {
		googleJavaFormat()
	}
}

// Always run spotlessApply before spotlessCheck
tasks.named("check") {
	dependsOn("spotlessJavaApply")
}

//tasks.named("spotlessJavaCheck").configure {
//	enabled = false
//}


