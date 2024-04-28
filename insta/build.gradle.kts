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

	// Crypto Bouncy Castle
	implementation("org.bouncycastle:bcpkix-jdk15on:1.70")

	// Map Struct
	implementation("org.mapstruct:mapstruct:1.5.3.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

	// Faker
	implementation("com.github.javafaker:javafaker:1.0.2")

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

/**
 * What is a Gradle task ?
 * A Gradle task is a unit of work that can be executed by the Gradle build system.
 * Tasks are the basic building blocks of a Gradle build script.
 */

/**
 * What is the classpath ?
 * The classpath is a list of directories and JAR files that the Java compiler and runtime use to find classes.
 * The classpath is a parameter of the Java Virtual Machine (JVM) that tells the JVM where to look for user-defined classes and packages.
 */

/**
 * What is the runtimeClasspath ?
 * The runtimeClasspath is a list of directories and JAR files that the Java runtime uses to find classes.
 */

/**
 * What is the main class ?
 * mainClass: The fully qualified name of the main class to run.
 * This class must have a main method.
 */

/**
 * What is the sourceSets ?
 * A SourceSet represents a logical group of Java source and resource files.
 * A project can have multiple SourceSets, each representing a different group of source files.
 */

/**
 * What is a project property ?
 * A project property is a value that is passed to a Gradle build script from the command line.
 * Project properties are defined using the -P flag when running the Gradle build.
 * e.g ./gradlew loadFakeData -PdbName=insta
 */


//tasks.register("helloWorld") {
//	println("Hello World")
//}


tasks.register<JavaExec>("seedDBWithFakeData") {
	group = "database"
	description = "Run the application with fake data"
	classpath = sourceSets["test"].runtimeClasspath + sourceSets["main"].runtimeClasspath
	mainClass = "com.example.insta.faker.FakeDataLoaderRunner"

	// Provide a default value for dbName if it's not specified as a project property
	val dbName: String = project.findProperty("dbName") as String? ?: "insta"
	args("--spring.data.mongodb.database=$dbName")
}

tasks.register<Exec>("createDBDump") {
	group = "database"
	description = "Creates a dump of the MongoDB database and zips it."

	val dbName: String = project.findProperty("dbName") as String? ?: "insta"
	commandLine("mongodump", "--db=$dbName", "--archive=./db/db.dump", "--gzip")
}

tasks.register<Exec>("restoreDBDump") {
	group = "database"
	description = "Restores the MongoDB database from a zipped dump."

	val dbName: String = project.findProperty("dbName") as String? ?: "insta"
	commandLine("mongorestore", "--db=$dbName", "--archive=./db/db.dump", "--gzip")
}



