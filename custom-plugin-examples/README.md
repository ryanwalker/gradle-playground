# Gradle plugin for kubra

This plugin applies the following gradle plugins for your gradle project:

Spotless Java
Sonarqube
Jacoco 

To use the plugin:

```
buildscript {
  repositories {
    mavenLocal()
    mavenCentral()
  }

  dependencies {
    classpath 'com.kubra.gradle:GradlePlugin:3.0-SNAPSHOT'
  }
}

apply plugin: 'com.kubra.config'

```

For local development and testing you can publish mavenLocal() like this:

```
./gradlew publishToMavenLocal
```

Note that the plugin id is specified by naming the plugin properties file. 
[com.kubra.config.properties](src/main/resources/META-INF/gradle-plugins/com.kubra.config.properties)