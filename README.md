# Trade Report Engine - Java Code Challenge

## Overview

This is a Java project that loads a set of event XML messages into a database, and then displays a custom subset of them through a REST API.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle 7.5](https://gradle.org/)

## Project setup instructions

To start using this project use the following commands:

```shell
git clone https://github.com/leighbrunner/trade-reporting-engine.git
cd trade-reporting-engine
./gradlew bootRun
```

Alternatively you can run the app by executing the `main` method in the `com.leighbrunner.tradereportingengine.TradeReportingEngineApplication` class from your IDE.

You can then view the API with the required report at http://localhost:8080/

Some additional endpoints to demo the services can be found here:
 - http://localhost:8080/event/all
 - http://localhost:8080/event/currency/aud
 - http://localhost:8080/event/currency/usd

## Database Access

When the app is running, navigate to http://localhost:8080/h2-console and use the default login details:
 - **JDBC URL:** jdbc:h2:mem:testdb
 - **User Name:** sa
 - **Password:** *(no password)*

## Configuration

The directory that contains the XML files to be loaded is configured by the eventXmlDir variable in application.yaml

New event XML files can be added to this directory.  The application will need to be restarted to load them in.

## Testing the application

```shell
./gradlew test
```

## License info
MIT License

Copyright (c) 2024 Leigh Brunner

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.