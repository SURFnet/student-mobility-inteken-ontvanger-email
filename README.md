# student-mobility-inteken-ontvanger-email
[![Build Status](https://github.com/SURFnet/student-mobility-inteken-ontvanger-email/actions/workflows/build.yml/badge.svg)](https://github.com/SURFnet/student-mobility-inteken-ontvanger-email/actions/workflows/maven.yml/badge.svg)
[![codecov](https://codecov.io/gh/SURFnet/student-mobility-inteken-ontvanger-email/branch/main/graph/badge.svg)](https://codecov.io/gh/SURFnet/student-mobility-inteken-ontvanger-email)

Specific part of the institution hosted part of the broker for educational cross-institution registrations.
This is an implementation that emails the user after registration

## [Getting started](#getting-started)

### [System Requirements](#system-requirements)

- Java 8
- Maven 3

Set the JAVA_HOME property for maven (example for macOS):

```shell
export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/
```

## [Building and running](#building-and-running)

### [The student-mobility-inteken-ontvanger-email-server](#student-mobility-inteken-ontvanger-email-server)

This project uses Spring Boot and Maven. To run locally, type:

```shell
mvn spring-boot:run
```

To build and deploy (the latter requires credentials in your maven settings):

```shell
mvn clean deploy
```
