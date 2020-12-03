# student-mobility-inteken-ontvanger-email
[![Build Status](https://travis-ci.org/SURFnet/student-mobility-inteken-ontvanger-email.svg)](https://travis-ci.org/SURFnet/student-mobility-inteken-ontvanger-email)
[![codecov.io](https://codecov.io/github/SURFnet/student-mobility-inteken-ontvanger-email/coverage.svg)](https://codecov.io/github/SURFnet/student-mobility-inteken-ontvanger-email)

Specific part of the institution hosted part of the broker for educational cross-institution registrations. 
This is an implementation that emails the user after registration 

## [Getting started](#getting-started)

### [System Requirements](#system-requirements)

- Java 8
- Maven 3

## [Building and running](#building-and-running)

### [The student-mobility-inteken-ontvanger-email-server](#student-mobility-inteken-ontvanger-email-server)

This project uses Spring Boot and Maven. To run locally, type:

```
mvn spring-boot:run
```

To build and deploy (the latter requires credentials in your maven settings):

`mvn clean deploy`
