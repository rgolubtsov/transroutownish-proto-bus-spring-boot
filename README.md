# Trans-RoutE-Townish (transroutownish) :small_blue_diamond: Urban bus routing microservice prototype

**A Spring Boot-based application, designed and intended to be run as a microservice,
<br />implementing a simple urban bus routing prototype**

---

## Table of Contents

* **[Building](#building)**
* **[Running](#running)**
* **[Operating](#operating)**
  * **[Error handling](#error-handling)**

## Building

The microservice is known to be built and run successfully under **Ubuntu Server (Ubuntu 20.04.3 LTS x86-64)**. Install the necessary dependencies (`openjdk-11-jdk-headless`, `maven`, `make`):

```
$ sudo apt-get update && \
  sudo apt-get install openjdk-11-jdk-headless maven make -y
```

**Build** the microservice using **Maven Wrapper**:

```
$ ./mvnw clean
...
$ ./mvnw compile
...
$ ./mvnw test  # <== Optional. This currently does nothing except emitting a huge (most likely useless) log output.
...
$ ./mvnw package
...
```

(Note: the `package` target above includes `test`.)

**Build** the microservice using **GNU Make** (optional):

```
$ make clean
...
$ make      # <== Compilation phase.
...
$ make test
...
$ make jar
...
$ make all  # <== Or make all the targets at one pass: compile,.. jar.
...
```

(Note: the `jar` target above includes `test`.)

## Running

**Run** the microservice using **Maven Wrapper** (generally for development and debugging purposes):

```
$ ./mvnw spring-boot:run; echo $?
$ #                     ^   ^   ^
$ #                     |   |   |
$ # --------------------+---+---+
$ # Whilst this is not necessary, it's beneficial knowing the exit code.
...
```

**Run** the microservice using its all-in-one JAR file, built previously by the `package` or `jar` targets:

```
$ java -jar target/bus-0.0.1-SNAPSHOT.jar; echo $?
$ #                                      ^   ^   ^
$ #                                      |   |   |
$ # -------------------------------------+---+---+
$ # Whilst this is not necessary, it's beneficial knowing the exit code.
...
```

## Operating

All the routes are contained in a so called **routes data store**. It is located in the `data/` directory. The default filename for it is `routes.txt`, but it can be specified explicitly (if intended to use another one) in the `src/main/resources/application.properties` file.

**Identify**, whether there is a direct route between two bus stops with IDs given in the **HTTP GET** request, searching for them against the underlying **routes data store**:

HTTP request param | Sample value | Another sample value
------------------ | ------------ | --------------------
`from`             | `4838`       | `82`
`to`               | `524987`     | `35390`

The direct route is found:

```
$ curl 'http://localhost:8080/api/direct?from=4838&to=524987'
{"from":4838,"to":524987,"direct":true}
```

The direct route is not found:

```
$ curl 'http://localhost:8080/api/direct?from=82&to=35390'
{"from":82,"to":35390,"direct":false}
```

### Error handling

When the query string passed in a request, contains inappropriate input, or the URI endpoint doesn't contain anything else at all after its path, the microservice will respond with the **HTTP 400 Bad Request** status code, including the response body in JSON representation, like the following:

```
$ curl 'http://localhost:8080/api/direct?from=qwerty4838&to=-i-.;--089asdf../nj524987'
{"error":"Request parameters must take positive integer values, in the range 1 .. 2,147,483,647. Please check your inputs."}
```

Or simply:

```
$ curl http://localhost:8080/api/direct
{"error":"Request parameters must take positive integer values, in the range 1 .. 2,147,483,647. Please check your inputs."}
```
